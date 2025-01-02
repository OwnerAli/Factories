package dev.viaduct.factories.generators.manual_generators.block_generators.impl;

import org.bukkit.Color;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public class OakWoodGeneratorT2 extends BlockManualGenerator {

    public OakWoodGeneratorT2(String id) {
        super(id, Material.DARK_OAK_WOOD, 5, TimeUnit.SECONDS,
                true, true, Color.RED);
    }

    @Override
    public String getFormattedName() {
        return "#a8996fOak Wood Generator";
    }

}
