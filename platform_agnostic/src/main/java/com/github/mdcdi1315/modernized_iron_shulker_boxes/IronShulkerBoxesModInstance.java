package com.github.mdcdi1315.modernized_iron_shulker_boxes;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentException;

import com.github.mdcdi1315.basemodslib.item.IItemRegistrar;
import com.github.mdcdi1315.basemodslib.block.IBlockRegistrar;
import com.github.mdcdi1315.basemodslib.eventapi.EventManager;
import com.github.mdcdi1315.basemodslib.network.NetworkManager;
import com.github.mdcdi1315.basemodslib.menu.IMenuTypeRegistrar;
import com.github.mdcdi1315.basemodslib.mods.IServerModInstance;
import com.github.mdcdi1315.basemodslib.eventapi.mods.registries.*;
import com.github.mdcdi1315.basemodslib.registries.IRegistryRegistrar;
import com.github.mdcdi1315.basemodslib.eventapi.mods.CommonSetupEvent;
import com.github.mdcdi1315.basemodslib.block.entity.IBlockEntityRegistrar;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.tags.*;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.recipes.RecipesInitializer;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.IronShulkerBoxesItems;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.IronShulkerBoxesBlocks;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxesMenuTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.item.cauldron.CauldronInteractions;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.networking.IronShulkerBoxesNetworking;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.block.entity.IronShulkerBoxesBlockEntities;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IronShulkerBoxesModInstance
    implements IServerModInstance
{
    public static Logger LOGGER;
    public static final String MOD_ID = "modernized_iron_shulker_boxes";

    public static ResourceLocation ID(String name)
            throws ArgumentException
    {
        var rl = ResourceLocation.tryBuild(MOD_ID , name);
        if (rl == null) {
            throw new ArgumentException("Cannot construct the specified ID!!\nName: " + name , "name");
        }
        return rl;
    }

    @Override
    public void Initialize() {
        LOGGER = LoggerFactory.getLogger("Modernized Iron Shulker Boxes Mod Instance");
        LOGGER.info("Now initializing Modernized Iron Shulker Boxes!!");
    }

    @Override
    public void OnInitializeEnd() {
        ItemTags.Initialize();
        BlockTags.Initialize();
    }

    @Override
    public void InitializeNetwork(NetworkManager manager) {
        IronShulkerBoxesNetworking.Initialize(manager);
    }

    @Override
    public void RegisterBlocks(IBlockRegistrar registrar) {
        IronShulkerBoxesBlocks.Initialize(registrar);
    }

    @Override
    public void RegisterBlockEntities(IBlockEntityRegistrar registrar) {
        IronShulkerBoxesBlockEntities.Initialize(registrar);
    }

    @Override
    public void RegisterItems(IItemRegistrar registrar) {
        IronShulkerBoxesItems.Initialize(registrar);
    }

    @Override
    public void RegisterEvents(EventManager manager) {
        manager.AddEventListener(BlockEntityTypeRegistryFinalizedEvent.class, IronShulkerBoxesBlockEntities::InitializeFields);
        manager.AddEventListener(MenuTypeRegistryFinalizedEvent.class, IronShulkerBoxesMenuTypes::InitializeFields);
        manager.AddEventListener(CommonSetupEvent.class, CauldronInteractions::Initialize);
    }

    @Override
    public void RegisterMenuTypes(IMenuTypeRegistrar registrar) {
        IronShulkerBoxesMenuTypes.Initialize(registrar);
    }

    @Override
    public void RegisterRegistryItems(IRegistryRegistrar registrar) {
        RecipesInitializer.Initialize(registrar);
    }

    @Override
    public String GetModId() { return MOD_ID; }

    @Override
    public void Dispose() {
        IronShulkerBoxesNetworking.MANAGER = null;
        LOGGER = null;
    }
}
