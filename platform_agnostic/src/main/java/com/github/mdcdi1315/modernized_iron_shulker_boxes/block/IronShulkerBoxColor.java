package com.github.mdcdi1315.modernized_iron_shulker_boxes.block;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentException;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.basemodslib.codecs.EnumCodec;

import com.mojang.serialization.Codec;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public enum IronShulkerBoxColor
{
    NONE(0),
    WHITE(1),
    ORANGE(2),
    MAGENTA(3),
    LIGHT_BLUE(4),
    YELLOW(5),
    LIME(6),
    PINK(7),
    GRAY(8),
    LIGHT_GRAY(9),
    CYAN(10),
    PURPLE(11),
    BLUE(12),
    BROWN(13),
    GREEN(14),
    RED(15),
    BLACK(16);

    private final byte variant_id;

    public static final Codec<IronShulkerBoxColor> CODEC = new EnumCodec<>(IronShulkerBoxColor.class);

    @MaybeNull
    public static IronShulkerBoxColor FromVariantID(byte variant_id)
    {
        for (IronShulkerBoxColor color : values())
        {
            if (color.variant_id == variant_id) {
                return color;
            }
        }
        return null;
    }

    public static IronShulkerBoxColor FromVariantIDChecked(byte variant_id)
    {
        for (IronShulkerBoxColor color : values()) {
            if (color.variant_id == variant_id) { return color; }
        }
        throw new ArgumentException(String.format("The specified variant ID is not one of the members of the Iron Shulker Box color enumeration: %d" , variant_id), "variant_id");
    }

    IronShulkerBoxColor(int variant_id) {
        this.variant_id = (byte) variant_id;
    }

    public byte GetVariantID() { return variant_id; }

    public String GetNamespacedID() { return name().toLowerCase(Locale.ROOT); }

    @MaybeNull
    public DyeColor AsDyeColor()
    {
        return switch (this) {
            case NONE -> null;
            case WHITE -> DyeColor.WHITE;
            case ORANGE -> DyeColor.ORANGE;
            case MAGENTA -> DyeColor.MAGENTA;
            case LIGHT_BLUE -> DyeColor.LIGHT_BLUE;
            case YELLOW -> DyeColor.YELLOW;
            case LIME -> DyeColor.LIME;
            case PINK -> DyeColor.PINK;
            case GRAY -> DyeColor.GRAY;
            case LIGHT_GRAY -> DyeColor.LIGHT_GRAY;
            case CYAN -> DyeColor.CYAN;
            case PURPLE -> DyeColor.PURPLE;
            case BLUE -> DyeColor.BLUE;
            case BROWN -> DyeColor.BROWN;
            case GREEN -> DyeColor.GREEN;
            case RED -> DyeColor.RED;
            case BLACK -> DyeColor.BLACK;
        };
    }

    public static IronShulkerBoxColor FromDyeColor(DyeColor color)
    {
        return switch (color) {
            case WHITE -> WHITE;
            case ORANGE -> ORANGE;
            case MAGENTA -> MAGENTA;
            case LIGHT_BLUE -> LIGHT_BLUE;
            case YELLOW -> YELLOW;
            case LIME -> LIME;
            case PINK -> PINK;
            case GRAY -> GRAY;
            case LIGHT_GRAY -> LIGHT_GRAY;
            case CYAN -> CYAN;
            case PURPLE -> PURPLE;
            case BLUE -> BLUE;
            case BROWN -> BROWN;
            case GREEN -> GREEN;
            case RED -> RED;
            case BLACK -> BLACK;
        };
    }

    public static List<String> AsListWithoutNone()
    {
        ArrayList<String> sl = new ArrayList<>(10);
        for (IronShulkerBoxColor c : values()) {
            if (c != NONE) { sl.add(c.GetNamespacedID()); }
        }
        return sl;
    }
}
