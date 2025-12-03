package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public final class IronShulkerBoxMenu
    extends AbstractIronShulkerBoxMenu
{
    public IronShulkerBoxMenu(int container_id, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX, container_id, player_inventory, box_inventory, IronShulkerBoxesTypes.IRON);
    }

    public static IronShulkerBoxMenu CreateMenuDirect(int container_id, Inventory player_inventory) {
        return new IronShulkerBoxMenu(container_id , player_inventory , new SimpleContainer(IronShulkerBoxesTypes.IRON.size));
    }
}
