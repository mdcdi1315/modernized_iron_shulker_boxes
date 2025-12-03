package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public final class CopperShulkerBoxMenu
    extends AbstractIronShulkerBoxMenu
{
    public CopperShulkerBoxMenu(int containerId, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, containerId, player_inventory, box_inventory, IronShulkerBoxesTypes.COPPER);
    }

    public static CopperShulkerBoxMenu CreateMenuDirect(int container_id, Inventory player_inventory) {
        return new CopperShulkerBoxMenu(container_id , player_inventory , new SimpleContainer(IronShulkerBoxesTypes.COPPER.size));
    }
}
