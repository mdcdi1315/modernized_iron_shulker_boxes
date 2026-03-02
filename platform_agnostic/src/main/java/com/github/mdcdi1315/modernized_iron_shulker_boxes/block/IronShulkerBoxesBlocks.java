package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;

import com.github.mdcdi1315.basemodslib.block.IBlockRegistrar;
import com.github.mdcdi1315.basemodslib.block.BlockRegistrationInformation;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.creative_mode_tab.IronShulkerBoxesCreativeModeTabs;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class IronShulkerBoxesBlocks
{
    private IronShulkerBoxesBlocks() {}

    public static IronShulkerBoxBlock IRON_SHULKER_BOX;
    public static GoldShulkerBoxBlock GOLD_SHULKER_BOX;
    public static CopperShulkerBoxBlock COPPER_SHULKER_BOX;
    public static DiamondShulkerBoxBlock DIAMOND_SHULKER_BOX;
    public static CrystalShulkerBoxBlock CRYSTAL_SHULKER_BOX;
    public static ObsidianShulkerBoxBlock OBSIDIAN_SHULKER_BOX;
    public static NetheriteShulkerBoxBlock NETHERITE_SHULKER_BOX;

    private static String ConstructExactDescriptionID(ResourceLocation location)
            throws ArgumentNullException
    {
        ArgumentNullException.ThrowIfNull(location , "location");
        String namespace = location.getNamespace();
        if (namespace.isEmpty()) {
            return String.format("block.UNKNOWN.%s", location.getPath().replace('/' , '.'));
        } else {
            return String.format("block.%s.%s" , namespace , location.getPath().replace('/' , '.'));
        }
    }

    private static BlockBehaviour.Properties ConstructProperties(ResourceLocation location)
    {
        return BlockBehaviour.Properties.of()
                .overrideDescription(ConstructExactDescriptionID(location))
                .setId(ResourceKey.create(Registries.BLOCK, location));
    }

    public static void Initialize(IBlockRegistrar registrar)
    {
        registrar.Register("iron_shulker_box" , new BlockRegistrationInformation(
                (rl) -> IRON_SHULKER_BOX = new IronShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("copper_shulker_box" , new BlockRegistrationInformation(
                (rl) -> COPPER_SHULKER_BOX = new CopperShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("gold_shulker_box" , new BlockRegistrationInformation(
                (rl) -> GOLD_SHULKER_BOX = new GoldShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("crystal_shulker_box" , new BlockRegistrationInformation(
                (rl) -> CRYSTAL_SHULKER_BOX = new CrystalShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("diamond_shulker_box" , new BlockRegistrationInformation(
                (rl) -> DIAMOND_SHULKER_BOX = new DiamondShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("obsidian_shulker_box" , new BlockRegistrationInformation(
                (rl) -> OBSIDIAN_SHULKER_BOX = new ObsidianShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));

        registrar.Register("netherite_shulker_box", new BlockRegistrationInformation(
                (rl) -> NETHERITE_SHULKER_BOX = new NetheriteShulkerBoxBlock(ConstructProperties(rl)),
                IronShulkerBoxesBlocks::ItemGetter,
                IronShulkerBoxesCreativeModeTabs.IRON_SHULKER_BOXES
        ));
    }

    private static Item ItemGetter(Block b , ResourceLocation location)
    {
        AbstractIronShulkerBoxBlock g = (AbstractIronShulkerBoxBlock) b;
        IronShulkerBoxItem it = new IronShulkerBoxItem(g, new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, location))
                .overrideDescription(ConstructExactDescriptionID(location)));
        return switch (g) {
            case CopperShulkerBoxBlock c -> IronShulkerBoxesItems.COPPER_SHULKER_BOX = it;
            case IronShulkerBoxBlock i -> IronShulkerBoxesItems.IRON_SHULKER_BOX = it;
            case GoldShulkerBoxBlock gb -> IronShulkerBoxesItems.GOLD_SHULKER_BOX = it;
            case DiamondShulkerBoxBlock d -> IronShulkerBoxesItems.DIAMOND_SHULKER_BOX = it;
            case ObsidianShulkerBoxBlock o -> IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX = it;
            case NetheriteShulkerBoxBlock n -> IronShulkerBoxesItems.NETHERITE_SHULKER_BOX = it;
            case CrystalShulkerBoxBlock c -> IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX = it;
            case null, default -> it;
        };
    }
}
