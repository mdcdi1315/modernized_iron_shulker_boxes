package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class VirtualizedScreen<T extends AbstractContainerMenu>
    extends AbstractContainerScreen<T>
{
    private final int desired_width;
    private final int desired_height;
    private final AbstractContainerScreen<T> virtual;

    public VirtualizedScreen(AbstractContainerScreen<T> virtual, Inventory inv, int width, int height)
    {
        super(virtual.getMenu(), inv, virtual.getTitle());
        this.virtual = virtual;
        this.desired_width = width;
        this.desired_height = height;
    }

    @Override
    protected void init()
    {
        virtual.init(minecraft, desired_width, desired_height);
    }

    @Override
    public void render(GuiGraphics gc, int mouseX, int mouseY, float partialTick)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        mouseX -= offset_x;
        mouseY -= offset_y;

        RenderSystem.getModelViewStack().pushMatrix();
        RenderSystem.getModelViewStack().translate(offset_x, offset_y, 0);
        RenderSystem.applyModelViewMatrix();

        virtual.setFocused(mouseX >= 0 && mouseX < desired_width && mouseY >= 0 && mouseY < desired_height);

        gc.pose().scale((float)desired_width / width, (float) desired_height / height, (float)desired_width / width);
        virtual.render(gc, mouseX, mouseY, partialTick);

        RenderSystem.getModelViewStack().popMatrix();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {}

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        return virtual.mouseClicked(mouseX - offset_x, mouseY - offset_y, button);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        return virtual.isMouseOver(mouseX - offset_x, mouseY - offset_y);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        virtual.mouseMoved(mouseX - offset_x, mouseY - offset_y);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        return virtual.mouseScrolled(mouseX - offset_x, mouseY - offset_y, scrollX, scrollY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        return virtual.mouseReleased(mouseX - offset_x, mouseY - offset_y, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        int offset_x = (this.width - this.desired_width) / 2;
        int offset_y = (this.height - this.desired_height) / 2;
        return virtual.mouseDragged(mouseX - offset_x, mouseY - offset_y, button, dragX, dragY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) { return virtual.keyPressed(keyCode, scanCode, modifiers); }

    @Override
    protected void containerTick() { virtual.tick(); }
}
