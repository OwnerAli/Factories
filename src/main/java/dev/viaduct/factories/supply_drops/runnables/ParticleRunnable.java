package dev.viaduct.factories.supply_drops.runnables;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.registries.impl.SupplyDropRegistry;
import dev.viaduct.factories.supply_drops.FallingSupplyDrop;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleRunnable extends BukkitRunnable {

    private final FallingSupplyDrop fallingSupplyDrop;

    public ParticleRunnable(FallingSupplyDrop fallingSupplyDrop) {
        this.fallingSupplyDrop = fallingSupplyDrop;
    }

    public void start() {
        runTaskAsynchronously(FactoriesPlugin.getInstance());
    }

    @Override
    public void run() {
        Location fallingDropLocation = fallingSupplyDrop.fallingEntity()
                .getLocation();
        World fallingDropLocationWorld = fallingDropLocation.getWorld();

        if (fallingDropLocationWorld == null) {
            cancel();
            return;
        }
        if (SupplyDropRegistry.getInstance()
                .getFallingSupplyDrop(fallingSupplyDrop.fallingEntity()).isEmpty()) {
            cancel();
        }

        fallingDropLocationWorld.spawnParticle(
                org.bukkit.Particle.FLAME,
                fallingDropLocation,
                10,
                0.5,
                0.5,
                0.5,
                0.1
        );
    }

}
