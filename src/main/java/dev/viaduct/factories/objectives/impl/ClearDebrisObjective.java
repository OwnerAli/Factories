package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.settings.SettingType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Set;

public abstract class ClearDebrisObjective extends Objective {

    private final Set<Material> debrisSet;

    public ClearDebrisObjective(Material... debris) {
        super(0, "Clear the debris", "from your area!");
        this.debrisSet = Set.of(debris);
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        Area currentTaskArea = factoryPlayer.getTaskHolder()
                .getCurrentTaskArea();

        if (currentTaskArea == null) return;
        currentTaskArea.countBlocksInArea(factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND).getLocOfCenterOfIsland(),
                debrisSet);

        setDescription(List.of("Clear " + debrisSet.toString().replace("[", "").replace("]", ""),
                "from your area!"));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!debrisSet.contains(event.getBlock().getType())) return;

        FactoryPlayerRegistry.getInstance()
                .get(event.getPlayer())
                .ifPresent(this::progressObjective);
    }

}
