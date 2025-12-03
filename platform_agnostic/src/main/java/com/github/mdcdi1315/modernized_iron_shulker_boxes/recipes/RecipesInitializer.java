package com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes;

import com.github.mdcdi1315.basemodslib.registries.IRegistryRegistrar;
import com.github.mdcdi1315.basemodslib.registries.RegistryObjectSupplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class RecipesInitializer
{
    private RecipesInitializer() {}

    private static class ColoringCraftingSerializerGetter
        extends RegistryObjectSupplier<RecipeSerializer<?>>
    {
        @Override
        protected ColoringCraftingRecipeSerializer Get(ResourceLocation location) {
            return ColoringCraftingRecipeSerializer.INSTANCE;
        }
    }

    public static void Initialize(IRegistryRegistrar registrar) {
        registrar.RegisterObject(Registries.RECIPE_SERIALIZER, "iron_shulker_box_crafting_coloring", new ColoringCraftingSerializerGetter());
    }
}
