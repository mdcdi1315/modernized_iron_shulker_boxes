package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.basemodslib.client.*;
import com.github.mdcdi1315.basemodslib.mods.IClientModInstance;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesBlocks;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxesMenuTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.client.itemrendering.IronShulkerBoxSpecialRenderer;

public final class IronShulkerBoxesClientModInstance
    implements IClientModInstance
{
    @Override
    public void RegisterBlockEntityRenderers(IBlockEntityRendererRegistrar registrar)
    {
        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.IRON_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.COPPER_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.GOLD_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.DIAMOND_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.CRYSTAL_SHULKER_BOX,
                CrystalIronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.NETHERITE_SHULKER_BOX,
                IronShulkerBoxRenderer::new
        ));
    }

    @Override
    public void RegisterMenuScreens(IMenuScreensRegistrar registrar)
    {
        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );

        registrar.RegisterMenuScreen(
                () -> IronShulkerBoxesMenuTypes.NETHERITE_SHULKER_BOX,
                IronShulkerBoxScreen::new
        );
    }

    @Override
    public void RegisterSpecialModelRenderers(ISpecialModelRendererRegistrar registrar)
    {
        var renderer = IronShulkerBoxSpecialRenderer.Unbaked.INSTANCE;

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.COPPER_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.IRON_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.GOLD_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.DIAMOND_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.CRYSTAL_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.OBSIDIAN_SHULKER_BOX, renderer));

        registrar.Register(new SpecialModelRendererRegistrationInfo(() -> IronShulkerBoxesBlocks.NETHERITE_SHULKER_BOX, renderer));

        registrar.RegisterCodec(new SpecialModelRendererCodecRegistrationInfo(IronShulkerBoxesModInstance.ID("shulker_box_renderer"), IronShulkerBoxSpecialRenderer.Unbaked.CODEC));
    }

    @Override
    public String GetModId() { return IronShulkerBoxesModInstance.MOD_ID; }

    @Override
    public void Dispose() {

    }
}
