package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.GeneratorPlaceEvent;
import dev.viaduct.factories.objectives.Objective;
import org.bukkit.event.EventHandler;

public class GeneratorPlaceObjective extends Objective {

    private final String generatorId;

    public GeneratorPlaceObjective(int amount, String generatorId) {
        super(amount, "Place down the ", generatorId.replace("_", " ") + ".");
        this.generatorId = generatorId;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {
        // No setup required
    }

    @EventHandler
    public void onGeneratorPlace(GeneratorPlaceEvent event) {
        if (!event.getGenerator().getId().equalsIgnoreCase(generatorId)) return;

        progressObjective(event.getFactoryPlayer());
    }

}
