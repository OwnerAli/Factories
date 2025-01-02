package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.actions.impl.AddResourceAction;
import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.BlockBreakObjective;
import dev.viaduct.factories.objectives.impl.CrankGeneratorTask;
import dev.viaduct.factories.objectives.impl.GeneratorPlaceObjective;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class IntroFollowUpTask extends Task {

    public IntroFollowUpTask() {
        super("Create your first generator!", new MarketTutorialTask(), true,
                Set.of(), List.of(),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE),
                        new AddResourceAction("stone", 10)),
                new GeneratorPlaceObjective(1, "wood_generator_t1"),
                new CrankGeneratorTask(1, "Hold right click on the", "generator that you placed."),
                new BlockBreakObjective(Material.OAK_WOOD, 1, "Mine the block that", "you generated to earn",
                        "&e&n4 wood resources."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {}

}
