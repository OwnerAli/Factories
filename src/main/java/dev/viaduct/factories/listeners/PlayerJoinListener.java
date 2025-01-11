package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import world.bentobox.bentobox.api.events.island.IslandCreatedEvent;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        super();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new FactoryPlayer(event.getPlayer()).register();
    }

    @EventHandler
    public void onIslandCreate(IslandCreatedEvent event) {
        FactoryPlayerRegistry.getInstance()
                .get(event.getOwner())
                .ifPresent(FactoryPlayer::setupLand);
    }

}