package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import lombok.Getter;

@Getter
public class OutlineAreaAction implements Action {

    private final Area area;

    public OutlineAreaAction(Area area) {
        this.area = area;
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        area.toggleOutline(factoryPlayer.getPlayer());
    }

}