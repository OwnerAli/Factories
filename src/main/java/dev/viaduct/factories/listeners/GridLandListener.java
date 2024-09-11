package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.lands.Land;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.settings.SettingType;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GridLandListener implements Listener {

    private final FactoryPlayerRegistry factoryPlayerRegistry;

    public GridLandListener(FactoryPlayerRegistry factoryPlayerRegistry) {
        this.factoryPlayerRegistry = factoryPlayerRegistry;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(factoryPlayer -> {
            Land playerLand = factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND);

            if (playerLand == null) return;
            if (playerLand.inAccessibleSquare(event.getBlock().getLocation())) return;
            event.setCancelled(true);
            event.getPlayer().sendMessage("You cannot place blocks outside your land!");
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(factoryPlayer -> {
            Land playerLand = factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND);

            if (playerLand == null) return;
            if (playerLand.inAccessibleSquare(event.getBlock().getLocation())) return;
            event.setCancelled(true);

            if (playerLand.purchaseAccessibleSquare(event.getBlock().getLocation())) {
                Chat.tell(event.getPlayer(), "You have purchased a new square of land!");
                return;
            }
            event.getPlayer().sendMessage("You cannot break blocks outside your land!");
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(factoryPlayer -> {
            Land playerLand = factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND);

            if (playerLand == null) return;

            Location toLocation = event.getTo();

            if (toLocation == null) return;
            if (toLocation.getWorld() == null) return;
            if (playerLand.inAccessibleSquare(toLocation)) return;
            event.setCancelled(true);

            playerLand.spawnPurchaseSquareText(event.getPlayer().getEyeLocation().clone());
        });
    }

}
