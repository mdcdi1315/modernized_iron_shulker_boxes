package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;

import com.google.common.collect.ImmutableList;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class IronShulkerBoxesModels
{
    private IronShulkerBoxesModels() {}

    static {
        SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE,"entity/shulker/shulker");
        IRON_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_iron");
        GOLD_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_gold");
        COPPER_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_copper");
        CRYSTAL_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_crystal");
        DIAMOND_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_diamond");
        OBSIDIAN_SHULKER_TEXTURE_LOCATION = ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/default/shulker_obsidian");
        List<String> values = IronShulkerBoxColor.AsListWithoutNone();
        IRON_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("iron", color)).collect(ImmutableList.toImmutableList());
        GOLD_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("gold", color)).collect(ImmutableList.toImmutableList());
        COPPER_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("copper", color)).collect(ImmutableList.toImmutableList());
        DIAMOND_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("diamond", color)).collect(ImmutableList.toImmutableList());
        CRYSTAL_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("crystal", color)).collect(ImmutableList.toImmutableList());
        OBSIDIAN_COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map((color) -> getShulkerBoxResourceLocation("obsidian", color)).collect(ImmutableList.toImmutableList());
        COLORED_SHULKER_TEXTURE_LOCATION = values.stream().map(IronShulkerBoxesModels::getShulkerBoxResourceLocation).collect(ImmutableList.toImmutableList());
    }

    public static final List<ResourceLocation> IRON_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> GOLD_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> DIAMOND_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> COPPER_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> CRYSTAL_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> OBSIDIAN_COLORED_SHULKER_TEXTURE_LOCATION;
    public static final List<ResourceLocation> COLORED_SHULKER_TEXTURE_LOCATION;

    public static final ResourceLocation SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation IRON_SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation GOLD_SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation COPPER_SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation DIAMOND_SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation CRYSTAL_SHULKER_TEXTURE_LOCATION;
    public static final ResourceLocation OBSIDIAN_SHULKER_TEXTURE_LOCATION;

    private static ResourceLocation getShulkerBoxResourceLocation(String typeName, String colorName) {
        return ResourceLocation.tryBuild(IronShulkerBoxesModInstance.MOD_ID, "model/" + colorName + "/shulker_" + colorName + "_" + typeName);
    }

    private static ResourceLocation getShulkerBoxResourceLocation(String colorName) {
        return ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE ,"entity/shulker/shulker_" + colorName);
    }

    public static ResourceLocation chooseShulkerBoxTexture(IronShulkerBoxesTypes type, int dyeColor) {
        return switch (type) {
            case IRON -> IRON_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            case GOLD -> GOLD_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            case DIAMOND -> DIAMOND_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            case COPPER -> COPPER_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            case CRYSTAL -> CRYSTAL_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            case OBSIDIAN -> OBSIDIAN_COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
            default -> COLORED_SHULKER_TEXTURE_LOCATION.get(dyeColor);
        };
    }

    public static ResourceLocation chooseShulkerBoxTexture(IronShulkerBoxesTypes type) {
        return switch (type) {
            case IRON -> IRON_SHULKER_TEXTURE_LOCATION;
            case GOLD -> GOLD_SHULKER_TEXTURE_LOCATION;
            case DIAMOND -> DIAMOND_SHULKER_TEXTURE_LOCATION;
            case COPPER -> COPPER_SHULKER_TEXTURE_LOCATION;
            case CRYSTAL -> CRYSTAL_SHULKER_TEXTURE_LOCATION;
            case OBSIDIAN -> OBSIDIAN_SHULKER_TEXTURE_LOCATION;
            default -> SHULKER_TEXTURE_LOCATION;
        };
    }
}
