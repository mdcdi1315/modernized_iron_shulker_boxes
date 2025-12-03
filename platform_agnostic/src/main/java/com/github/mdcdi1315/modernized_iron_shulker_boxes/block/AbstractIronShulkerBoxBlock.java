package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.DotNetLayer.System.Func1;
import com.github.mdcdi1315.basemodslib.codecs.CodecUtils;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxUpgradeItem;
import com.google.common.collect.Maps;

import com.mojang.serialization.MapCodec;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.Util;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.*;
import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractIronShulkerBoxBlock
        extends BaseEntityBlock
{
    private static final Component UNKNOWN_CONTENTS = Component.translatable("container.shulkerBox.unknownContents");
    private static final VoxelShape UP_OPEN_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    private static final VoxelShape WEST_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_OPEN_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private static final Map<Direction, VoxelShape> OPEN_SHAPE_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_258974_) -> {
        p_258974_.put(Direction.NORTH, NORTH_OPEN_AABB);
        p_258974_.put(Direction.EAST, EAST_OPEN_AABB);
        p_258974_.put(Direction.SOUTH, SOUTH_OPEN_AABB);
        p_258974_.put(Direction.WEST, WEST_OPEN_AABB);
        p_258974_.put(Direction.UP, UP_OPEN_AABB);
        p_258974_.put(Direction.DOWN, DOWN_OPEN_AABB);
    });
    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final Property<IronShulkerBoxColor> COLOR = new IronShulkerBoxColorProperty("isb_color");
    public static final ResourceLocation CONTENTS = IronShulkerBoxesModInstance.ID("contents");

    private final IronShulkerBoxesTypes type;
    protected final Func1<BlockEntityType<? extends AbstractIronShulkerBoxBlockEntity>> block_ent_type;

    private record SuffocatingViewBlockingPredicate()
        implements StatePredicate
    {
        private SuffocatingViewBlockingPredicate {}

        public static final SuffocatingViewBlockingPredicate INSTANCE = new SuffocatingViewBlockingPredicate();

        @Override
        public boolean test(BlockState state, BlockGetter getter, BlockPos position)
        {
            if (getter.getBlockEntity(position) instanceof AbstractIronShulkerBoxBlockEntity entity) {
                return entity.isClosed();
            } else {
                return true;
            }
        }
    }

    private record DynamicDropCreater(AbstractIronShulkerBoxBlockEntity entity)
            implements LootParams.DynamicDrop
    {
        @Override
        public void add(Consumer<ItemStack> consumer) {
            for (int i = 0; i < entity.getContainerSize(); ++i) {
                consumer.accept(entity.getItem(i));
            }
        }
    }

    public AbstractIronShulkerBoxBlock(
            Properties properties,
            IronShulkerBoxesTypes type,
            Func1<BlockEntityType<? extends AbstractIronShulkerBoxBlockEntity>> type_getter
    ) {
        super(properties
                .forceSolidOn()
                .strength(2.0F)
                .dynamicShape()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .isSuffocating(SuffocatingViewBlockingPredicate.INSTANCE)
                .isViewBlocking(SuffocatingViewBlockingPredicate.INSTANCE)
        );

        this.type = type;
        this.block_ent_type = type_getter;

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(COLOR , IronShulkerBoxColor.NONE));
    }

    protected final <T extends AbstractIronShulkerBoxBlock> MapCodec<T> CreateMapCodecForIronShulkerBlock(Function<Properties, T> constructor) {
        return CodecUtils.CreateMapCodecDirect(propertiesCodec(), constructor);
    }

    @Override
    @MaybeNull
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> server_block_entity) {
        return createTickerHelper(server_block_entity, this.block_ent_type.function(), AbstractIronShulkerBoxBlockEntity::tick);
    }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    *
    * @deprecated call via {@link BlockStateBase#getRenderShape}
    * whenever possible. Implementing/overriding is fine.
    */
    @Override
    @Deprecated
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof IronShulkerBoxUpgradeItem) {
            return ItemInteractionResult.FAIL;
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            if (level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity ironShulkerBoxBlockEntity) {
                if (CanOpen(state, level, pos, ironShulkerBoxBlockEntity)) {
                    player.openMenu(ironShulkerBoxBlockEntity);
                    player.awardStat(Stats.OPEN_SHULKER_BOX);
                    PiglinAi.angerNearbyPiglins(player, true);
                }

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private static boolean CanOpen(BlockState pState, Level pLevel, BlockPos pPos, AbstractIronShulkerBoxBlockEntity pBlockEntity) {
        if (pBlockEntity.getAnimationStatus() != AbstractIronShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
            return true;
        } else {
            AABB aabb = Shulker.getProgressDeltaAabb(1F, pState.getValue(FACING), 0.0F,0.5F).move(pPos).deflate(1.0E-6D);
            return pLevel.noCollision(aabb);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext cxt) {
        return this.defaultBlockState()
                .setValue(FACING, cxt.getClickedFace())
                .setValue(COLOR, Objects.requireNonNullElse(
                        cxt.getItemInHand().get(IronShulkerBoxColorDataComponentType.INSTANCE),
                        IronShulkerBoxColor.NONE
                ));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING, COLOR); }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     *
     * @return
     */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity isb_entity) {
            if (!level.isClientSide && player.isCreative() && !isb_entity.isEmpty()) {
                ItemStack itemstack = IronShulkerBoxItem.CreateItemStack(state.getValue(COLOR), this);
                isb_entity.saveToItem(itemstack, level.registryAccess());
                if (isb_entity.hasCustomName()) {
                    itemstack.set(DataComponents.CUSTOM_NAME, isb_entity.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(level ,pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            } else {
                isb_entity.unpackLootTable(player);
            }
        }

        super.playerWillDestroy(level, pos, state, player);

        return state;
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder)
    {
        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof AbstractIronShulkerBoxBlockEntity shulker_box_entity) {
            builder = builder.withDynamicDrop(CONTENTS, new DynamicDropCreater(shulker_box_entity));
        }

        return super.getDrops(state, builder);
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @MaybeNull LivingEntity placer, ItemStack item_stack)
    {
        var cn = item_stack.get(DataComponents.CUSTOM_NAME);
        if (cn != null && level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity ent)
        {
            ent.setComponents(
                    DataComponentMap.builder()
                            .addAll(ent.components()) // Retain all the old components! Not doing this we lose every custom data set!
                            .set(DataComponents.CUSTOM_NAME , item_stack.getHoverName())
                            .build()
            );
            // ent.setCustomName(pStack.getHoverName());
        }
    }

    @Override
    @Deprecated
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState new_state, boolean moving_by_piston)
    {
        // super.onRemove calls removeBlockEntity if the given state is a block entity.
        // If it does, it removes it. The code below just avoids calling the base implementation
        // and rather calls that directly since we have verified that the block entity is our block.
        if (!state.is(new_state.getBlock()) && level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity)
        {
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
            level.removeBlockEntity(pos);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack , context , components , flag);
        if (stack.has(DataComponents.CONTAINER_LOOT)) {
            components.add(UNKNOWN_CONTENTS);
        } else {
            int shown = 0, count = 0;

            for (ItemStack itemstack : (stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)).nonEmptyItems()) {
                ++count;
                if (shown < 5) {
                    shown++;
                    components.add(Component.translatable("container.shulkerBox.itemCount", itemstack.getHoverName(), itemstack.getCount()));
                }
            }

            int more = count - shown;

            if (more > 0) {
                components.add(Component.translatable("container.shulkerBox.more", more).withStyle(ChatFormatting.ITALIC));
            }
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter getter, BlockPos pos) {
        if (getter.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity entity && !entity.isClosed()) {
            return OPEN_SHAPE_BY_DIRECTION.get(state.getValue(FACING).getOpposite());
        } else {
            return Shapes.block();
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext cxt) {
        return level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity entity ? Shapes.create(entity.getBoundingBox(state)) : Shapes.block();
    }

    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) { return false; }

   /**
    * @deprecated call via {@link
    * BlockStateBase#hasAnalogOutputSignal} whenever possible.
    * Implementing/overriding is fine.
    */
    @Override
    @Deprecated
    public boolean hasAnalogOutputSignal(BlockState pState) { return true; }

   /**
    * @deprecated call via {@link
    * BlockStateBase#getAnalogOutputSignal} whenever possible.
    * Implementing/overriding is fine.
    */
    @Override
    @Deprecated
    public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer((Container) pLevel.getBlockEntity(pPos));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(level, pos, state);

        Optional<? extends AbstractIronShulkerBoxBlockEntity> opt = level.getBlockEntity(pos, this.block_ent_type.function());
        if (opt.isPresent()) {
            opt.get().saveToItem(itemstack , level.registryAccess());
        }
        return itemstack;
    }

    public static IronShulkerBoxesTypes getTypeFromBlock(Block blockIn) {
        return blockIn instanceof AbstractIronShulkerBoxBlock b ? b.getType() : IronShulkerBoxesTypes.VANILLA;
    }

    public IronShulkerBoxesTypes getType() { return this.type; }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    *
    * @deprecated call via {@link BlockStateBase#rotate} whenever
    * possible. Implementing/overriding is fine.
    */
    @Override
    @Deprecated
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    *
    * @deprecated call via {@link BlockStateBase#mirror} whenever
    * possible. Implementing/overriding is fine.
    */
    @Override
    @Deprecated
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
