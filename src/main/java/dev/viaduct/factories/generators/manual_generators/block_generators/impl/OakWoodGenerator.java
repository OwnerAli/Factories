package dev.viaduct.factories.generators.manual_generators.block_generators.impl;

import org.bukkit.Color;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public class OakWoodGenerator extends BlockManualGenerator {

    public OakWoodGenerator(String id) {
        super(id, Material.OAK_WOOD, 10, TimeUnit.SECONDS,
                true, true, Color.RED);
    }

    @Override
    public String getFormattedName() {
        return "#a8996fOak Wood Generator";
    }

}
