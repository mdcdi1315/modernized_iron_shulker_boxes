package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.AbstractIronShulkerBoxBlockEntity;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.ICrystalShulkerBoxBlockEntityDetails;

import com.google.common.collect.ImmutableList;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import org.joml.Vector3f;

import java.util.List;

public final class CrystalIronShulkerBoxRenderer<T extends AbstractIronShulkerBoxBlockEntity & ICrystalShulkerBoxBlockEntityDetails>
    extends IronShulkerBoxRenderer<T>
{
    private static final List<ModelItem> MODEL_ITEMS;

    static {
        MODEL_ITEMS = ImmutableList.of(
                // Lower layer
                new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F),
                new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F),
                new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F),
                new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F),

                // Upper layer
                new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F),
                new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F),
                new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F),
                new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F)
                // ,new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F)
        );
    }

    public CrystalIronShulkerBoxRenderer(BlockEntityRendererProvider.Context context) { super(context); }

    @Override
    public void render(T tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn)
    {
        super.render(tileEntityIn, partialTicks, poseStack, bufferSource, combinedLightIn, combinedOverlayIn);

        if (tileEntityIn.HasTransparentSides() && Vec3.atCenterOf(tileEntityIn.getBlockPos()).closerThan(this.renderer.camera.getPosition(), 128d))
        {
            List<ItemStack> list = tileEntityIn.GetTopStacks();

            if (list.size() > 0) // This check is faster than !list.isEmpty()
            {
                float rotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTicks;

                int bound = Math.min(MODEL_ITEMS.size(), list.size());

                for (int I = 0; I < bound; I++) {
                    RenderItem(poseStack, bufferSource, list.get(I), MODEL_ITEMS.get(I), rotation, combinedLightIn);
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
}
