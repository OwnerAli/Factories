package dev.viaduct.factories.generators.manual_generators.block_generators.impl.wood;

import dev.viaduct.factories.generators.manual_generators.block_generators.impl.BlockManualGenerator;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public class WoodGeneratorT1 extends BlockManualGenerator {

    public WoodGeneratorT1(String id) {
        super(id, Material.OAK_WOOD, 10, TimeUnit.SECONDS,
                true, true, Color.ORANGE);
    }

    @Override
    public String getFormattedName() {
        return "#a8996fOak Wood Generator";
    }

}
