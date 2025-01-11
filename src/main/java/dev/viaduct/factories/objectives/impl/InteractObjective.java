package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractObjective extends Objective {

    private final Material blockToClick;
    private final Material itemInHand;

    public InteractObjective(int amount, Material blockToClick, Material itemInHand, String... description) {
        super(amount, description);
        this.blockToClick = blockToClick;
        this.itemInHand = itemInHand;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (itemInHand != null && !itemInHand.equals(event.getMaterial())) return;
        if (blockToClick != null) {
            if (event.getClickedBlock() == null) return;
            if (!blockToClick.equals(event.getClickedBlock().getType())) return;
        }

        FactoryPlayerRegistry.getInstance()
                .get(event.getPlayer())
                .ifPresent(this::progressObjective);
    }

}
