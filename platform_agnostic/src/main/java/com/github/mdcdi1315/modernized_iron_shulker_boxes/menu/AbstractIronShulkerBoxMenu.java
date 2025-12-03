package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class AbstractIronShulkerBoxMenu
    extends AbstractContainerMenu
{
    protected final Container container;
    private final IronShulkerBoxesTypes type;

    protected AbstractIronShulkerBoxMenu(@MaybeNull MenuType<?> menuType, int container_id, Inventory player_inventory, Container box_inventory, IronShulkerBoxesTypes box_type)
    {
        super(menuType, container_id);
        checkContainerSize(this.container = box_inventory , (this.type = box_type).size);

        container.startOpen(player_inventory.player);

        for (int shulkerBoxRow = 0; shulkerBoxRow < type.getRowCount(); shulkerBoxRow++) {
            for (int shulkerBoxCol = 0; shulkerBoxCol < type.rowLength; shulkerBoxCol++) {
                this.addSlot(new ShulkerBoxSlot(container, shulkerBoxCol + shulkerBoxRow * type.rowLength, 12 + shulkerBoxCol * 18, 18 + shulkerBoxRow * 18));
            }
        }

        int leftCol = (type.xSize - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(player_inventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, type.ySize - (4 - playerInvRow) * 18 - 10));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++) {
            this.addSlot(new Slot(player_inventory, hotHarSlot, leftCol + hotHarSlot * 18, type.ySize - 24));
        }
    }

    @Override
    public boolean stillValid(Player player) { return this.container.stillValid(player); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStackInSlot = slot.getItem();
            itemStack = itemStackInSlot.copy();

            if (index < this.type.size) {
                if (!this.moveItemStackTo(itemStackInSlot, this.type.size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStackInSlot, 0, this.type.size, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public IronShulkerBoxesTypes GetShulkerBoxType() { return this.type; }
}
