package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client;

import com.github.mdcdi1315.DotNetLayer.System.Func2;
import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.basemodslib.codecs.CodecUtils;

import com.mojang.serialization.Codec;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ShulkerBoxSlot;

public final class MenuItemsPlacementData
{
    private final GUITextureData texture_data;

    private final int inventory_slots_x_position;
    private final int inventory_slots_y_position;

    private final int inventory_text_x_position;
    private final int inventory_text_y_position;

    private final byte item_stack_count_in_each_row;

    public static final int SLOT_WIDTH_IN_PIXELS = 18;
    public static final int SLOT_HEIGHT_IN_PIXELS = 18;
    public static final int SHULKER_CONTAINER_X_OFFSET = 12;
    public static final int SHULKER_CONTAINER_Y_OFFSET = 18;

    public static final Codec<MenuItemsPlacementData> CODEC = CodecUtils.CreateCodecDirect(
            CodecUtils.CreateCodecDirect(
                    ResourceLocation.CODEC.fieldOf("texture_location").forGetter(GUITextureData::GetTexture),
                    CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("actual_texture_width").forGetter(GUITextureData::GetActualTextureWidth),
                    CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("actual_texture_height").forGetter(GUITextureData::GetActualTextureHeight),
                    CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("used_texture_width").forGetter(GUITextureData::GetUsedTextureWidth),
                    CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("used_texture_height").forGetter(GUITextureData::GetUsedTextureHeight),
                    GUITextureData::new
            ).fieldOf("gui_texture").forGetter(MenuItemsPlacementData::GetTextureData),
            CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("inventory_slots_x_position").forGetter((f) -> f.inventory_slots_x_position),
            CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("inventory_slots_y_position").forGetter((f) -> f.inventory_slots_y_position),
            CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("inventory_text_x_position").forGetter(MenuItemsPlacementData::GetInventoryTextXPosition),
            CodecUtils.ZERO_OR_POSITIVE_INTEGER.fieldOf("inventory_text_y_position").forGetter(MenuItemsPlacementData::GetInventoryTextYPosition),
            CodecUtils.POSITIVE_INTEGER.fieldOf("item_stack_count_in_each_row").forGetter(MenuItemsPlacementData::GetItemStackCountInEachRow),
            MenuItemsPlacementData::new
    );

    public MenuItemsPlacementData(
            @MaybeNull GUITextureData texture_data,
            int inventory_slots_x_position,
            int inventory_slots_y_position,
            int inventory_text_x_position,
            int inventory_text_y_position,
            int item_stack_count_in_each_row
    ) throws ArgumentNullException
    {
        this.texture_data = texture_data;
        this.inventory_text_x_position = inventory_text_x_position;
        this.inventory_text_y_position = inventory_text_y_position;
        this.inventory_slots_x_position = inventory_slots_x_position;
        this.inventory_slots_y_position = inventory_slots_y_position;
        this.item_stack_count_in_each_row = (byte) item_stack_count_in_each_row;
    }

    public MenuItemsPlacementData(
            GUITextureData texture_data,
            int item_stack_count_in_each_row
    ) throws ArgumentNullException
    {
        ArgumentNullException.ThrowIfNull(texture_data, "texture_data");
        this.texture_data = texture_data;
        this.inventory_text_x_position = 8;
        this.inventory_text_y_position = texture_data.GetUsedTextureHeight() - 94;
        this.inventory_slots_x_position = (texture_data.GetUsedTextureWidth() - 162) / 2 + 1;
        this.inventory_slots_y_position = texture_data.GetUsedTextureHeight();
        this.item_stack_count_in_each_row = (byte) item_stack_count_in_each_row;
    }

    @MaybeNull
    public GUITextureData GetTextureData() { return this.texture_data; }

    public int GetInventoryTextXPosition() { return this.inventory_text_x_position; }

    public int GetInventoryTextYPosition() { return this.inventory_text_y_position; }

    public int GetItemStackCountInEachRow() { return this.item_stack_count_in_each_row & 0xFF; }

    // IMPORTANT: Do not modify the below code! Doing so can lead to slots misunderstanding by both sides!

    public void ArrangeSlots(Container container, Inventory player_inventory, Func2<Slot, Slot> slot_adder)
            throws ArgumentNullException
    {
        ArgumentNullException.ThrowIfNull(container, "container");
        ArgumentNullException.ThrowIfNull(slot_adder, "slot_adder");
        ArgumentNullException.ThrowIfNull(player_inventory, "player_inventory");

        int c_size = container.getContainerSize();
        int row_length = GetItemStackCountInEachRow();
        int row_count = c_size / row_length;
        for (int shulkerBoxRow = 0; shulkerBoxRow < row_count; shulkerBoxRow++)
        {
            for (int shulkerBoxCol = 0; shulkerBoxCol < row_length; shulkerBoxCol++)
            {
                slot_adder.function(
                        new ShulkerBoxSlot(
                                container,
                                shulkerBoxCol + shulkerBoxRow * row_length,
                                SHULKER_CONTAINER_X_OFFSET + shulkerBoxCol * SLOT_WIDTH_IN_PIXELS,
                                SHULKER_CONTAINER_Y_OFFSET + shulkerBoxRow * SLOT_HEIGHT_IN_PIXELS
                        )
                );
            }
        }
        int remaining;
        // If not exactly divisible we must still add the remaining items.
        // So, any remaining items are now added with this below code fragment.
        if ((remaining = c_size % row_length) > 0)
        {
            int rl_new = row_count * row_length;
            for (int I = 0; I < remaining; I++)
            {
                slot_adder.function(
                        new ShulkerBoxSlot(container, I + rl_new, SHULKER_CONTAINER_X_OFFSET + I * SLOT_WIDTH_IN_PIXELS, SHULKER_CONTAINER_Y_OFFSET + row_count * SLOT_HEIGHT_IN_PIXELS)
                );
            }
        }

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
        {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
            {
                slot_adder.function(
                        new Slot(
                                player_inventory,
                                playerInvCol + playerInvRow * 9 + 9,
                                inventory_slots_x_position + playerInvCol * SLOT_WIDTH_IN_PIXELS,
                                inventory_slots_y_position - (4 - playerInvRow) * SLOT_HEIGHT_IN_PIXELS - 10
                        )
                );
            }
        }

        for (int hotHarSlot = 0; hotHarSlot < 9; hotHarSlot++)
        {
            slot_adder.function(
                    new Slot(
                            player_inventory,
                            hotHarSlot,
                            inventory_slots_x_position + hotHarSlot * SLOT_WIDTH_IN_PIXELS,
                            inventory_slots_y_position - 24
                    )
            );
        }
    }

}
