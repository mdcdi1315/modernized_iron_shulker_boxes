package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public final class NetheriteShulkerBoxMenu
    extends AbstractIronShulkerBoxMenu
{
    public NetheriteShulkerBoxMenu(int container_id, Inventory player_inventory, Container box_inventory) {
        super(IronShulkerBoxesMenuTypes.NETHERITE_SHULKER_BOX, container_id, player_inventory, box_inventory, IronShulkerBoxesTypes.NETHERITE);
    }

    public static NetheriteShulkerBoxMenu CreateMenuDirect(int container_id, Inventory player_inventory) {
        return new NetheriteShulkerBoxMenu(container_id , player_inventory , new SimpleContainer(IronShulkerBoxesTypes.NETHERITE.size));
    }
}
