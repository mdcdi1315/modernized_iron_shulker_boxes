package com.github.mdcdi1315.modernized_iron_shulker_boxes.tags;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;

public final class ItemTags
{
    public static TagKey<Item> DYEABLE_IRON_SHULKER_BOXES;

    private ItemTags() {}

    public static void Initialize()
    {
        DYEABLE_IRON_SHULKER_BOXES = TagKey.create(
                Registries.ITEM,
                IronShulkerBoxesModInstance.ID("dyeable_iron_shulker_boxes")
        );
    }
}
