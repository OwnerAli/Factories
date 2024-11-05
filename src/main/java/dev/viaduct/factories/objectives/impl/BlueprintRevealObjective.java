package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.BlueprintRevealEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class BlueprintRevealObjective extends Objective {

    private final int amount;

    public BlueprintRevealObjective(int amount, String... description) {
        super(amount, description);
        this.amount = amount;
    }

    @Override
    public int getAmount(FactoryPlayer factoryPlayer) {
        return amount;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        // No setup required
    }

    @EventHandler
    public void onBlueprintUnlock(BlueprintRevealEvent event) {
        progressObjective(event.getFactoryPlayer());
    }

}
