package dev.viaduct.factories.events;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Optional;

@Getter
public class BlueprintRevealEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final FactoryPlayer factoryPlayer;
    private final BlueprintProgress blueprintProgress;

    public BlueprintRevealEvent(Player player, BlueprintProgress blueprintProgress) {
        Optional<FactoryPlayer> factoryPlayerOptional = FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .get(player);
        if (factoryPlayerOptional.isEmpty()) throw new IllegalArgumentException("FactoryPlayer not found for player " + player.getName());

        this.factoryPlayer = factoryPlayerOptional.get();
        this.blueprintProgress = blueprintProgress;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
