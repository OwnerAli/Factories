package dev.viaduct.factories.items.beam_items.configuration;

import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
public class BeamConfiguration {

    private BeamTransformer.BeamType beamType;
    private Material beamMaterial;
    private double speed;
    private int maxDistance;
    private int shotsPerSecond;

    public BeamConfiguration(BeamTransformer.BeamType beamType, Material beamMaterial,
                             double speed, int maxDistance, int shotsPerSecond) {
        this.beamType = beamType;
        this.beamMaterial = beamMaterial;
        this.speed = speed;
        this.maxDistance = maxDistance;
        this.shotsPerSecond = shotsPerSecond;
    }

    public BeamConfiguration(BeamConfiguration beamConfiguration) {
        this.beamType = beamConfiguration.getBeamType();
        this.beamMaterial = beamConfiguration.getBeamMaterial();
        this.speed = beamConfiguration.getSpeed();
        this.maxDistance = beamConfiguration.getMaxDistance();
        this.shotsPerSecond = beamConfiguration.getShotsPerSecond();
    }

}
