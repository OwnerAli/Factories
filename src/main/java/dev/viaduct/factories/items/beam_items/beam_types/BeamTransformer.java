package dev.viaduct.factories.items.beam_items.beam_types;

import dev.viaduct.factories.items.beam_items.beam_types.impl.BulletBeamTransformer;
import dev.viaduct.factories.items.beam_items.beam_types.impl.LaserBeamTransformer;
import lombok.Getter;
import org.bukkit.entity.Display;
import org.bukkit.util.Vector;

public interface BeamTransformer {

    @Getter
    enum BeamType {

        Laser(new LaserBeamTransformer()),
        Bullet(new BulletBeamTransformer());

        private final BeamTransformer beamTransformer;

        BeamType(BeamTransformer beamTransformer) {
            this.beamTransformer = beamTransformer;
        }

    }

    void transform(Display display);

    Vector getSpawnOffset(Vector direction);

}
