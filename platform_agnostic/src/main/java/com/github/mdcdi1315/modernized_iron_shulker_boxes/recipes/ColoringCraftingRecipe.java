package com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;
import com.github.mdcdi1315.DotNetLayer.System.InvalidOperationException;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.DisallowNull;

// import com.github.mdcdi1315.modernized_iron_shulker_boxes.tags.ItemTags;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import net.minecraft.world.item.*;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.*;
import net.minecraft.core.component.DataComponentMap;

import java.util.List;
import java.util.ArrayList;

public final class ColoringCraftingRecipe
    implements CraftingRecipe
{
    private final String group;
    private final IronShulkerBoxColor color;
    private final IronShulkerBoxItem shulker_box;
    private final NonNullList<Ingredient> ingredients;

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
        ingredients = NonNullList.of(
                Ingredient.EMPTY ,
                Ingredient.of(shulker_box),
                Ingredient.of(DyeItem.byColor(this.color.AsDyeColor()))
        );
    }

    public IronShulkerBoxColor GetColor() { return color; }

    public IronShulkerBoxItem GetShulkerBox() { return shulker_box; }

    @Override
    public CraftingBookCategory category() { return CraftingBookCategory.MISC; }

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

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return MatchesAndGetItems(input, new ItemByRef());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider)
    {
        ItemByRef sk = new ItemByRef();
        if (MatchesAndGetItems(input, sk)) {

            ItemStack new_stack = sk.item.copy();

            new_stack.applyComponents(
                    DataComponentMap
                            .builder()
                            .set(IronShulkerBoxColorDataComponentType.INSTANCE , color)
                            .build()
            );

            return new_stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return true; }

    @Override
    public String getGroup() { return group; }

    @Override
    public NonNullList<Ingredient> getIngredients() { return ingredients; }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider)
    {
        ItemStack is = new ItemStack(shulker_box, 1);
        is.applyComponents(
                DataComponentMap
                        .builder()
                        .set(IronShulkerBoxColorDataComponentType.INSTANCE , color)
                        .build()
        );
        return is;
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return ColoringCraftingRecipeSerializer.INSTANCE; }
}
