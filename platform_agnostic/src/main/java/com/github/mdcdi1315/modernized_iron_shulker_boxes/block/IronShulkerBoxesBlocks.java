package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.basemodslib.block.IBlockRegistrar;
import com.github.mdcdi1315.basemodslib.block.BlockRegistrationInformation;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.creative_mode_tab.IronShulkerBoxesCreativeModeTabs;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class IronShulkerBoxesBlocks
{
    private IronShulkerBoxesBlocks() {}

    public static IronShulkerBoxBlock IRON_SHULKER_BOX;
    public static CopperShulkerBoxBlock COPPER_SHULKER_BOX;
    public static GoldShulkerBoxBlock GOLD_SHULKER_BOX;
    public static ObsidianShulkerBoxBlock OBSIDIAN_SHULKER_BOX;
    public static CrystalShulkerBoxBlock CRYSTAL_SHULKER_BOX;
    public static DiamondShulkerBoxBlock DIAMOND_SHULKER_BOX;

    public static void Initialize(IBlockRegistrar registrar)
    {
        registrar.Register("iron_shulker_box" , new BlockRegistrationInformation(
                (rl) -> IRON_SHULKER_BOX = new IronShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("copper_shulker_box" , new BlockRegistrationInformation(
                (rl) -> COPPER_SHULKER_BOX = new CopperShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("gold_shulker_box" , new BlockRegistrationInformation(
                (rl) -> GOLD_SHULKER_BOX = new GoldShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("crystal_shulker_box" , new BlockRegistrationInformation(
                (rl) -> CRYSTAL_SHULKER_BOX = new CrystalShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("diamond_shulker_box" , new BlockRegistrationInformation(
                (rl) -> DIAMOND_SHULKER_BOX = new DiamondShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("obsidian_shulker_box" , new BlockRegistrationInformation(
                (rl) -> OBSIDIAN_SHULKER_BOX = new ObsidianShulkerBoxBlock(BlockBehaviour.Properties.of()),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
    }

    private static Item ItemGetter(Block b , ResourceLocation location) {
        IronShulkerBoxItem.Builder builder = new IronShulkerBoxItem.Builder()
                .WithUnderlyingBlock((AbstractIronShulkerBoxBlock)b)
                .WithProperties(new Item.Properties());
        Item result = null;
        if (b instanceof IronShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_IRON_SHULKER_BOX);
            result = IronShulkerBoxesItems.IRON_SHULKER_BOX = builder.Build();
        } else if (b instanceof CopperShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_COPPER_SHULKER_BOX);
            result = IronShulkerBoxesItems.COPPER_SHULKER_BOX = builder.Build();
        } else if (b instanceof GoldShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_GOLD_SHULKER_BOX);
            result = IronShulkerBoxesItems.GOLD_SHULKER_BOX = builder.Build();
        } else if (b instanceof CrystalShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_CRYSTAL_SHULKER_BOX);
            result = IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX = builder.Build();
        } else if (b instanceof DiamondShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_DIAMOND_SHULKER_BOX);
            result = IronShulkerBoxesItems.DIAMOND_SHULKER_BOX = builder.Build();
        } else if (b instanceof ObsidianShulkerBoxBlock) {
            builder.WithBlockEntityRenderingFunction(() -> IronShulkerBoxesBlockEntities.ITEM_REND_OBSIDIAN_SHULKER_BOX);
            result = IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX = builder.Build();
        }
        return result;
    }
}
