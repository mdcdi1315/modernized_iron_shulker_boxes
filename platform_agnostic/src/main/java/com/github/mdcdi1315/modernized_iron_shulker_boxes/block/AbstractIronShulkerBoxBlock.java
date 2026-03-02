package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.basemodslib.codecs.CodecUtils;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxUpgradeItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import com.google.common.collect.Maps;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.*;
import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.PushReaction;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractIronShulkerBoxBlock
        extends BaseEntityBlock
{
    private static final Map<Direction, VoxelShape> OPEN_SHAPE_BY_DIRECTION = CreateOpenShapeByDirection();

    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = IronShulkerBoxesModInstance.ID("contents");
    public static final Property<IronShulkerBoxColor> COLOR = new IronShulkerBoxColorProperty("isb_color");

    private final IronShulkerBoxesTypes type;

    private static Map<Direction, VoxelShape> CreateOpenShapeByDirection()
    {
        Map<Direction, VoxelShape> m = Maps.newEnumMap(Direction.class);
        m.put(Direction.NORTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D)); // NORTH_OPEN_AABB
        m.put(Direction.EAST, Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)); // EAST_OPEN_AABB
        m.put(Direction.SOUTH, Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D)); // SOUTH_OPEN_AABB
        m.put(Direction.WEST, Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D)); // WEST_OPEN_AABB
        m.put(Direction.UP, Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D)); // UP_OPEN_AABB
        m.put(Direction.DOWN, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)); // DOWN_OPEN_AABB
        return m;
    }

    protected static <T extends AbstractIronShulkerBoxBlock> MapCodec<T> CreateMapCodecForIronShulkerBlock(Function<Properties, T> constructor) { return CodecUtils.CreateMapCodecDirect(propertiesCodec(), constructor); }

    private record SuffocatingViewBlockingPredicate()
        implements StatePredicate
    {
        private SuffocatingViewBlockingPredicate {}

        public static final SuffocatingViewBlockingPredicate INSTANCE = new SuffocatingViewBlockingPredicate();

        @Override
        public boolean test(BlockState state, BlockGetter getter, BlockPos position)
        {
            if (getter.getBlockEntity(position) instanceof AbstractIronShulkerBoxBlockEntity entity) {
                return entity.IsClosed();
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
            IronShulkerBoxesTypes type
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

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(COLOR , IronShulkerBoxColor.NONE));
    }

    /**
     * Method for getting the block entity type directly. <br />
     * Avoids to store the function to the block entity itself, which it might be computationally expensive.
     * @return The block entity type for the current iron shulker box block.
     */
    public abstract BlockEntityType<? extends AbstractIronShulkerBoxBlockEntity> GetBlockEntityType();

    @Override
    @MaybeNull
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> server_block_entity) {
        return createTickerHelper(server_block_entity, GetBlockEntityType(), AbstractIronShulkerBoxBlockEntity::tick);
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
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return (stack.getItem() instanceof IronShulkerBoxUpgradeItem) ? InteractionResult.FAIL : InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        if (level instanceof ServerLevel sl &&
                sl.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity block_ent &&
                CanOpen(state, sl, pos, block_ent)
        ) {
            player.openMenu(block_ent);
            player.awardStat(Stats.OPEN_SHULKER_BOX);
            // Already that it is a server level was validated with the first if statement.
            PiglinAi.angerNearbyPiglins(sl, player, true);
        }

        return InteractionResult.SUCCESS;
    }

    private static boolean CanOpen(BlockState state, Level level, BlockPos pos, AbstractIronShulkerBoxBlockEntity p_entity)
    {
        return p_entity.getAnimationStatus() != AbstractIronShulkerBoxBlockEntity.AnimationStatus.CLOSED || level.noCollision(
                Shulker
                        .getProgressDeltaAabb(1F, state.getValue(FACING), 0.0F, 0.5F, pos.getBottomCenter())
                        .deflate(1.0E-6D)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext cxt) {
        return this.defaultBlockState()
                .setValue(FACING, cxt.getClickedFace())
                .setValue(COLOR, Objects.requireNonNullElse(
                        cxt.getItemInHand().get((DataComponentType<IronShulkerBoxColor>) IronShulkerBoxColorDataComponentType.INSTANCE),
                        IronShulkerBoxColor.NONE
                ));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING, COLOR); }

    /**
     * Called before the Block is set to air in the world.
     * Called regardless of if the player's tool can actually collect this block.
     * @return
     */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity isb_entity) {
            if (!level.isClientSide && player.preventsBlockDrops() && !isb_entity.isEmpty()) {
                ItemStack itemstack = new ItemStack(IronShulkerBoxesItems.ResolveShulkerBoxItemByBlock(this), 1);
                itemstack.applyComponents(isb_entity.collectComponents());
                itemstack.set(IronShulkerBoxColorDataComponentType.INSTANCE, state.getValue(COLOR));
                ItemEntity itementity = new ItemEntity(level ,pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            } else {
                isb_entity.unpackLootTable(player);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
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

    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved_by_piston) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    /* We don't need this anymore see the method above ^
    @Override
    @Deprecated
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState new_state, boolean moving_by_piston)
    {
        super.affectNeighborsAfterRemoval();
        // super.onRemove calls removeBlockEntity if the given state is a block entity.
        // If it does, it removes it. The code below just avoids calling the base implementation
        // and rather calls that directly since we have verified that the block entity is our block.
        if (!state.is(new_state.getBlock()) && level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity)
        {
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
            level.removeBlockEntity(pos);
        }
    }
     */

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter getter, BlockPos pos) {
        if (getter.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity entity && !entity.IsClosed()) {
            return OPEN_SHAPE_BY_DIRECTION.get(state.getValue(FACING).getOpposite());
        } else {
            return Shapes.block();
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext cxt) {
        return level.getBlockEntity(pos) instanceof AbstractIronShulkerBoxBlockEntity entity ? Shapes.create(entity.getBoundingBox(state)) : Shapes.block();
    }

    protected boolean propagatesSkylightDown(BlockState state) { return false; }

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
    public int getAnalogOutputSignal(BlockState block_state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    public static IronShulkerBoxesTypes getTypeFromBlock(Block blockIn) {
        return blockIn instanceof AbstractIronShulkerBoxBlock b ? b.GetType() : IronShulkerBoxesTypes.VANILLA;
    }

    public IronShulkerBoxesTypes GetType() { return this.type; }

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
