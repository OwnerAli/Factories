package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Optional;

@Getter
public class FactoryPlayerEvent extends CustomEvent {

    private final FactoryPlayer factoryPlayer;

    public FactoryPlayerEvent(FactoryPlayer factoryPlayer) {
        this.factoryPlayer = factoryPlayer;
    }

    public FactoryPlayerEvent(Player player) {
        Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance().get(player);
        if (factoryPlayerOptional.isEmpty())
            throw new IllegalArgumentException("FactoryPlayer not found for player " + player.getName());
        this.factoryPlayer = factoryPlayerOptional.get();
    }

}
