package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public final class CopperShulkerBoxMenu
    extends AbstractIronShulkerBoxMenu
{
    public CopperShulkerBoxMenu(int containerId, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, containerId, player_inventory, box_inventory, IronShulkerBoxesTypes.COPPER);
    }

    public CopperShulkerBoxMenu(int containerId, Inventory player_inventory) {
        super(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, containerId, player_inventory, IronShulkerBoxesTypes.COPPER);
    }
}
