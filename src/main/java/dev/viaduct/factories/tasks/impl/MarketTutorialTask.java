package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.InteractObjective;
import dev.viaduct.factories.objectives.impl.UnlockLandObjective;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Material;

import java.util.List;
import java.util.Set;

public class MarketTutorialTask extends Task {

    public MarketTutorialTask() {
        super("The Market Matters Most", null, true,
                Set.of(),
                List.of(),
                List.of(),
                new InteractObjective(1, null, Material.NETHER_STAR,
                        "Navigate to the market using", "&ethe nether star in your inventory."),

                new UnlockLandObjective(1, "Expand your factory!",
                        "&7Walk up to a plot of", "&7land and right-click."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}
