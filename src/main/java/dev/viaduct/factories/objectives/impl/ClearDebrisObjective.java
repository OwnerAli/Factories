package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.tasks.TaskHolder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

public abstract class ClearDebrisObjective extends Objective {

    private final Set<Material> debrisSet;

    public ClearDebrisObjective(Material... debris) {
        super("Clear the debris from the highlighted area!", 0);
        this.debrisSet = Set.of(debris);
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        Area currentTaskArea = factoryPlayer.getTaskHolder()
                .getCurrentTaskArea();

        if (currentTaskArea == null) return;
        currentTaskArea.updateAmountOfMatchingBlocksInArea(debrisSet);
    }

    @Override
    public void progressObjective(FactoryPlayer factoryPlayer) {
        TaskHolder taskHolder = factoryPlayer.getTaskHolder();
        taskHolder.incrementObjectiveProgress(factoryPlayer, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!debrisSet.contains(event.getBlock().getType())) return;

        FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .get(event.getPlayer())
                .ifPresent(this::progressObjective);
    }

}
