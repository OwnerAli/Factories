package dev.viaduct.factories.listeners;

import dev.viaduct.factories.events.SupplyDropClaimEvent;
import dev.viaduct.factories.guis.menus.SupplyDropLootMenu;
import dev.viaduct.factories.registries.impl.SupplyDropRegistry;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SupplyDropListeners implements Listener {

    private final SupplyDropRegistry supplyDropRegistry;

    public SupplyDropListeners() {
        this.supplyDropRegistry = SupplyDropRegistry.getInstance();
    }

    @EventHandler
    public void onBlockLand(EntityChangeBlockEvent event) {
        // Get the falling drop if it exists
        supplyDropRegistry.getFallingSupplyDrop(event.getEntity())
                .ifPresent(fallingSupplyDrop -> {
                    // Remove from the falling supply drop registry
                    supplyDropRegistry.removeFallingSupplyDrop(event.getEntity());

                    // Add to the landed supply drop registry
                    supplyDropRegistry.get(fallingSupplyDrop.getSupplyDropId())
                            .ifPresentOrElse(supplyDrop -> supplyDrop.landFallenSupplyDrop(event.getBlock().getLocation(), fallingSupplyDrop),
                                    () -> Chat.log("Failed to add landed drop: Supply drop not found"));
                });
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        supplyDropRegistry.getLandedDrop(event.getClickedBlock().getLocation())
                .ifPresent(supplyDrop -> supplyDrop.getInteractEventConsumer().accept(event));
    }

    @EventHandler
    public void onSupplyDropClaimEvent(SupplyDropClaimEvent event) {
        event.getSupplyDrop().clearSupplyDrop(event.getLocation());
        new SupplyDropLootMenu().showToPlayer(event.getFactoryPlayer().getPlayer(), event.getSupplyDrop());
    }

}
