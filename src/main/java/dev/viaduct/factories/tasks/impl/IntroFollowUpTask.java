package dev.viaduct.factories.tasks.impl;

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
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE)),
                new GeneratorPlaceObjective(1, "oak_wood_generator"),
                new CrankGeneratorTask(1, "Hold right click on the", "generator that you placed."),
                new BlockBreakObjective(Material.OAK_WOOD, 1, "Mine the block that you generated."));
//                new BlueprintRevealObjective(1, "Reveal your first blueprint."),
//                new BlueprintCompleteObjective(1, "Complete your first blueprint.",
//                        "&7Hover over blueprint", "&7in your inventory", "&7for more info."),
//                new GeneratorPlaceObjective(1, "oak_wood_generator"),
//                new CrankGeneratorTask(1, "Hold right click on", "the generator", "that you placed."),
//                new BlockBreakObjective(Material.OAK_WOOD, 1, "Mine the block you", "generated to earn", "4 wood resources."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}
