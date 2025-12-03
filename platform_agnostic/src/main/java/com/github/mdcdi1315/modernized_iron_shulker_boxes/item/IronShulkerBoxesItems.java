package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import com.github.mdcdi1315.DotNetLayer.System.InvalidOperationException;

import com.github.mdcdi1315.basemodslib.item.IItemRegistrar;
import com.github.mdcdi1315.basemodslib.item.ItemRegistrationInformation;
import com.github.mdcdi1315.basemodslib.item.datacomponents.DataComponentTypeRegistrationInformation;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.*;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.creative_mode_tab.IronShulkerBoxesCreativeModeTabs;

import net.minecraft.world.item.Item;

public final class IronShulkerBoxesItems
{
    private IronShulkerBoxesItems() {}

    // Shulker box items - these are initialized via the IronShulkerBoxesBlocks class
    public static Item IRON_SHULKER_BOX;
    public static Item GOLD_SHULKER_BOX;
    public static Item COPPER_SHULKER_BOX;
    public static Item CRYSTAL_SHULKER_BOX;
    public static Item DIAMOND_SHULKER_BOX;
    public static Item OBSIDIAN_SHULKER_BOX;

    public static Item ResolveShulkerBoxItemByBlock(AbstractIronShulkerBoxBlock blk)
    {
        return switch (blk) {
            case IronShulkerBoxBlock i -> IronShulkerBoxesItems.IRON_SHULKER_BOX;
            case CopperShulkerBoxBlock c -> IronShulkerBoxesItems.COPPER_SHULKER_BOX;
            case GoldShulkerBoxBlock g -> IronShulkerBoxesItems.GOLD_SHULKER_BOX;
            case CrystalShulkerBoxBlock c -> IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX;
            case DiamondShulkerBoxBlock d -> IronShulkerBoxesItems.DIAMOND_SHULKER_BOX;
            case ObsidianShulkerBoxBlock o -> IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX;
            case null, default -> throw new InvalidOperationException("Attempted to create a non-existent item!!!");
        };
    }

    private static void RegisterDefaultUpgradeItems(IItemRegistrar registrar)
    {
        registrar.Register("copper_to_iron_shulker_box_upgrade", new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxUpgradeItem(
                        IronShulkerBoxesBlocks.COPPER_SHULKER_BOX,
                        IronShulkerBoxesBlocks.IRON_SHULKER_BOX
                ),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
        registrar.Register("iron_to_gold_shulker_box_upgrade", new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxUpgradeItem(
                        IronShulkerBoxesBlocks.IRON_SHULKER_BOX,
                        IronShulkerBoxesBlocks.GOLD_SHULKER_BOX
                ),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
        registrar.Register("gold_to_diamond_shulker_box_upgrade" , new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxUpgradeItem(
                        IronShulkerBoxesBlocks.GOLD_SHULKER_BOX,
                        IronShulkerBoxesBlocks.IRON_SHULKER_BOX
                ),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
        registrar.Register("diamond_to_obsidian_shulker_box_upgrade" , new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxUpgradeItem(
                        IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX,
                        IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX
                ),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
        registrar.Register("diamond_to_crystal_shulker_box_upgrade" , new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxUpgradeItem(
                        IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX,
                        IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX
                ),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
    }

    private static void RegisterVanillaUpgradeItems(IItemRegistrar registrar)
    {
        registrar.Register("vanilla_to_copper_shulker_box_upgrade" , new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxVanillaUpgradeItem(IronShulkerBoxesBlocks.COPPER_SHULKER_BOX),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
        registrar.Register("vanilla_to_iron_shulker_box_upgrade" , new ItemRegistrationInformation(
                (rl) -> new IronShulkerBoxVanillaUpgradeItem(IronShulkerBoxesBlocks.IRON_SHULKER_BOX),
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
    }

    public static void Initialize(IItemRegistrar registrar)
    {
        registrar.RegisterDataComponentType("i_s_box_color", new DataComponentTypeRegistrationInformation<>(IronShulkerBoxColorDataComponentType::GetInstance));
        IronShulkerBoxesCreativeModeTabs.Register(registrar);
        RegisterDefaultUpgradeItems(registrar);
        RegisterVanillaUpgradeItems(registrar);
    }
}
