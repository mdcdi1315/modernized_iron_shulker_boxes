package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu;

import com.github.mdcdi1315.basemodslib.menu.IMenuTypeRegistrar;
import com.github.mdcdi1315.basemodslib.menu.MenuTypeRegistrationInfo;
import com.github.mdcdi1315.basemodslib.eventapi.mods.registries.MenuTypeRegistryFinalizedEvent;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public final class IronShulkerBoxesMenuTypes
{
    private IronShulkerBoxesMenuTypes() {}

    public static MenuType<IronShulkerBoxMenu> IRON_SHULKER_BOX;
    public static MenuType<GoldShulkerBoxMenu> GOLD_SHULKER_BOX;
    public static MenuType<CopperShulkerBoxMenu> COPPER_SHULKER_BOX;
    public static MenuType<DiamondShulkerBoxMenu> DIAMOND_SHULKER_BOX;
    public static MenuType<CrystalShulkerBoxMenu> CRYSTAL_SHULKER_BOX;
    public static MenuType<ObsidianShulkerBoxMenu> OBSIDIAN_SHULKER_BOX;
    public static MenuType<NetheriteShulkerBoxMenu> NETHERITE_SHULKER_BOX;

    public static void Initialize(IMenuTypeRegistrar registrar)
    {
        registrar.Register("iron_shulker_box_menu" , new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, IronShulkerBoxMenu::new));

        registrar.Register("copper_shulker_box_menu" , new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, CopperShulkerBoxMenu::new));

        registrar.Register("gold_shulker_box_menu" , new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, GoldShulkerBoxMenu::new));

        registrar.Register("diamond_shulker_box_menu", new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, DiamondShulkerBoxMenu::new));

        registrar.Register("crystal_shulker_box_menu" , new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, CrystalShulkerBoxMenu::new));

        registrar.Register("obsidian_shulker_box_menu", new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, ObsidianShulkerBoxMenu::new));

        registrar.Register("netherite_shulker_box_menu", new MenuTypeRegistrationInfo<>(FeatureFlags.VANILLA_SET, NetheriteShulkerBoxMenu::new));
    }

    public static void InitializeFields(MenuTypeRegistryFinalizedEvent event) {
        IRON_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("iron_shulker_box_menu"));
        GOLD_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("gold_shulker_box_menu"));
        COPPER_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("copper_shulker_box_menu"));
        DIAMOND_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("diamond_shulker_box_menu"));
        CRYSTAL_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("crystal_shulker_box_menu"));
        OBSIDIAN_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("obsidian_shulker_box_menu"));
        NETHERITE_SHULKER_BOX = event.GetRegistryObjectChecked(IronShulkerBoxesModInstance.ID("netherite_shulker_box_menu"));
    }
}
