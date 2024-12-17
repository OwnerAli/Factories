package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.generators.Generator;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class ProgressDisplayCompletionEvent extends FactoryPlayerEvent {

    private final Generator generator;
    private final Location displayLocation;

    public ProgressDisplayCompletionEvent(Generator generator, Location displayLocation, FactoryPlayer factoryPlayer) {
        super(factoryPlayer);
        this.generator = generator;
        this.displayLocation = displayLocation;
    }

}
