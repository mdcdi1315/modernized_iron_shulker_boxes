package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.basemodslib.utils.Extensions;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;

public final class ShulkerBoxModel
            extends Model
{
    private final ModelPart lid;

    public ShulkerBoxModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.lid = root.getChild("lid");
    }

    public ShulkerBoxModel(EntityModelSet ems) { this(ems.bakeLayer(ModelLayers.SHULKER_BOX)); }

    public void animate(float progress)
    {
        this.lid.setPos(0.0F, 24.0F - progress * 0.5F * 16.0F, 0.0F);
        this.lid.yRot = 270.0F * progress * (Extensions.PI / 180F);
    }
}