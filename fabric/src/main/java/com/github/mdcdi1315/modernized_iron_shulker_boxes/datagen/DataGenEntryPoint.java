package com.github.mdcdi1315.modernized_iron_shulker_boxes.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;

public class DataGenEntryPoint
    implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabric_data_generator)
    {
        var p = fabric_data_generator.createPack();
        p.addProvider(RecipesGenerator::new);
    }
}
