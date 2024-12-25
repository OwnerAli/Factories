package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

@Getter
public class BlockBreakObjective extends Objective {

    private final Material blockType;

    public BlockBreakObjective(Material blockType, int amount) {
        super(amount, "Break " + amount + " " + blockType.name() + " Blocks");
        this.blockType = blockType;
    }

    public BlockBreakObjective(Material blockType, int amount, String... description) {
        super(amount, description);
        this.blockType = blockType;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != blockType) return;

        FactoryPlayerRegistry.getInstance()
                .get(event.getPlayer())
                .ifPresent(this::progressObjective);
    }

}