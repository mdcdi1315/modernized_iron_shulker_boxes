
### About Iron Shulker Box Menu Placements

Iron Shulker box menu placements is a system that manages loading of different images for the Iron Shulker Box containers
at run-time. They are needed so that the slots are correctly attached to the texture.

Those placements are conventionally grouped as `presets`, which in turn is specified in the mod's configuration file to load them.

Some advantages of this system:

1. Each player can specify his own images for displaying the menus. The server just dispatches the request, and the client creates and
arranges the image and the container slots as instructed to. 
As such, two players can use different menu textures, and both of them can interact with the box without issues.
2. Textures and placement data are user-defined through resource packs.

#### Declaring a new preset:

1. Create a directory `modernized_iron_shulker_boxes` in your `assets` directory of your resource pack.
2. Create a directory `menu_placements` in the `modernized_iron_shulker_boxes` directory.
3. Create a directory of a name you would like. This is your preset's name. Must be a non-existing one, otherwise you want to override that one.
4. Create `copper_shulker_box_menu.json`, `iron_shulker_box_menu.json`, `gold_shulker_box_menu.json`, `diamond_shulker_box_menu.json`, `crystal_shulker_box_menu.json`, `obsidian_shulker_box_menu.json`, `netherite_shulker_box_menu.json` files.
5. Specify the texture(s) to use. Texture files can be located anywhere, it is enough that they are in `textures/gui` directory so that the texture atlas finds them.
6. Now fill in the required data. Here is an example for the Diamond Shulker Box Menu from the mod's provided `small` preset:

~~~JSON
{
	"gui_texture": {
		"texture_location": "modernized_iron_shulker_boxes:textures/gui/small_preset_diamond_container.png",
		"actual_texture_width": 320,
		"actual_texture_height": 256,
		"used_texture_width": 309,
		"used_texture_height": 243
	},
	"inventory_text_x_position": 60,
	"inventory_text_y_position": 148,
	"inventory_slots_x_position": 75,
	"inventory_slots_y_position": 242,
	"item_stack_count_in_each_row": 16
}
~~~

Fields description:

| Field                        | Description                                                                                              |
|------------------------------|----------------------------------------------------------------------------------------------------------|
| `texture_location`           | Location of the texture to use.                                                                          |
| `actual_texture_width`       | Exact width of the GUI Texture, specified in pixels.                                                     |
| `actual_texture_height`      | Exact height of the GUI Texture, specified in pixels.                                                    |
| `used_texture_width`         | The exact width of the area of the GUI Texture currently in use. Also specified in pixels.               |
| `used_texture_height`        | The exact height of the area of the GUI Texture currently in use. Also specified in pixels.              |
| `inventory_text_x_position`  | Absolute X position where the `Inventory` text will be drawn to. Also specified in pixels.               |
| `inventory_text_y_position`  | Absolute Y position where the `Inventory` text will be drawn to. Also specified in pixels.               |
| `inventory_slots_x_position` | Absolute X position of the top-left corner of the player's inventory slots. Also specified in pixels.    |
| `inventory_slots_y_position` | Absolute Y position of the bottom-left corner of the player's inventory slots. Also specified in pixels. |

> [!NOTE]
Due to how the `inventory_slots_y_position` is expressed due to compatibility reasons, to specify this correctly go to the bottom left corner of 
the inventory slots and add to the found value 8 pixels. 


