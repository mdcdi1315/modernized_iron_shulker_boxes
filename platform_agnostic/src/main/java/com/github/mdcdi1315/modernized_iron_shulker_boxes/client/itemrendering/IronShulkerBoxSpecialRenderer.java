package com.github.mdcdi1315.modernized_iron_shulker_boxes.client.itemrendering;

import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.AllowNull;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.client.ShulkerBoxModel;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxItem;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxColor;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.client.IronShulkerBoxesModels;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.datacomponent.IronShulkerBoxColorDataComponentType;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.serialization.MapCodec;

import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;

public final class IronShulkerBoxSpecialRenderer
    implements SpecialModelRenderer<Material>
{
    private final ShulkerBoxModel model;

    public IronShulkerBoxSpecialRenderer(EntityModelSet modelSet) {
        model = new ShulkerBoxModel(modelSet);
        model.animate(0f);
    }

    @Override
    public void render(@AllowNull Material material, ItemDisplayContext dc, PoseStack pose_stack, MultiBufferSource multi_buffer_source, int packed_light, int packed_overlay, boolean has_foil)
    {
        pose_stack.pushPose(); // POSE PUSH UNSAFE BEGIN
        try {
            /*
            Original code is the below:
            But because we do not need the direction, we avoid the multiplication at all.
            And we do not need any translations for the outcome of this.
            So we just specify the translations to flip the image and appropriately scale to inventory slots.
            pose_stack.translate(0.5F, 0.5F, 0.5F);
            pose_stack.scale(0.9995F, 0.9995F, 0.9995F);
            pose_stack.mulPose(Direction.UP.getRotation());
            pose_stack.scale(1.0F, -1.0F, -1.0F);
            pose_stack.translate(0.0F, -1.0F, 0.0F);
            this.model.animate(0f); // Not needed for the SMR
             */
            pose_stack.translate(0.5F, 1.5F, 0.5F);
            pose_stack.scale(1.0F, -1.0F, -1.0F);
            this.model.renderToBuffer(pose_stack, material.buffer(multi_buffer_source, this.model::renderType), packed_light, packed_overlay);
        } finally {
            pose_stack.popPose(); // POSE POP UNSAFE END
        }
    }

    @Override
    public Material extractArgument(ItemStack stack)
    {
        IronShulkerBoxesTypes type;
        IronShulkerBoxColor color = stack.get(IronShulkerBoxColorDataComponentType.INSTANCE);
        if (stack.getItem() instanceof IronShulkerBoxItem i) { type = i.getBlock().GetType(); } else { type = IronShulkerBoxesTypes.VANILLA; }

        return new Material(
                Sheets.SHULKER_SHEET,
                IronShulkerBoxesModels.chooseShulkerBoxTexture(type, color)
        );
    }

    public record Unbaked()
            implements SpecialModelRenderer.Unbaked
    {
        public static final Unbaked INSTANCE = new Unbaked();

        public static final MapCodec<Unbaked> CODEC = MapCodec.unit(Unbaked.INSTANCE);

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet ems) { return new IronShulkerBoxSpecialRenderer(ems); }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked> type() { return CODEC; }
    }
}
