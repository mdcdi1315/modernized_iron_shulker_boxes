package com.github.mdcdi1315.modernized_iron_shulker_boxes.creative_mode_tab;

import com.github.mdcdi1315.basemodslib.item.IItemRegistrar;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class IronShulkerBoxesCreativeModeTabs
{
    private IronShulkerBoxesCreativeModeTabs() {}

    static {
        IRON_SHULKER_BOXES = CreativeModeTab
                .builder(CreativeModeTab.Row.BOTTOM, 4)
                .icon(IronShulkerBoxesCreativeModeTabs::IronShulkerBoxItemStack)
                .title(Component.translatable("creative_mode_tab.modernized_iron_shulker_boxes"))
                .build();
    }

    public static final CreativeModeTab IRON_SHULKER_BOXES;

    private static ItemStack IronShulkerBoxItemStack() {
        return new ItemStack(IronShulkerBoxesItems.IRON_SHULKER_BOX);
    }

    public static void Register(IItemRegistrar registrar) {
        registrar.RegisterCreativeModeTab("iron_shulker_boxes_tab", IRON_SHULKER_BOXES);
    }
}
