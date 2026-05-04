package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public final class ObsidianShulkerBoxMenu
        extends AbstractIronShulkerBoxMenu
{
    public ObsidianShulkerBoxMenu(int containerId, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, containerId, player_inventory, box_inventory, IronShulkerBoxesTypes.OBSIDIAN);
    }

    public ObsidianShulkerBoxMenu(int containerId, Inventory player_inventory) {
        super(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, containerId, player_inventory, IronShulkerBoxesTypes.OBSIDIAN);
    }
}
