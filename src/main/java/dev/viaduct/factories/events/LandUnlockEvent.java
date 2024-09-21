package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class LandUnlockEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final FactoryPlayer factoryPlayer;
    private final GridSquare gridSquare;

    public LandUnlockEvent(FactoryPlayer factoryPlayer, GridSquare gridSquare) {
        this.factoryPlayer = factoryPlayer;
        this.gridSquare = gridSquare;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}