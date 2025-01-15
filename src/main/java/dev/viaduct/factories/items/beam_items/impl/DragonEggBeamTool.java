package dev.viaduct.factories.items.beam_items.impl;

import dev.viaduct.factories.items.beam_items.AbstractBeamItem;
import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import org.bukkit.Material;

public class DragonEggBeamTool extends AbstractBeamItem {

    public DragonEggBeamTool() {
        super("dragon_egg_beam_tool", new BeamConfiguration(
                BeamTransformer.BeamType.Bullet,
                Material.DRAGON_EGG,
                1.0,
                10,
                3
        ));
    }

}