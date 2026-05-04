package com.github.mdcdi1315.modernized_iron_shulker_boxes;

import com.github.mdcdi1315.basemodslib.config.IModConfig;
import com.github.mdcdi1315.basemodslib.config.ConfigField;
import com.github.mdcdi1315.modernized_iron_shulker_boxes.menu.client.MenuItemsPlacementDataLoader;

public class Config
    implements IModConfig
{
    public static Config INSTANCE;

    @ConfigField(
            field_name = "menu_texture_and_placement_data_id",
            comment = "[Translate]configuration.modernized_iron_shulker_boxes_client.menu_texture_and_placement_data_id.desc"
    )
    public String MenuTextureAndPlacementDataIdentifier;

    public Config()
    {
        MenuTextureAndPlacementDataIdentifier = MenuItemsPlacementDataLoader.DEFAULT_DATA;
    }

    @Override
    public String GetName() { return "modernized_iron_shulker_boxes-client"; }
}
