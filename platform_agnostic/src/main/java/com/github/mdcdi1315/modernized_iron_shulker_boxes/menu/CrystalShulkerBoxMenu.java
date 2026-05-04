package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public final class CrystalShulkerBoxMenu
        extends AbstractIronShulkerBoxMenu
{
    public CrystalShulkerBoxMenu(int container_id, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, container_id, player_inventory, box_inventory, IronShulkerBoxesTypes.CRYSTAL);
    }

    public CrystalShulkerBoxMenu(int container_id, Inventory player_inventory) {
        super(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, container_id, player_inventory, IronShulkerBoxesTypes.CRYSTAL);
    }
}
