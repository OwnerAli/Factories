package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.Registry;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class FactoryPlayerRegistry extends Registry<UUID, FactoryPlayer> {

    public Optional<FactoryPlayer> get(Player player) {
        return get(player.getUniqueId());
    }

    // Lazy Initialization
    public static FactoryPlayerRegistry getInstance() {
        return FactoryPlayerRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final FactoryPlayerRegistry instance = new FactoryPlayerRegistry();
    }

}
