package com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes;

import com.github.mdcdi1315.basemodslib.codecs.CodecUtils;
import com.github.mdcdi1315.basemodslib.registries.RegistryUtils;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class ColoringCraftingRecipeSerializer
    implements RecipeSerializer<ColoringCraftingRecipe>
{
    private final NetCodec stream_codec;
    private final MapCodec<ColoringCraftingRecipe> codec;

    private static final class NetCodec
        implements StreamCodec<RegistryFriendlyByteBuf, ColoringCraftingRecipe>
    {
        @Override
        public ColoringCraftingRecipe decode(RegistryFriendlyByteBuf buffer)
        {
            IronShulkerBoxColor c = IronShulkerBoxColor.FromVariantID(buffer.readByte());
            String group = buffer.readUtf();
            IronShulkerBoxItem i = (IronShulkerBoxItem) RegistryUtils.GetRegistryObjectChecked(BuiltInRegistries.ITEM , ResourceLocation.parse(buffer.readUtf()));
            return new ColoringCraftingRecipe(c, i, group);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buffer, ColoringCraftingRecipe recipe)
        {
            buffer.writeByte(recipe.GetColor().GetVariantID());
            buffer.writeUtf(recipe.getGroup());
            buffer.writeUtf(BuiltInRegistries.ITEM.getKey(recipe.GetShulkerBox()).toString());
        }
    }

    private ColoringCraftingRecipeSerializer()
    {
        codec = CodecUtils.CreateMapCodecDirect(
                IronShulkerBoxColor.CODEC.fieldOf("color").forGetter(ColoringCraftingRecipe::GetColor),
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("shulker_box").forGetter(ColoringCraftingRecipe::GetShulkerBox),
                Codec.STRING.optionalFieldOf("group", "").forGetter(ColoringCraftingRecipe::getGroup),
                ColoringCraftingRecipe::new
        );
        stream_codec = new NetCodec();
    }

    public static final ColoringCraftingRecipeSerializer INSTANCE = new ColoringCraftingRecipeSerializer();

    @Override
    public MapCodec<ColoringCraftingRecipe> codec() { return codec; }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ColoringCraftingRecipe> streamCodec() { return stream_codec; }
}
