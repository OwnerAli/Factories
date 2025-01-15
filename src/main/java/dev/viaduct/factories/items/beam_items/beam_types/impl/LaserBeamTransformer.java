package dev.viaduct.factories.items.beam_items.beam_types.impl;

import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

public class LaserBeamTransformer implements BeamTransformer {

    @Override
    public void transform(Display display) {
        Transformation transformation = display.getTransformation();
        transformation.getScale().set(0.5, 0.5, 2.0); // Thin and long
        display.setTransformation(transformation);
    }

    @Override
    public Vector getSpawnOffset(Vector direction) {
        return direction.getCrossProduct(new Vector(0, 1, 0))
                .normalize()
                .multiply(0.2);
    }

}