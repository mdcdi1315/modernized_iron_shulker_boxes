package com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity;

import com.github.mdcdi1315.DotNetLayer.System.Func1;
import com.github.mdcdi1315.DotNetLayer.System.Func3;

import com.github.mdcdi1315.basemodslib.block.entity.IBlockEntityFactory;
import com.github.mdcdi1315.basemodslib.block.entity.IBlockEntityRegistrar;
import com.github.mdcdi1315.basemodslib.eventapi.mods.registries.BlockEntityTypeRegistryFinalizedEvent;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class IronShulkerBoxesBlockEntities
{
    private IronShulkerBoxesBlockEntities() {}

    private static final class IronShulkerBoxEntityFactory<T extends AbstractIronShulkerBoxBlockEntity>
        implements IBlockEntityFactory<T>
    {
        private final Func3<BlockPos , BlockState , T> constructor;
        private Func1<Block> block;
        private Block[] blks;

        public IronShulkerBoxEntityFactory(Func3<BlockPos , BlockState , T> constructor , Func1<Block> block) {
            this.constructor = constructor;
            this.block = block;
            blks = null;
        }

        @Override
        public T Create(BlockPos blockPos, BlockState blockState) { return constructor.function(blockPos, blockState); }

        @Override
        public Block[] GetBlocks()
        {
            if (blks == null) {
                blks = new Block[] { block.function() };
                block = null;
            }
            return blks;
        }
    }

    public static BlockEntityType<IronShulkerBoxBlockEntity> IRON_SHULKER_BOX;
    public static BlockEntityType<GoldShulkerBoxBlockEntity> GOLD_SHULKER_BOX;
    public static BlockEntityType<CopperShulkerBoxBlockEntity> COPPER_SHULKER_BOX;
    public static BlockEntityType<DiamondShulkerBoxBlockEntity> DIAMOND_SHULKER_BOX;
    public static BlockEntityType<CrystalShulkerBoxBlockEntity> CRYSTAL_SHULKER_BOX;
    public static BlockEntityType<ObsidianShulkerBoxBlockEntity> OBSIDIAN_SHULKER_BOX;

    public static IronShulkerBoxBlockEntity ITEM_REND_IRON_SHULKER_BOX;
    public static GoldShulkerBoxBlockEntity ITEM_REND_GOLD_SHULKER_BOX;
    public static CopperShulkerBoxBlockEntity ITEM_REND_COPPER_SHULKER_BOX;
    public static DiamondShulkerBoxBlockEntity ITEM_REND_DIAMOND_SHULKER_BOX;
    public static CrystalShulkerBoxBlockEntity ITEM_REND_CRYSTAL_SHULKER_BOX;
    public static ObsidianShulkerBoxBlockEntity ITEM_REND_OBSIDIAN_SHULKER_BOX;

    public static void Initialize(IBlockEntityRegistrar registrar)
    {
        registrar.Register("iron_shulker_box", new IronShulkerBoxEntityFactory<>(IronShulkerBoxBlockEntity::new, () -> IronShulkerBoxesBlocks.IRON_SHULKER_BOX));
        registrar.Register("gold_shulker_box", new IronShulkerBoxEntityFactory<>(GoldShulkerBoxBlockEntity::new , () -> IronShulkerBoxesBlocks.GOLD_SHULKER_BOX));
        registrar.Register("copper_shulker_box", new IronShulkerBoxEntityFactory<>(CopperShulkerBoxBlockEntity::new, () -> IronShulkerBoxesBlocks.COPPER_SHULKER_BOX));
        registrar.Register("diamond_shulker_box", new IronShulkerBoxEntityFactory<>(DiamondShulkerBoxBlockEntity::new, () -> IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX));
        registrar.Register("crystal_shulker_box", new IronShulkerBoxEntityFactory<>(CrystalShulkerBoxBlockEntity::new, () -> IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX));
        registrar.Register("obsidian_shulker_box", new IronShulkerBoxEntityFactory<>(ObsidianShulkerBoxBlockEntity::new, () -> IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX));
    }

    public static void InitializeFields(BlockEntityTypeRegistryFinalizedEvent event)
    {
        IRON_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("iron_shulker_box"));
        GOLD_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("gold_shulker_box"));
        COPPER_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("copper_shulker_box"));
        DIAMOND_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("diamond_shulker_box"));
        CRYSTAL_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("crystal_shulker_box"));
        OBSIDIAN_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("obsidian_shulker_box"));
        ITEM_REND_IRON_SHULKER_BOX = new IronShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.IRON_SHULKER_BOX.defaultBlockState());
        ITEM_REND_COPPER_SHULKER_BOX = new CopperShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.defaultBlockState());
        ITEM_REND_GOLD_SHULKER_BOX = new GoldShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.defaultBlockState());
        ITEM_REND_CRYSTAL_SHULKER_BOX = new CrystalShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.defaultBlockState());
        ITEM_REND_DIAMOND_SHULKER_BOX = new DiamondShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.defaultBlockState());
        ITEM_REND_OBSIDIAN_SHULKER_BOX = new ObsidianShulkerBoxBlockEntity(BlockPos.ZERO, IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.defaultBlockState());
    }
}
