package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.ProgressDisplayCompletionEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class CrankGeneratorTask extends Objective {

    public CrankGeneratorTask(int amount, String... description) {
        super(amount, description);
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        // No setup required
    }

    @EventHandler
    public void onGeneratorPlace(ProgressDisplayCompletionEvent event) {
        progressObjective(event.getFactoryPlayer());
    }

}
