package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.AbstractIronShulkerBoxMenu;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public final class IronShulkerBoxScreen
      extends AbstractContainerScreen<AbstractIronShulkerBoxMenu>
{
    private final IronShulkerBoxesTypes type;
    private final int textureXSize;
    private final int textureYSize;

    public IronShulkerBoxScreen(AbstractIronShulkerBoxMenu container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);

        this.type = container.GetShulkerBoxType();
        this.imageWidth = type.xSize;
        this.imageHeight = type.ySize;
        this.textureXSize = type.textureXSize;
        this.textureYSize = type.textureYSize;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 6, 4210752, false);

        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.type.guiTexture);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(this.type.guiTexture, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
    }
}

