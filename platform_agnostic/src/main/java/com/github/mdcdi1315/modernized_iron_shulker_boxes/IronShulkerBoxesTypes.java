package com.github.mdcdi1315.modernized_iron_shulker_boxes;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;

import com.github.mdcdi1315.basemodslib.utils.ISynchronized;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum IronShulkerBoxesTypes
        implements StringRepresentable, ISynchronized
{
    IRON(54),
    GOLD(81),
    COPPER(45),
    DIAMOND(108),
    CRYSTAL(108),
    OBSIDIAN(132),
    NETHERITE(169),
    VANILLA(0);

    private final byte size;

    IronShulkerBoxesTypes(int size) { this.size = (byte)size; }

    @NotNull
    @Override
    public String getSerializedName() { return this.name(); }

    // The container size is now currently stored as a byte, for storage packing.
    // However, Java does not provide for us an actual 'byte'; this happens because all numeric types
    // are handled as signed integer values - as such to use the power of packing and to ensure that we
    // will report back the correct size, we upcast to 'int' (which otherwise the containers do need in fact)
    // and AND it with the 0xFF value - equal to 255 in decimal.
    // This allows us to save three bytes, which is a great deal in heavily patched Minecraft environments.
    public int GetSize() { return this.size & 0xFF; }

    public boolean IsTransparent() { return this == CRYSTAL; }

    @NotNull
    public String GetId() { return this.name().toLowerCase(Locale.ROOT); }
}
