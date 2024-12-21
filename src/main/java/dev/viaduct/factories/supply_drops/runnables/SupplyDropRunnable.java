package dev.viaduct.factories.supply_drops.runnables;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.supply_drops.SupplyDrop;
import org.bukkit.scheduler.BukkitRunnable;

public class SupplyDropRunnable extends BukkitRunnable {

    private final long timeInSeconds;
    private final SupplyDrop supplyDrop;

    public SupplyDropRunnable(long timeInSeconds, SupplyDrop supplyDrop) {
        this.timeInSeconds = timeInSeconds;
        this.supplyDrop = supplyDrop;
    }

    public void start() {
        runTaskLaterAsynchronously(FactoriesPlugin.getInstance(),
                timeInSeconds * 20L).getTaskId();
    }

    @Override
    public void run() {

    }

}
