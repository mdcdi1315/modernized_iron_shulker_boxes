package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class IronShulkerBoxRenderer<T extends AbstractIronShulkerBoxBlockEntity>
        implements BlockEntityRenderer<T>
{
    private final ShulkerModel<?> model;
    protected final BlockEntityRenderDispatcher renderer;

    public IronShulkerBoxRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ShulkerModel<>(context.bakeLayer(ModelLayers.SHULKER));
        this.renderer = context.getBlockEntityRenderDispatcher();
    }

    @Override
    public void render(T tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
        IronShulkerBoxColor color = null;
        Direction direction = Direction.UP;
        BlockState block_state = tileEntityIn.getBlockState();
        Block block = block_state.getBlock();
        if (block instanceof AbstractIronShulkerBoxBlock) {
            color = block_state.getValue(AbstractIronShulkerBoxBlock.COLOR);
            direction = block_state.getValue(AbstractIronShulkerBoxBlock.FACING);
        }

        IronShulkerBoxesTypes boxType = tileEntityIn.GetShulkerBoxType();
        IronShulkerBoxesTypes typeFromBlock = AbstractIronShulkerBoxBlock.getTypeFromBlock(block);

        if (typeFromBlock != null && boxType != typeFromBlock) { boxType = typeFromBlock; }

        Material material;

        if (color == null || color == IronShulkerBoxColor.NONE) {
            material = new Material(Sheets.SHULKER_SHEET, IronShulkerBoxesModels.chooseShulkerBoxTexture(boxType));
        } else {
            material = new Material(Sheets.SHULKER_SHEET, IronShulkerBoxesModels.chooseShulkerBoxTexture(boxType, color.GetVariantID() - 1));
        }

        poseStack.pushPose(); // POSE PUSH UNSAFE BEGIN
        try {
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.scale(0.9995F, 0.9995F, 0.9995F);
            poseStack.mulPose(direction.getRotation());
            poseStack.scale(1.0F, -1.0F, -1.0F);
            poseStack.translate(0.0F, -1.0F, 0.0F);
            ModelPart modelpart = this.model.getLid();
            modelpart.setPos(0.0F, 24.0F - tileEntityIn.GetProgress(partialTicks) * 0.5F * 16.0F, 0.0F);
            modelpart.yRot = 270.0F * tileEntityIn.GetProgress(partialTicks) * ((float) Math.PI / 180F);
            this.model.renderToBuffer(poseStack, material.buffer(bufferSource, RenderType::entityCutoutNoCull), combinedLightIn, combinedOverlayIn); //  1.0F, 1.0F, 1.0F, 1.0F
        } finally {
            poseStack.popPose(); // POSE POP UNSAFE END
        }
    }

    public AABB getRenderBoundingBox(AbstractIronShulkerBoxBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5);
    }
}
