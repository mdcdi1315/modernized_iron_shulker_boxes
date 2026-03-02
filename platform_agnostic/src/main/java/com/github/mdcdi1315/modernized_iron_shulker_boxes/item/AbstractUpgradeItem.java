package com.github.mdcdi1315.modernized_iron_shulker_boxes.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractUpgradeItem
    extends Item
{
    public AbstractUpgradeItem(ResourceLocation location) {
        super(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, location)));
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) { return 0; }
}
