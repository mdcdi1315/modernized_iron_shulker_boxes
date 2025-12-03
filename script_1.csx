
// Just an on-the-fly script for generating the coloring recipes.

using System;
using System.IO;

public const String NAMESPACE = "modernized_iron_shulker_boxes";
public const String RECIPES_PREFIX = "shulker_boxes_coloring/";

private static String CreateFileName(String en, DirectoryInfo base_dir)
=> String.Concat(
	base_dir.FullName,
	"/",
	en,
	".json"
);

private static String CreateEntryName(String color, String shulker_box_resource_location) =>
String.Concat(
	 color.ToLower(),
	 "_",
	 shulker_box_resource_location.Substring(NAMESPACE.Length+1),
	 "_recipe"
);

private static String CreateGroupName(String color) =>
String.Concat(
	NAMESPACE,
	":shulker_box_coloring_",
	color.ToLower()
);

private static void GenerateRecipeIds(DirectoryInfo di, IList<String> names)
{
	using (System.IO.StreamWriter sw = new(String.Concat(di.FullName, "/", "names")))
	{
		foreach (String name in names) {
			sw.WriteLine(String.Format("\t\"{0}:{1}{2}\",", NAMESPACE, RECIPES_PREFIX, name));
		}
	}
}


public static void GenerateColorRecipes(DirectoryInfo di)
{
	if (!di.Exists) {
		di.Create();
	}
	
	String[] colors = { "WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "BROWN", "GREEN", "RED", "BLACK" };
	String[] shulker_boxes = { $"{NAMESPACE}:iron_shulker_box", $"{NAMESPACE}:gold_shulker_box", $"{NAMESPACE}:diamond_shulker_box", $"{NAMESPACE}:copper_shulker_box", $"{NAMESPACE}:crystal_shulker_box", $"{NAMESPACE}:obsidian_shulker_box" };
	List<String> names = new(colors.Length * shulker_boxes.Length);
	foreach (String c in colors) {
		foreach (String sb in shulker_boxes) {
			String en = CreateEntryName(c , sb);
			names.Add(en);
			using (StreamWriter sw = new(CreateFileName(en , di))) {
				sw.WriteLine("{");
				sw.WriteLine("\t\"type\": \"modernized_iron_shulker_boxes:iron_shulker_box_crafting_coloring\", ");
				sw.WriteLine(String.Format("\t\"group\": \"{0}\", " , CreateGroupName(c)));
				sw.WriteLine(String.Format("\t\"color\": \"{0}\", ", c));
				sw.WriteLine(String.Format("\t\"shulker_box\": \"{0}\" ", sb));
				sw.WriteLine("}");
			}
		}
	}
	GenerateRecipeIds(di, names);
}