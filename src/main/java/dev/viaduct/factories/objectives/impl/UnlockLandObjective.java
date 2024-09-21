package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.LandUnlockEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class UnlockLandObjective extends Objective {

    public UnlockLandObjective(int amount, String... description) {
        super(amount, description);
    }

    @Override
    public int getAmount(FactoryPlayer factoryPlayer) {
        return 1;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        // No setup required
    }

    @EventHandler
    public void onLandUnlock(LandUnlockEvent event) {
        progressObjective(event.getFactoryPlayer());
    }

}
