package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class ChestClaimObjective extends InteractObjective {

    public ChestClaimObjective(Material itemInHand, String... description) {
        super(1, Material.CHEST, itemInHand, description);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        FactoryPlayerRegistry.getInstance()
                .get((Player) event.getPlayer())
                .ifPresent(factoryPlayer -> {
                    if (!progressObjective(factoryPlayer)) return;

                    Inventory inventory = event.getInventory();
                    if (!inventory.isEmpty()) return;
                    if (inventory.getLocation() == null) return;
                    if (inventory.getLocation().getBlock().getType()
                            != Material.CHEST) return;
                    ((Player) event.getPlayer()).playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    inventory.getLocation().getBlock().setType(Material.AIR);
                });
    }

}
