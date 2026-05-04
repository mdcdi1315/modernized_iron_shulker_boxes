package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public final class DiamondShulkerBoxMenu
        extends AbstractIronShulkerBoxMenu
{
    public DiamondShulkerBoxMenu(int container_id, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, container_id, player_inventory, box_inventory, IronShulkerBoxesTypes.DIAMOND);
    }

    public DiamondShulkerBoxMenu(int container_id, Inventory player_inventory) {
        super(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, container_id, player_inventory, IronShulkerBoxesTypes.DIAMOND);
    }
}
