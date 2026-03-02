package com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;
import com.github.mdcdi1315.DotNetLayer.System.InvalidOperationException;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.DisallowNull;

// import com.github.mdcdi1315.modernized_iron_shulker_boxes.tags.ItemTags;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;

import java.util.List;
import java.util.ArrayList;

public final class ColoringCraftingRecipe
    implements CraftingRecipe
{
    private final String group;
    private final IronShulkerBoxColor color;
    private final List<Ingredient> ingredients;
    private final IronShulkerBoxItem shulker_box;

    public ColoringCraftingRecipe(IronShulkerBoxColor color , Item shulker_box_item, String group)
    {
        ArgumentNullException.ThrowIfNull(group, "group");
        ArgumentNullException.ThrowIfNull(color, "color");
        ArgumentNullException.ThrowIfNull(shulker_box_item, "shulker_box_item");
        if ((this.color = color) == IronShulkerBoxColor.NONE) {
            throw new InvalidOperationException("Cannot specify the NONE value!");
        }
        this.group = group;
        if (shulker_box_item instanceof IronShulkerBoxItem it) {
            this.shulker_box = it;
            // if (!(new ItemStack(this.shulker_box = it).is(ItemTags.DYEABLE_IRON_SHULKER_BOXES))) {
            //    throw new InvalidOperationException("The specified item is not a dyeable iron shulker box!");
            //}
        } else {
            throw new InvalidOperationException("The specified item is not an iron shulker box item!");
        }
        ingredients = ImmutableList.of(
                Ingredient.of(shulker_box),
                Ingredient.of(DyeItem.byColor(this.color.AsDyeColor()))
        );
    }

    private static final class ItemByRef { public ItemStack item; }

    private boolean MatchesAndGetItems(CraftingInput input, @DisallowNull ItemByRef shulker_box)
    {
        if (input.ingredientCount() == ingredients.size()) {
            List<Ingredient> remaining = new ArrayList<>(ingredients);
            for (ItemStack is : input.items())
            {
                for (int I = 0; I < remaining.size(); I++)
                {
                    if (remaining.get(I).test(is)) {
                        if (is.is(this.shulker_box)) {
                            shulker_box.item = is;
                        }
                        remaining.remove(I);
                        break;
                    }
                }
            }
            return remaining.isEmpty();
        } else {
            return false;
        }
    }

    private SlotDisplay ConstructResultItem()
    {
        ItemStack item = new ItemStack(shulker_box);
        item.set(IronShulkerBoxColorDataComponentType.INSTANCE , this.color);
        return new SlotDisplay.ItemStackSlotDisplay(item);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) { return MatchesAndGetItems(input, new ItemByRef()); }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider)
    {
        ItemByRef sk = new ItemByRef();
        if (MatchesAndGetItems(input, sk)) {

            ItemStack new_stack = sk.item.copy();

            new_stack.set(IronShulkerBoxColorDataComponentType.INSTANCE , color);

            return new_stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public List<RecipeDisplay> display()
    {
        List<SlotDisplay> ingd = new ArrayList<>(ingredients.size());
        for (Ingredient ing : ingredients) { ingd.add(ing.display()); }
        return ImmutableList.of(
            new ShapelessCraftingRecipeDisplay(ingd, ConstructResultItem(), new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE))
        );
    }

    @NotNull
    @Override
    public String group() { return group; }

    @Override
    public boolean showNotification() { return true; }

    public IronShulkerBoxColor GetColor() { return color; }

    public IronShulkerBoxItem GetShulkerBox() { return shulker_box; }

    @Override
    public CraftingBookCategory category() { return CraftingBookCategory.MISC; }

    @Override
    public PlacementInfo placementInfo() { return PlacementInfo.create(ingredients); }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() { return ColoringCraftingRecipeSerializer.INSTANCE; }
}
