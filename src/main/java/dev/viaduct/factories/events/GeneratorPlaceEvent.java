package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.generators.Generator;
import lombok.Getter;

@Getter
public class GeneratorPlaceEvent extends FactoryPlayerEvent {

    private final Generator generator;

    public GeneratorPlaceEvent(FactoryPlayer factoryPlayer, Generator generator) {
        super(factoryPlayer);
        this.generator = generator;
    }

}
