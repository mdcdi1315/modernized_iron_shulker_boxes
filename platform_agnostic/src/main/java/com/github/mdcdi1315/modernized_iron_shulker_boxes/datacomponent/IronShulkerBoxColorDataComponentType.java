package com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;

import com.mojang.serialization.Codec;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.component.DataComponentType;

/**
 * Data component type for de/encoding iron shulker boxes color variant.
 * It acts also as an aggregate object during registration as it implements the function to pass to the registration method.
 */
public final class IronShulkerBoxColorDataComponentType
    implements DataComponentType<IronShulkerBoxColor>
{
    private final NetCodec network_codec;

    public static final IronShulkerBoxColorDataComponentType INSTANCE = new IronShulkerBoxColorDataComponentType();

    public static IronShulkerBoxColorDataComponentType GetInstance() { return INSTANCE; }

    private IronShulkerBoxColorDataComponentType() { network_codec = new NetCodec(); }

    // We don't need a registry-friendly byte buffer, the netty's one for this usage is more than OK.
    private static final class NetCodec
        implements StreamCodec<ByteBuf , IronShulkerBoxColor>
    {
        @NotNull
        @Override
        public IronShulkerBoxColor decode(ByteBuf buffer) {
            return IronShulkerBoxColor.FromVariantIDChecked(buffer.readByte());
        }

        @Override
        public void encode(ByteBuf buffer, IronShulkerBoxColor color) {
            buffer.writeByte(color.GetVariantID());
        }
    }

    @Override
    public Codec<IronShulkerBoxColor> codec() { return IronShulkerBoxColor.CODEC; }

    @NotNull
    @Override
    public StreamCodec<ByteBuf, IronShulkerBoxColor> streamCodec() { return network_codec; }
}
