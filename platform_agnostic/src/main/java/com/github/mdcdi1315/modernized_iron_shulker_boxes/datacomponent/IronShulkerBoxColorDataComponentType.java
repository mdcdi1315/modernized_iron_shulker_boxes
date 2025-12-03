package com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;

import com.mojang.serialization.Codec;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.component.DataComponentType;

public final class IronShulkerBoxColorDataComponentType
    implements DataComponentType<IronShulkerBoxColor>
{
    private final NetCodec network_codec;

    public static final IronShulkerBoxColorDataComponentType INSTANCE = new IronShulkerBoxColorDataComponentType();

    public static IronShulkerBoxColorDataComponentType GetInstance() { return INSTANCE; }

    private IronShulkerBoxColorDataComponentType() { network_codec = new NetCodec(); }

    private static final class NetCodec
        implements StreamCodec<RegistryFriendlyByteBuf , IronShulkerBoxColor>
    {
        @NotNull
        @Override
        public IronShulkerBoxColor decode(RegistryFriendlyByteBuf buffer) {
            return IronShulkerBoxColor.FromVariantIDChecked(buffer.readByte());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, IronShulkerBoxColor color) {
            buffer.writeByte(color.GetVariantID());
        }
    }

    @Override
    public Codec<IronShulkerBoxColor> codec() { return IronShulkerBoxColor.CODEC; }

    @NotNull
    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, IronShulkerBoxColor> streamCodec() { return network_codec; }
}
