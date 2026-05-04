package com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client;

import com.github.mdcdi1315.DotNetLayer.System.ArgumentNullException;
import com.github.mdcdi1315.DotNetLayer.System.InvalidOperationException;
import com.github.mdcdi1315.DotNetLayer.System.Diagnostics.CodeAnalysis.MaybeNull;

import com.github.mdcdi1315.basemodslib.BaseModsLib;
import com.github.mdcdi1315.basemodslib.ModdingEnvironment;

import com.github.mdcdi1315.modernized_iron_shulker_boxes.IronShulkerBoxesModInstance;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.IronShulkerBoxesMenuTypes;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.AbstractIronShulkerBoxMenu;

import net.minecraft.world.inventory.MenuType;

import java.util.*;

public final class MenuItemsPlacementDataLoader
{
    private MenuItemsPlacementDataLoader() {}

    public static final String DEFAULT_DATA = "Default";

    private static String loaded_name;
    private static final LinkedHashMap<MenuType<? extends AbstractIronShulkerBoxMenu>, MenuItemsPlacementData> data_map;

    static {
        if (BaseModsLib.GetEnvironment() == ModdingEnvironment.SERVER) {
            throw new InvalidOperationException("The Client Data Loader was touched while it should not!");
        } else {
            loaded_name = null;
            data_map = LinkedHashMap.newLinkedHashMap(7);
        }
    }

    public static void LoadDefault()
    {
        loaded_name = DEFAULT_DATA;
        data_map.put(
                IronShulkerBoxesMenuTypes.COPPER_SHULKER_BOX,
                new MenuItemsPlacementData(
                        new GUITextureData(
                                IronShulkerBoxesModInstance.ID("textures/gui/copper_container.png"),
                                256,
                                256,
                                184,
                                204
                        ),
                        9
                )
        );
        data_map.put(
                IronShulkerBoxesMenuTypes.IRON_SHULKER_BOX,
                new MenuItemsPlacementData(
                        new GUITextureData(
                                IronShulkerBoxesModInstance.ID("textures/gui/iron_container.png"),
                                256,
                                256,
                                184,
                                222
                        ),
                        9
                )
        );
        data_map.put(
                IronShulkerBoxesMenuTypes.GOLD_SHULKER_BOX,
                new MenuItemsPlacementData(
                        new GUITextureData(
                                IronShulkerBoxesModInstance.ID("textures/gui/gold_container.png"),
                                256,
                                276,
                                184,
                                276
                        ),
                        9
                )
        );
        var diamond_data = new MenuItemsPlacementData(
                new GUITextureData(
                        IronShulkerBoxesModInstance.ID("textures/gui/diamond_container.png"),
                        256,
                        276,
                        238,
                        276
                ),
                12
        );
        data_map.put(IronShulkerBoxesMenuTypes.CRYSTAL_SHULKER_BOX, diamond_data);
        data_map.put(IronShulkerBoxesMenuTypes.DIAMOND_SHULKER_BOX, diamond_data);
        data_map.put(
                IronShulkerBoxesMenuTypes.OBSIDIAN_SHULKER_BOX,
                new MenuItemsPlacementData(
                        new GUITextureData(
                                IronShulkerBoxesModInstance.ID("textures/gui/obsidian_container.png"),
                                256,
                                384,
                                238,
                                312
                        ),
                        12
                )
        );
        data_map.put(
                IronShulkerBoxesMenuTypes.NETHERITE_SHULKER_BOX,
                new MenuItemsPlacementData(
                        new GUITextureData(
                                IronShulkerBoxesModInstance.ID("textures/gui/netherite_container.png"),
                                256,
                                384,
                                256,
                                348
                        ),
                        13
                )
        );
    }

    public static void MergeDataFromMapAndId(Map<MenuType<? extends AbstractIronShulkerBoxMenu>, MenuItemsPlacementData> map, String id)
            throws ArgumentNullException
    {
        ArgumentNullException.ThrowIfNull(map, "map");
        ArgumentNullException.ThrowIfNullOrEmpty(id, "id");
        data_map.putAll(map);
        loaded_name = id;
    }

    @MaybeNull
    public static String GetLoadedName() { return loaded_name; }

    @MaybeNull
    public static MenuItemsPlacementData GetPlacementData(MenuType<? extends AbstractIronShulkerBoxMenu> menu_type) { return data_map.get(menu_type); }
}
