package com.github.mdcdi1315.modernized_iron_shulker_boxes.datagen;

import com.github.mdcdi1315.basemodslib.registries.RegistryUtils;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

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
    public void buildRecipes(RecipeOutput exporter)
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
                            exporter,
                            RegistryUtils.ConstructResourceLocation(
                                    id.getNamespace(),
                                    "shulker_boxes_coloring/" + color.GetNamespacedID() + "_" + id.getPath() + "_recipe"
                            )
                        );
            }
        }
    }
}
