package dev.viaduct.factories.generators.manual_generators.block_generators.impl.stone;

import dev.viaduct.factories.generators.manual_generators.block_generators.impl.BlockManualGenerator;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.concurrent.TimeUnit;

public class StoneGeneratorT1 extends BlockManualGenerator {

    public StoneGeneratorT1(String id) {
        super(id, Material.STONE, 15, TimeUnit.SECONDS, true,
                true, Color.GREEN);
    }

    @Override
    public String getFormattedName() {
        return "#bfbfbfStone Generator";
    }

}
