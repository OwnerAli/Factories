package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import lombok.Getter;

@Getter
public class LandUnlockEvent extends FactoryPlayerEvent {

    private final GridSquare gridSquare;

    public LandUnlockEvent(FactoryPlayer factoryPlayer, GridSquare gridSquare) {
        super(factoryPlayer);
        this.gridSquare = gridSquare;
    }

}