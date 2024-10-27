package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.BlueprintCompleteObjective;
import dev.viaduct.factories.objectives.impl.BlueprintRevealObjective;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class IntroFollowUpTask extends Task {

    public IntroFollowUpTask() {
        super("Create your first generator!", null, true,
                Set.of(),
                List.of(new PlaySoundAction(Sound.ITEM_GOAT_HORN_SOUND_1)),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE)),
                new BlueprintRevealObjective(1, "Reveal your first blueprint."),
                new BlueprintCompleteObjective(1, "Complete your first blueprint.", "Hover over it in your inventory", "for more info."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}
