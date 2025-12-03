package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.basemodslib.mods.IClientModInstance;
import com.github.mdcdi1315.basemodslib.client.IMenuScreensRegistrar;
import com.github.mdcdi1315.basemodslib.client.IBlockEntityRendererRegistrar;
import com.github.mdcdi1315.basemodslib.client.BlockEntityRendererRegistrationInfo;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxesMenuTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

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
                IronShulkerBoxRenderer::new
        ));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(
                () -> IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX,
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
    }

    @Override
    public String GetModId() { return IronShulkerBoxesModInstance.MOD_ID; }

    @Override
    public void Dispose() {

    }
}
