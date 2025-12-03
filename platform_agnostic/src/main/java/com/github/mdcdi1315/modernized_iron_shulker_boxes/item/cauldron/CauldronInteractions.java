package com.github.mdcdi1315.modernized_iron_shulker_boxes.item.cauldron;

import com.github.mdcdi1315.basemodslib.eventapi.mods.CommonSetupEvent;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;

import net.minecraft.core.cauldron.CauldronInteraction;

public final class CauldronInteractions
{
    private CauldronInteractions() {}

    public static void Initialize(CommonSetupEvent cse) {
        cse.EnqueueWork(CauldronInteractions::InitializeInternal);
    }

    private static void InitializeInternal()
    {
        IronShulkerBoxesClearBoxCauldronInteraction CLEAR = new IronShulkerBoxesClearBoxCauldronInteraction();
        var m = CauldronInteraction.WATER.map();
        m.put(IronShulkerBoxesItems.IRON_SHULKER_BOX, CLEAR);
        m.put(IronShulkerBoxesItems.COPPER_SHULKER_BOX, CLEAR);
        m.put(IronShulkerBoxesItems.GOLD_SHULKER_BOX, CLEAR);
        m.put(IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX, CLEAR);
        m.put(IronShulkerBoxesItems.DIAMOND_SHULKER_BOX, CLEAR);
        m.put(IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX, CLEAR);
        IronShulkerBoxesDestroyShulkerBoxInteraction DESTROY = new IronShulkerBoxesDestroyShulkerBoxInteraction();
        m = CauldronInteraction.LAVA.map();
        m.put(IronShulkerBoxesItems.IRON_SHULKER_BOX, DESTROY);
        m.put(IronShulkerBoxesItems.COPPER_SHULKER_BOX, DESTROY);
        m.put(IronShulkerBoxesItems.GOLD_SHULKER_BOX, DESTROY);
        m.put(IronShulkerBoxesItems.CRYSTAL_SHULKER_BOX, DESTROY);
        m.put(IronShulkerBoxesItems.DIAMOND_SHULKER_BOX, DESTROY);
        m.put(IronShulkerBoxesItems.OBSIDIAN_SHULKER_BOX, DESTROY);
    }
}