package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.WCSContributionEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class WCSContributionObjective extends Objective {

    public WCSContributionObjective(int amount, String... description) {
        super(amount, description);
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {

    }

    @EventHandler
    public void onContribute(WCSContributionEvent event) {
        progressObjective(event.getFactoryPlayer());
    }

}
