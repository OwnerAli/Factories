package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.lands.Land;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.settings.SettingType;
import org.bukkit.Material;

public class ClearDebrisFromPlotObjective extends ClearDebrisObjective {

    public ClearDebrisFromPlotObjective(Material... debris) {
        super(debris);
    }

    @Override
    public int getAmount(FactoryPlayer factoryPlayer) {
        return factoryPlayer.getTaskHolder()
                .getCurrentTaskArea()
                .getAmountOfMatchingBlocksInArea();
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        Land playerLand = factoryPlayer.getSettingHolder()
                .getSetting(SettingType.PLAYER_LAND);

        if (playerLand == null) return;
        Area area = new Area(playerLand.getAccessibleSquares());

        area.toggleOutline(factoryPlayer.getPlayer());
        factoryPlayer.getTaskHolder().setCurrentTaskArea(area);

        super.setupObjectiveForPlayer(factoryPlayer);
    }

}
