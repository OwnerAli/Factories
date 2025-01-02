package dev.viaduct.factories.generators.manual_generators.block_generators.impl.stone;

import dev.viaduct.factories.generators.manual_generators.block_generators.impl.BlockManualGenerator;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public class StoneGeneratorT2 extends BlockManualGenerator {

    public StoneGeneratorT2(String id) {
        super(id, Material.MOSSY_COBBLESTONE, 10, TimeUnit.SECONDS, true,
                true, Color.LIME);
    }

    @Override
    public String getFormattedName() {
        return "#bfbfbfStone Generator";
    }

}
