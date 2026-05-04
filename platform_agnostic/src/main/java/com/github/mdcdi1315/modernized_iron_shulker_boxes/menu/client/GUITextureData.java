package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;

import net.minecraft.resources.ResourceLocation;

public final class GUITextureData
{
    private final ResourceLocation texture;

    private final int actual_texture_width;
    private final int actual_texture_height;

    private final int used_texture_width;
    private final int used_texture_height;

    public GUITextureData(
            ResourceLocation texture,
            int actual_texture_width,
            int actual_texture_height,
            int used_texture_width,
            int used_texture_height
    ) throws ArgumentNullException
    {
        ArgumentNullException.ThrowIfNull(texture, "texture");
        this.texture = texture;
        this.actual_texture_width = actual_texture_width;
        this.actual_texture_height = actual_texture_height;
        this.used_texture_width = used_texture_width;
        this.used_texture_height = used_texture_height;
    }

    public ResourceLocation GetTexture() { return this.texture; }

    public int GetActualTextureWidth() { return this.actual_texture_width; }

    public int GetActualTextureHeight() { return this.actual_texture_height; }

    public int GetUsedTextureWidth() { return this.used_texture_width; }

    public int GetUsedTextureHeight() { return this.used_texture_height; }
}
