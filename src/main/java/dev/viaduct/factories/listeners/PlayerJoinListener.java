package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.CustomItemRegistry;
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
        CustomItemRegistry.getInstance().initialize(event.getPlayer());
    }

}