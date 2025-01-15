package dev.viaduct.factories.items.beam_items.impl;

import dev.viaduct.factories.items.beam_items.AbstractBeamItem;
import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import org.bukkit.Material;

public class GlassBeamTool extends AbstractBeamItem {

    public GlassBeamTool() {
        super("glass_beam_tool", new BeamConfiguration(
                BeamTransformer.BeamType.Laser,
                Material.RED_STAINED_GLASS,
                5.0,
                5,
                1));
    }

}
