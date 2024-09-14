package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.lands.Land;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.settings.SettingHolder;
import dev.viaduct.factories.settings.SettingType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    private final FactoryPlayerRegistry factoryPlayerRegistry;

    public PlayerInteractEntityListener(FactoryPlayerRegistry factoryPlayerRegistry) {
        this.factoryPlayerRegistry = factoryPlayerRegistry;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId())
                .ifPresent(factoryPlayer -> {
                    SettingHolder settingHolder = factoryPlayer.getSettingHolder();
                    Land playerLand = settingHolder.getSetting(SettingType.PLAYER_LAND);

                    if (playerLand == null) return;
                    if (!playerLand.hasInteractedEntity(event.getRightClicked())) return;
                    playerLand.purchaseAccessibleSquare(event.getRightClicked().getLocation());
                });
    }

}
