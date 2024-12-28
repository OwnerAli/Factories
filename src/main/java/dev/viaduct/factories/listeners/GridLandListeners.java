package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.lands.Land;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.settings.SettingType;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GridLandListeners implements Listener {

    private final FactoryPlayerRegistry factoryPlayerRegistry;

    public GridLandListeners(FactoryPlayerRegistry factoryPlayerRegistry) {
        this.factoryPlayerRegistry = factoryPlayerRegistry;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!(event.getBlock().getWorld().getName().equals("factories_world"))) return;
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(factoryPlayer -> {
            Land playerLand = factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND);

            if (playerLand == null) return;
            if (playerLand.inAccessibleSquare(event.getBlock().getLocation())) return;
            event.setCancelled(true);
            Chat.tell(event.getPlayer(), "&cYou cannot place blocks outside your land!");
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!(event.getBlock().getWorld().getName().equals("factories_world"))) return;
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(factoryPlayer -> {
            event.setDropItems(false);
            Land playerLand = factoryPlayer.getSettingHolder().getSetting(SettingType.PLAYER_LAND);

            if (playerLand == null) return;
            if (playerLand.inAccessibleSquare(event.getBlock().getLocation())) return;
            event.setCancelled(true);
            Chat.tell(event.getPlayer(), "&cYou cannot break blocks outside your land!");
        });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (event.getTo() == null) return;
        if (event.getTo().getWorld() == null) return;
        if (!(event.getTo().getWorld().getName().equals("factories_world"))) return;

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
