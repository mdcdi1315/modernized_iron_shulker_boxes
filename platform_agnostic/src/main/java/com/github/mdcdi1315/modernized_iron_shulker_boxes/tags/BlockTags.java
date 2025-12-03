package com.github.mdcdi1315.modernized_iron_shulker_boxes.tags;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;

public final class BlockTags
{
    public static TagKey<Block> IRON_SHULKER_BOXES;

    private BlockTags() {}

    public static void Initialize()
    {
        IRON_SHULKER_BOXES = TagKey.create(
                Registries.BLOCK,
                IronShulkerBoxesModInstance.ID("iron_shulker_boxes")
        );
    }
}
