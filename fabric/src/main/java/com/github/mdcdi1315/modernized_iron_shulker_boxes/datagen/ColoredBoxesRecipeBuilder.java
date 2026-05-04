package com.github.mdcdi1315.modernized_iron_shulker_boxes.datagen;

import com.github.mdcdi1315.DotNetLayer.System.StringUtils;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes.ColoringCraftingRecipe;

import net.minecraft.advancements.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.DyeItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;

import java.util.*;

public class ColoredBoxesRecipeBuilder
    implements RecipeBuilder
{
    private Item item;
    private String group;
    private IronShulkerBoxColor color;
    private final Map<String, Criterion<?>> criteria;

    public ColoredBoxesRecipeBuilder(Item item)
    {
        criteria = new HashMap<>();
        this.item = item;
    }

    @Override
    public ColoredBoxesRecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public ColoredBoxesRecipeBuilder group(String groupName) { this.group = groupName; return this; }

    @Override
    public Item getResult() { return item; }

    public IronShulkerBoxColor GetColor() { return color; }

    public ColoredBoxesRecipeBuilder WithColor(IronShulkerBoxColor color) { this.color = color; return this; }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> resource_key)
    {
        ResourceLocation rl = BuiltInRegistries.ITEM.getKey(item);
        List<String> rest_criteria = criteria.keySet().stream().toList();
        criteria.put(
                "has_the_box_recipe",
                RecipeUnlockedTrigger.unlocked(
                        ResourceKey.create(
                                Registries.RECIPE,
                                IronShulkerBoxesModInstance.ID("shulker_boxes_default/" + rl.getPath())
                        )
                )
        );
        criteria.put("already_has_the_recipe", RecipeUnlockedTrigger.unlocked(resource_key));
        criteria.put(
                "has_the_box_with_him",
                InventoryChangeTrigger.TriggerInstance.hasItems(item)
        );
        criteria.put(
                "has_the_dye",
                InventoryChangeTrigger.TriggerInstance.hasItems(DyeItem.byColor(color.AsDyeColor()))
        );
        output.accept(
                resource_key,
                new ColoringCraftingRecipe(
                        color,
                        item,
                        group
                ),
                new AdvancementHolder(
                        IronShulkerBoxesModInstance.ID(StringUtils.Format("recipes/coloring/{0}_{1}", rl.getPath(), color.GetNamespacedID())),
                        new Advancement(
                                Optional.of(ROOT_RECIPE_ADVANCEMENT),
                                Optional.empty(),
                                new AdvancementRewards(
                                        0,
                                        List.of(),
                                        List.of(resource_key),
                                        Optional.empty()
                                ),
                                criteria,
                                new AdvancementRequirements(
                                        rest_criteria.isEmpty() ?
                                                List.of(
                                                        List.of("has_the_box_recipe", "has_the_box_with_him", "already_has_the_recipe"),
                                                        List.of("has_the_dye", "already_has_the_recipe")
                                                ) :
                                                List.of(
                                                        List.of("has_the_box_recipe", "has_the_box_with_him", "already_has_the_recipe"),
                                                        List.of("has_the_dye", "already_has_the_recipe"),
                                                        rest_criteria
                                                )
                                ),
                                false
                        )
                )
        );
    }
}
