package dev.viaduct.factories.listeners;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        super();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new FactoryPlayer(event.getPlayer()).register();
        FactoriesPlugin.getRegistryManager()
                .getRegistry(BlueprintRegistry.class)
                .giveTestBP(event.getPlayer());
    }

}
