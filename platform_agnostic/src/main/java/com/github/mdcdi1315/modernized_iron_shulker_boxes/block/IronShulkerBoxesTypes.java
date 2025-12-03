package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public enum IronShulkerBoxesTypes
        implements StringRepresentable
{
    IRON(54, 9, 184, 222, IronShulkerBoxesModInstance.ID("textures/gui/iron_container.png"), 256, 256),
    GOLD(81, 9, 184, 276, IronShulkerBoxesModInstance.ID("textures/gui/gold_container.png"), 256, 276),
    DIAMOND(108, 12, 238, 276, IronShulkerBoxesModInstance.ID("textures/gui/diamond_container.png"), 256, 276),
    COPPER(45, 9, 184, 204, IronShulkerBoxesModInstance.ID("textures/gui/copper_container.png"), 256, 256),
    CRYSTAL(108, 12, 238, 276, IronShulkerBoxesModInstance.ID("textures/gui/diamond_container.png"), 256, 276),
    OBSIDIAN(132, 12, 238, 312, IronShulkerBoxesModInstance.ID("textures/gui/obsidian_container.png"), 256, 384),
    VANILLA(0, 0, 0, 0, ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE,"textures/gui/container/shulker_box.png"), 0, 0);

    private final String name;
    public final int size;
    public final int rowLength; // The number of item stacks presented in a single row.
    public final int xSize; // The row pixels actually used by the texture.
    public final int ySize; // The column pixels actually used by the texture.
    public final ResourceLocation guiTexture;
    public final int textureXSize;
    public final int textureYSize;

    IronShulkerBoxesTypes(int size, int rowLength, int xSize, int ySize, ResourceLocation guiTexture, int textureXSize, int textureYSize) {
        this.name = this.name();
        this.size = size;
        this.rowLength = rowLength;
        this.xSize = xSize;
        this.ySize = ySize;
        this.guiTexture = guiTexture;
        this.textureXSize = textureXSize;
        this.textureYSize = textureYSize;
    }

    public String getId() {
    return this.name().toLowerCase(Locale.ROOT);
  }

    public String getEnglishName() {
    return this.name;
  }

    @Override
    public String getSerializedName() {
    return this.getEnglishName();
  }

    public int getRowCount() { return this.size / this.rowLength; }

    public boolean isTransparent() { return this == CRYSTAL; }

    public static BlockState get(IronShulkerBoxesTypes type, IronShulkerBoxColor color) {
        if (color == null) {
            return switch (type) {
                case IRON -> IronShulkerBoxesBlocks.IRON_SHULKER_BOX.defaultBlockState();
                case GOLD -> IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.defaultBlockState();
                case DIAMOND -> IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.defaultBlockState();
                case CRYSTAL -> IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.defaultBlockState();
                case COPPER -> IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.defaultBlockState();
                case OBSIDIAN -> IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.defaultBlockState();
                default -> Blocks.SHULKER_BOX.defaultBlockState();
            };
        } else {
            return switch (type) {
                case IRON -> IronShulkerBoxesBlocks.IRON_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                case GOLD -> IronShulkerBoxesBlocks.GOLD_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                case DIAMOND -> IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                case CRYSTAL -> IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                case COPPER -> IronShulkerBoxesBlocks.COPPER_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                case OBSIDIAN -> IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX.defaultBlockState().setValue(AbstractIronShulkerBoxBlock.COLOR , color);
                default -> switch (color) {
                    case NONE -> null;
                    case WHITE -> Blocks.WHITE_SHULKER_BOX.defaultBlockState();
                    case ORANGE -> Blocks.ORANGE_SHULKER_BOX.defaultBlockState();
                    case MAGENTA -> Blocks.MAGENTA_SHULKER_BOX.defaultBlockState();
                    case LIGHT_BLUE -> Blocks.LIGHT_BLUE_SHULKER_BOX.defaultBlockState();
                    case YELLOW -> Blocks.YELLOW_SHULKER_BOX.defaultBlockState();
                    case LIME -> Blocks.LIME_SHULKER_BOX.defaultBlockState();
                    case PINK -> Blocks.PINK_SHULKER_BOX.defaultBlockState();
                    case GRAY -> Blocks.GRAY_SHULKER_BOX.defaultBlockState();
                    case LIGHT_GRAY -> Blocks.LIGHT_GRAY_SHULKER_BOX.defaultBlockState();
                    case CYAN -> Blocks.CYAN_SHULKER_BOX.defaultBlockState();
                    case PURPLE -> Blocks.PURPLE_SHULKER_BOX.defaultBlockState();
                    case BLUE -> Blocks.BLUE_SHULKER_BOX.defaultBlockState();
                    case BROWN -> Blocks.BROWN_SHULKER_BOX.defaultBlockState();
                    case GREEN -> Blocks.GREEN_SHULKER_BOX.defaultBlockState();
                    case RED -> Blocks.RED_SHULKER_BOX.defaultBlockState();
                    case BLACK -> Blocks.BLACK_SHULKER_BOX.defaultBlockState();
                };
            };
        }
    }
}
