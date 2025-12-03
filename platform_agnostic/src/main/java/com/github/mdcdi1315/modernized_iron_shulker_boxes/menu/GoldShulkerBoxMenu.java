package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public final class GoldShulkerBoxMenu
        extends AbstractIronShulkerBoxMenu
{
    public GoldShulkerBoxMenu(int container_id, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX, container_id, player_inventory, box_inventory, IronShulkerBoxesTypes.GOLD);
    }

    public static GoldShulkerBoxMenu CreateMenuDirect(int container_id, Inventory player_inventory) {
        return new GoldShulkerBoxMenu(container_id , player_inventory , new SimpleContainer(IronShulkerBoxesTypes.GOLD.size));
    }
}
