package dev.viaduct.factories.supply_drops;

import dev.viaduct.factories.supply_drops.runnables.ParticleRunnable;
import org.bukkit.entity.Entity;

public record FallingSupplyDrop(String supplyDropId, Entity fallingEntity) {

    public void spawnParticles() {
        new ParticleRunnable(this).start();
    }

}