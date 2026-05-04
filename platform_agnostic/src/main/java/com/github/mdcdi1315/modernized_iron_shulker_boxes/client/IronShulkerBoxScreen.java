package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client.GUITextureData;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.AbstractIronShulkerBoxMenu;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client.MenuItemsPlacementData;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class IronShulkerBoxScreen
      extends AbstractContainerScreen<AbstractIronShulkerBoxMenu>
{
    private final ResourceLocation texture;
    private final int textureXSize;
    private final int textureYSize;

    public IronShulkerBoxScreen(AbstractIronShulkerBoxMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);

        var data = (MenuItemsPlacementData)container.GetPlacementData();

        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = data.GetInventoryTextXPosition();
        this.inventoryLabelY = data.GetInventoryTextYPosition();
        GUITextureData tex_data = data.GetTextureData();
        this.texture = tex_data.GetTexture();
        this.imageWidth = tex_data.GetUsedTextureWidth();
        this.imageHeight = tex_data.GetUsedTextureHeight();
        this.textureXSize = tex_data.GetActualTextureWidth();
        this.textureYSize = tex_data.GetActualTextureHeight();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        guiGraphics.blit(texture, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
    }
}

