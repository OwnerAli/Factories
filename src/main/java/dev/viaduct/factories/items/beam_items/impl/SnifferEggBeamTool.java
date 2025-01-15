package dev.viaduct.factories.items.beam_items.impl;

import dev.viaduct.factories.items.beam_items.AbstractBeamItem;
import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import org.bukkit.Material;

public class SnifferEggBeamTool extends AbstractBeamItem {

    public SnifferEggBeamTool() {
        super("sniffer_egg_beam_tool", new BeamConfiguration(
                BeamTransformer.BeamType.Laser,
                Material.SNIFFER_EGG,
                2.0,
                10,
                3
        ));
    }

}
