package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.InteractObjective;
import dev.viaduct.factories.rules.impl.DenyBlockPlaceRule;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class IntroTask extends Task {

    public IntroTask() {
        super("Welcome Aboard", new IntroFollowUpTask(), true,
                Set.of(new DenyBlockPlaceRule()),
                List.of(new PlaySoundAction(Sound.ITEM_GOAT_HORN_SOUND_1)),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE)),
                new InteractObjective(1, Material.CHEST, null,
                        "&6Sun Industries &f→ We've left a", "gift to get you started.",
                        "&7Open up the chest to claim it!"));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}