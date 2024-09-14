package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.tasks.TaskHolder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

@Getter
public class BlockBreakObjective extends Objective {

    private final Material blockType;

    public BlockBreakObjective(Material blockType, int amount) {
        super("Break " + amount + " " + blockType.name() + " Blocks", amount);
        this.blockType = blockType;
    }

    @Override
    public int getAmount(FactoryPlayer factoryPlayer) {
        return getAmount();
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {

    }

    @Override
    public void progressObjective(FactoryPlayer factoryPlayer) {
        TaskHolder taskHolder = factoryPlayer.getTaskHolder();
        taskHolder.incrementObjectiveProgress(factoryPlayer, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != blockType) return;

        FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .get(event.getPlayer())
                .ifPresent(this::progressObjective);
    }

}
