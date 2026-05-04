package com.github.mdcdi1315.modernized_iron_shulker_boxes.client;

import com.github.mdcdi1315.basemodslib.client.*;
import com.github.mdcdi1315.basemodslib.config.ConfigManager;
import com.github.mdcdi1315.basemodslib.mods.IClientModInstance;
import com.github.mdcdi1315.basemodslib.config.gui.ConfigurationScreenFactory;
import com.github.mdcdi1315.basemodslib.client.registries.IClientRegistryRegistrar;
import com.github.mdcdi1315.basemodslib.config.gui.DefaultConfigurationScreenFactory;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.Config;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.*;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesBlocks;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client.MenuItemsPlacementDataReloadListener;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.client.itemrendering.IronShulkerBoxSpecialRenderer;

public final class IronShulkerBoxesClientModInstance
    implements IClientModInstance
{
    @Override
    public void RegisterBlockEntityRenderers(IBlockEntityRendererRegistrar registrar)
    {
        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.IRON_SHULKER_BOX, IronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.COPPER_SHULKER_BOX, IronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.GOLD_SHULKER_BOX, IronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.DIAMOND_SHULKER_BOX, IronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.CRYSTAL_SHULKER_BOX, CrystalIronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.OBSIDIAN_SHULKER_BOX, IronShulkerBoxRenderer::new));

        registrar.Register(new BlockEntityRendererRegistrationInfo<>(() -> IronShulkerBoxesBlockEntities.NETHERITE_SHULKER_BOX, IronShulkerBoxRenderer::new));
    }

    @Override
    public void RegisterMenuScreens(IMenuScreensRegistrar registrar)
    {
        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX, IronShulkerBoxScreen::new);

        registrar.RegisterMenuScreen(() -> IronShulkerBoxesMenuTypes.NETHERITE_SHULKER_BOX, IronShulkerBoxScreen::new);
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
    public void RegisterClientRegistryItems(IClientRegistryRegistrar registrar)
    {
        registrar.RegisterResourceReloadListener("menu_placement_data", new MenuItemsPlacementDataReloadListener());
    }

    @Override
    public void SetupConfigurationFiles(ConfigManager manager)
    {
        manager.TrackJsonConfigurationFile(Config.class, Config::new);
        Config.INSTANCE = manager.LoadOrCreateConfigurationFile(Config.class);
    }

    @Override
    public ConfigurationScreenFactory<?> RegisterConfigurationScreenFactory() { return new DefaultConfigurationScreenFactory<>(Config.INSTANCE); }

    @Override
    public String GetModId() { return IronShulkerBoxesModInstance.MOD_ID; }

    @Override
    public void Dispose() {

    }
}
