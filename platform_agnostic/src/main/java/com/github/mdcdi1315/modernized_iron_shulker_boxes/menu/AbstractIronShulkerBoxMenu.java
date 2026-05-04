package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.NotNull;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client.MenuItemsPlacementDataLoader;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractIronShulkerBoxMenu
    extends AbstractContainerMenu
{
    private Object placement_data;
    protected final Container container;

    protected AbstractIronShulkerBoxMenu(
            @MaybeNull MenuType<? extends AbstractIronShulkerBoxMenu> menuType,
            int container_id, 
            Inventory player_inventory,
            Container box_inventory,
            IronShulkerBoxesTypes box_type
    ) {
        super(menuType, container_id);
        checkContainerSize(this.container = box_inventory, box_type.GetSize());

        container.startOpen(player_inventory.player);

        // IMPORTANT: Do not modify the below code! Doing so can lead to slots misunderstanding by both sides!
        if (player_inventory.player instanceof ServerPlayer) { // Server side
            Init_ServerSide(player_inventory);
        } else { // Client side
            Init_ClientSide(player_inventory, menuType);
        }
    }

    protected AbstractIronShulkerBoxMenu(
            @MaybeNull MenuType<? extends AbstractIronShulkerBoxMenu> menuType,
            int container_id,
            Inventory player_inventory,
            IronShulkerBoxesTypes box_type
    ) {
        this(menuType, container_id, player_inventory, new SimpleContainer(box_type.GetSize()), box_type);
    }

    private void Init_ServerSide(Inventory player_inventory)
    {
        placement_data = null;

        // x,y values in the slots are NEVER used by the server implementations, so we can define them as zero.

        int c_size = this.container.getContainerSize();
        for (int I = 0; I < c_size; I++) { addSlot(new ShulkerBoxSlot(container, I, 0, 0)); }

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
        {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
            {
                addSlot(new Slot(player_inventory, playerInvCol + playerInvRow * 9 + 9, 0, 0));
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++)
        {
            addSlot(new Slot(player_inventory, hotHarSlot, 0, 0));
        }
    }

    private void Init_ClientSide(Inventory player_inventory, MenuType<? extends AbstractIronShulkerBoxMenu> menuType)
    {
        var data = MenuItemsPlacementDataLoader.GetPlacementData(menuType);
        data.ArrangeSlots(container, player_inventory, this::addSlot);
        placement_data = data;
    }

    @MaybeNull
    public Object GetPlacementData() { return placement_data; }

    @Override
    public boolean stillValid(Player player) { return this.container.stillValid(player); }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack item_stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slot_stack = slot.getItem();
            item_stack = slot_stack.copy();

            int container_size = this.container.getContainerSize();
            if (index < container_size) {
                if (!this.moveItemStackTo(slot_stack, container_size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slot_stack, 0, container_size, false)) {
                return ItemStack.EMPTY;
            }

            if (slot_stack.isEmpty()) { slot.set(ItemStack.EMPTY); } else { slot.setChanged(); }
        }

        return item_stack;
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
