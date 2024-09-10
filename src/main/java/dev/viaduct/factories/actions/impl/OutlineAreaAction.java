package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.utils.OutlineUtils;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class OutlineAreaAction implements Action {

    private final Location cornerOne;
    private final Location cornerTwo;
    private final OutlineUtils outlineUtils;

    public OutlineAreaAction(Location cornerOne, Location cornerTwo) {
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
        this.outlineUtils = new OutlineUtils(cornerOne, cornerTwo);
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        outlineUtils.toggleOutline(factoryPlayer.getPlayer());
    }

}
