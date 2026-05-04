package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client;

import com.github.mdcdi1315.DotNetLayer.System.StringUtils;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.Config;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxesMenuTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.AbstractIronShulkerBoxMenu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonParseException;

import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.util.List;
import java.util.HashMap;
import java.util.Optional;

import java.io.IOException;

public final class MenuItemsPlacementDataReloadListener
    implements ResourceManagerReloadListener
{
    @Override
    public void onResourceManagerReload(ResourceManager resource_manager)
    {
        String id = Config.INSTANCE.MenuTextureAndPlacementDataIdentifier;
        IronShulkerBoxesModInstance.LOGGER.info("Loading Iron Shulker Boxes menu placement data: {}", id);
        if (id.equals(MenuItemsPlacementDataLoader.DEFAULT_DATA)) {
            MenuItemsPlacementDataLoader.LoadDefault();
        } else {
            LoadData(resource_manager, id);
        }
    }

    private static void LoadData(ResourceManager resource_manager, String id)
    {
        Gson gson = new Gson();
        HashMap<MenuType<? extends AbstractIronShulkerBoxMenu>, MenuItemsPlacementData> hm = new HashMap<>();
        boolean loaded_all = true;
        MenuItemsPlacementData temp;
        for (MenuType<? extends AbstractIronShulkerBoxMenu> type : List.of(
                IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX,
                IronShulkerBoxesMenuTypes.NETHERITE_SHULKER_BOX
        )) {
            temp = LoadIndividualResource(type, resource_manager, gson, id);
            if (temp == null) {
                loaded_all = false;
                break;
            } else {
                hm.put(type, temp);
            }
        }
        if (loaded_all) {
            MenuItemsPlacementDataLoader.MergeDataFromMapAndId(hm, id);
        } else {
            IronShulkerBoxesModInstance.LOGGER.warn("Failed to load Iron Shulker Boxes menu placement data {}, loading default.", id);
            MenuItemsPlacementDataLoader.LoadDefault();
        }
    }

    private static MenuItemsPlacementData LoadIndividualResource(
            MenuType<? extends AbstractIronShulkerBoxMenu> menu_type,
            ResourceManager resource_manager,
            Gson gson,
            String id
    ) {
        ResourceLocation menu_location = BuiltInRegistries.MENU.getKey(menu_type);
        if (menu_location == null) {
            return null;
        } else {
            ResourceLocation location = ResourceLocation.tryBuild(
                    IronShulkerBoxesModInstance.MOD_ID,
                    StringUtils.Format("menu_placements/{0}/{1}.json", id, menu_location.getPath())
            );
            if (location == null) {
                IronShulkerBoxesModInstance.LOGGER.info("Failed to load Iron Shulker Boxes menu placement data {} because the preset name is invalid.", id);
                return null;
            } else {
                Optional<Resource> rc = resource_manager.getResource(location);
                if (rc.isPresent()) {
                    return LoadResource(menu_type, rc.get(), gson);
                } else {
                    IronShulkerBoxesModInstance.LOGGER.error("Failed to load Iron Shulker Boxes menu placement data for {} because the file is missing.", location);
                    return null;
                }
            }
        }
    }

    private static MenuItemsPlacementData LoadResource(MenuType<? extends AbstractIronShulkerBoxMenu> menu, Resource resource, Gson gson)
    {
        try (JsonReader jr = new JsonReader(resource.openAsReader())) {
            DataResult<MenuItemsPlacementData> d = MenuItemsPlacementData.CODEC.parse(JsonOps.INSTANCE, gson.fromJson(jr, JsonElement.class));
            if (d.isError()) {
                IronShulkerBoxesModInstance.LOGGER.info("Iron Shulker Boxes menu placement data could not be loaded: {}", d.error().get().message());
                return null;
            } else {
                return d.result().get();
            }
        } catch (IOException | JsonParseException ioex) {
            IronShulkerBoxesModInstance.LOGGER.info("Iron Shulker Boxes menu placement data could not be loaded.", ioex);
            return null;
        }
    }
}
