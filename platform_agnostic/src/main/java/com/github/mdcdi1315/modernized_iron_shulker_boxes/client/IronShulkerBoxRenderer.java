package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.AbstractIronShulkerBoxBlock;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ICrystalShulkerBoxBlockEntityDetails;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import org.joml.Vector3f;

import java.util.List;

public class IronShulkerBoxRenderer<T extends AbstractIronShulkerBoxBlockEntity>
        implements BlockEntityRenderer<T>
{
    private final ShulkerModel<?> model;
    private final BlockEntityRenderDispatcher renderer;

    private static final List<ModelItem> MODEL_ITEMS = ImmutableList.of(
        new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F),
        new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F),
        new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F),
        new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F),
        new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F),
        new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F),
        new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F),
        new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F),
        new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F)
    );

    public IronShulkerBoxRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ShulkerModel<>(context.bakeLayer(ModelLayers.SHULKER));
        this.renderer = context.getBlockEntityRenderDispatcher();
    }

    @Override
    public void render(AbstractIronShulkerBoxBlockEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
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
            modelpart.setPos(0.0F, 24.0F - tileEntityIn.getProgress(partialTicks) * 0.5F * 16.0F, 0.0F);
            modelpart.yRot = 270.0F * tileEntityIn.getProgress(partialTicks) * ((float) Math.PI / 180F);
            this.model.renderToBuffer(poseStack, material.buffer(bufferSource, RenderType::entityCutoutNoCull), combinedLightIn, combinedOverlayIn); //  1.0F, 1.0F, 1.0F, 1.0F
        } finally {
            poseStack.popPose(); // POSE POP UNSAFE END
        }

        if (tileEntityIn instanceof ICrystalShulkerBoxBlockEntityDetails csb && csb.HasTransparentSides() && Vec3.atCenterOf(tileEntityIn.getBlockPos()).closerThan(this.renderer.camera.getPosition(), 128d))
        {
            List<ItemStack> list = csb.GetTopStacks();

            if (!list.isEmpty())
            {
                float rotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTicks;

                int items_size = MODEL_ITEMS.size() - 1, list_size = list.size();

                for (int j = 0; j < list_size && j < items_size; j++) {
                    RenderItem(poseStack, bufferSource, list.get(j), MODEL_ITEMS.get(j), rotation, combinedLightIn);
                }
            }
        }
    }

    /**
     * Renders a single item in a TESR
     *
     * @param matrices  Matrix stack instance
     * @param buffer    Buffer instance
     * @param item      Item to render
     * @param modelItem Model items for render information
     * @param light     Model light
     */
    public static void RenderItem(PoseStack matrices, MultiBufferSource buffer, ItemStack item, ModelItem modelItem, float rotation, int light)
    {
        // Although by interface definition no empty items should be specified in the GetTopStacks list, verify that this is true with this simple statement.
        if (item.isEmpty()) { return; }

        // start rendering
        matrices.pushPose(); // POSE PUSH UNSAFE BEGIN
        try {
            Vector3f center = modelItem.getCenter();
            matrices.translate(center.x(), center.y(), center.z());

            matrices.mulPose(Axis.YP.rotationDegrees(rotation));

            // scale
            float scale = modelItem.getSizeScaled();
            matrices.scale(scale, scale, scale);

            // render the actual item
            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, null, 0);
        } finally {
            matrices.popPose(); // POSE POP UNSAFE END
        }
    }

    public AABB getRenderBoundingBox(AbstractIronShulkerBoxBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5);
    }
}
