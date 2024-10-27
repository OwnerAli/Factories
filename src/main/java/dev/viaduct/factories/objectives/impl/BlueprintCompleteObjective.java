package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.BlueprintCompleteEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class BlueprintCompleteObjective extends Objective {

    private final int amount;

    public BlueprintCompleteObjective(int amount, String... description) {
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
    public void onBlueprintUnlock(BlueprintCompleteEvent event) {
        progressObjective(event.getFactoryPlayer());
    }

}
