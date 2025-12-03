package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public final class ObsidianShulkerBoxMenu
        extends AbstractIronShulkerBoxMenu
{
    public ObsidianShulkerBoxMenu(int containerId, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, containerId, player_inventory, box_inventory, IronShulkerBoxesTypes.OBSIDIAN);
    }

    public static ObsidianShulkerBoxMenu CreateMenuDirect(int container_id, Inventory player_inventory) {
        return new ObsidianShulkerBoxMenu(container_id , player_inventory , new SimpleContainer(IronShulkerBoxesTypes.OBSIDIAN.size));
    }
}
