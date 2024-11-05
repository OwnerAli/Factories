package dev.viaduct.factories.rules;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import org.bukkit.event.Listener;

public interface Rule extends Listener {

    String getDescription();

    default boolean ignoreRule(FactoryPlayer player) {
        return player.getPlayer().isOp();
    }

}