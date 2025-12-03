package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.basemodslib.utils.Extensions;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;

import java.util.List;

public abstract class AbstractIronShulkerBoxBlockEntity
        extends RandomizableContainerBlockEntity
        implements WorldlyContainer
{
    private static final byte IMPLICIT_CLOSING_DELAY = 9;

    private int openCount;
    private final int[] slots;
    private AnimationStatus animationStatus;
    private NonNullList<ItemStack> itemStacks;

    private float progress, progressOld;

    // Delay a bit more the closing so that this nice animation is seen by the user.
    private byte closing_delay;
    private boolean close_sound_not_played;

    public AbstractIronShulkerBoxBlockEntity(BlockEntityType<?> typeIn, BlockPos blockPos, BlockState blockState, IronShulkerBoxesTypes shulkerBoxTypeIn) {
        super(typeIn, blockPos, blockState);

        this.animationStatus = AnimationStatus.CLOSED;
        this.slots = new int[shulkerBoxTypeIn.size];
        for (int I = 0; I < slots.length; I++) { slots[I] = I; }
        // The above is much faster than using this: this.slots = IntStream.range(0, shulkerBoxTypeIn.size).toArray();
        this.itemStacks = NonNullList.withSize(shulkerBoxTypeIn.size, ItemStack.EMPTY);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AbstractIronShulkerBoxBlockEntity pBlockEntity) {
        pBlockEntity.updateAnimation(pLevel, pPos, pState);
    }

    protected void updateAnimation(Level level, BlockPos pos, BlockState state) {
        this.progressOld = this.progress;
        switch (this.animationStatus) {
            case CLOSED:
                closing_delay = 0;
                this.progress = 0.0F;
                close_sound_not_played = true;
                break;
            case OPENING:
                this.progress += 0.1F;
                if (this.progressOld == 0.0F) {
                    doNeighborUpdates(level, pos, state);
                }

                if (this.progress >= 1.0F) {
                    this.animationStatus = AnimationStatus.OPENED;
                    this.progress = 1.0F;
                    doNeighborUpdates(level, pos, state);
                }

                this.moveCollidedEntities(level, pos, state);
                break;
            case OPENED:
                this.progress = 1.0F;
                close_sound_not_played = true;
                break;
            case CLOSING:
                if (++closing_delay < IMPLICIT_CLOSING_DELAY) { return; }
                if (close_sound_not_played) {
                    level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    close_sound_not_played = false;
                }
                this.progress -= 0.1F;
                if (this.progressOld == 1.0F) {
                    doNeighborUpdates(level, pos, state);
                }

                if (this.progress <= 0.0F) {
                    this.animationStatus = AnimationStatus.CLOSED;
                    this.progress = 0.0F;
                    closing_delay = 0;
                    close_sound_not_played = true;
                    doNeighborUpdates(level, pos, state);
                }
        }
    }

    public AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }

    public AABB getBoundingBox(BlockState pState) {
        return Shulker.getProgressAabb(1f, pState.getValue(AbstractIronShulkerBoxBlock.FACING), 0.5F * this.getProgress(1.0F));
    }

    private void moveCollidedEntities(Level pLevel, BlockPos pPos, BlockState pState)
    {
        if (pState.getBlock() instanceof AbstractIronShulkerBoxBlock) {
            Direction direction = pState.getValue(AbstractIronShulkerBoxBlock.FACING);
            AABB aabb = Shulker.getProgressDeltaAabb(1f, direction, this.progressOld , this.progress).move(pPos);
            List<Entity> list = pLevel.getEntities(null, aabb);

            if (!list.isEmpty()) {
                for (Entity entity : list) {
                    if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
                        entity.move(MoverType.SHULKER_BOX, new Vec3((aabb.getXsize() + 0.01D) * (double) direction.getStepX(), (aabb.getYsize() + 0.01D) * (double) direction.getStepY(), (aabb.getZsize() + 0.01D) * (double) direction.getStepZ()));
                    }
                }
            }
        }
    }

   /**
    * Returns the number of slots in the inventory.
    */
    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        if (pId == 1) {
            this.openCount = pType;
            if (pType == 0) {
                this.animationStatus = AnimationStatus.CLOSING;
                OnClosing();
                // doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            }

            if (pType == 1) {
                this.animationStatus = AnimationStatus.OPENING;
                OnOpening();
                // doNeighborUpdates(this.getLevel(), this.worldPosition, this.getBlockState());
            }

            return true;
        } else {
            return super.triggerEvent(pId, pType);
        }
    }

    protected void OnOpening() {}

    protected void OnClosing() {}

    private static void doNeighborUpdates(Level pLevel, BlockPos pPos, BlockState pState) {
        pState.updateNeighbourShapes(pLevel, pPos, 3);
        pLevel.updateNeighborsAt(pPos, pState.getBlock());
    }

    @Override
    public void startOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, ++this.openCount);

            if (this.openCount == 1) {
                this.level.gameEvent(pPlayer, GameEvent.CONTAINER_OPEN, this.worldPosition);
                this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    public void stopOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator()) {
            --this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);

            if (this.openCount < 1) {
                this.level.gameEvent(pPlayer, GameEvent.CONTAINER_CLOSE, this.worldPosition);
                // this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    protected Component getDefaultName() {
        return getBlockState().getBlock().getName();
    }

    @Override
    public final void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
    {
        super.loadAdditional(tag, provider);
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

        if (!tryLoadLootTable(tag)) {
            LoadBlockEntityData(tag, provider);
        }
    }

    @Override
    protected final void saveAdditional(CompoundTag pTag, HolderLookup.Provider provider)
    {
        super.saveAdditional(pTag, provider);

        if (!this.trySaveLootTable(pTag)) {
            SaveBlockEntityData(pTag, provider);
        }
    }

    protected void LoadBlockEntityData(CompoundTag tag , HolderLookup.Provider registries)
    {
        if (tag.contains("Items", CompoundTag.TAG_LIST)) {
            ContainerHelper.loadAllItems(tag, this.itemStacks, registries);
        }
    }

    protected void SaveBlockEntityData(CompoundTag tag , HolderLookup.Provider registries)
    {
        ContainerHelper.saveAllItems(tag, this.itemStacks, registries);
    }

    @Override
    public NonNullList<ItemStack> getItems() { return this.itemStacks; }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.itemStacks = NonNullList.withSize(this.GetShulkerBoxType().size, ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {
            if (i < this.itemStacks.size()) {
                this.itemStacks.set(i, itemsIn.get(i));
            }
        }
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) { return this.slots; }

   /**
    * Returns {@code true} if automation can insert the given item in the given slot from the given side.
    */
    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @MaybeNull Direction pDirection) {
        return !(Block.byItem(pItemStack.getItem()) instanceof ShulkerBoxBlock) || !(Block.byItem(pItemStack.getItem()) instanceof AbstractIronShulkerBoxBlock);
    }

   /**
    * Returns {@code true} if automation can extract the given item in the given slot from the given side.
    */
    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) { return true; }

    public float getProgress(float pPartialTicks) {
        return Extensions.Lerp(pPartialTicks, this.progressOld, this.progress);
    }

    public boolean isClosed() {
        return this.animationStatus == AnimationStatus.CLOSED;
    }

    public IronShulkerBoxesTypes GetShulkerBoxType() {
        IronShulkerBoxesTypes type = IronShulkerBoxesTypes.IRON;

        if (this.hasLevel()) {
            IronShulkerBoxesTypes typeNew = AbstractIronShulkerBoxBlock.getTypeFromBlock(this.getBlockState().getBlock());

            if (typeNew != null) {
                type = typeNew;
            }
        }

        return type;
    }

    public enum AnimationStatus { CLOSED, OPENING, OPENED, CLOSING }
}
