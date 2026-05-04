package com.github.mdcdi1315.modernized_iron_shulker_boxes.datagen;

import com.github.mdcdi1315.basemodslib.registries.RegistryUtils;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.world.item.Item;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipesGenerator
    extends FabricRecipeProvider
{
    public RecipesGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) { return new Provider(registryLookup, exporter); }

    public static final class Provider
        extends RecipeProvider
    {
        public Provider(HolderLookup.Provider registries, RecipeOutput output) { super(registries, output); }

        @Override
        public void buildRecipes()
        {
            for (Item item : List.of(
                    IronShulkerBoxesItems.COPPER_SHULKER_BOX,
                    IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX,
                    IronShulkerBoxesItems.IRON_SHULKER_BOX,
                    IronShulkerBoxesItems.DIAMOND_SHULKER_BOX,
                    IronShulkerBoxesItems.GOLD_SHULKER_BOX,
                    IronShulkerBoxesItems.NETHERITE_SHULKER_BOX,
                    IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX
            )) {
                for (IronShulkerBoxColor color : IronShulkerBoxColor.values())
                {
                    if (color == IronShulkerBoxColor.NONE) { continue; }
                    ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
                    new ColoredBoxesRecipeBuilder(item)
                            .WithColor(color)
                            .group("modernized_iron_shulker_boxes:shulker_box_coloring_" + color.GetNamespacedID())
                            .save(
                                    this.output,
                                    ResourceKey.create(
                                            Registries.RECIPE,
                                            RegistryUtils.ConstructResourceLocation(
                                                    id.getNamespace(),
                                                    "shulker_boxes_coloring/" + color.GetNamespacedID() + "_" + id.getPath() + "_recipe"
                                            )
                                    )
                            );
                }
            }
        }
    }

    @Override
    public String getName()
    {
        return "Colorized recipes and advancements generator";
    }
}
