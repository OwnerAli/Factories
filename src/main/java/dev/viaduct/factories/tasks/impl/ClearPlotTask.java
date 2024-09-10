package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.ClearDebrisFromPlotObjective;
import dev.viaduct.factories.rules.impl.DenyBlockPlaceRule;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class ClearPlotTask extends Task {

    public ClearPlotTask() {
        super("Clear Your Plot", Set.of(new DenyBlockPlaceRule()),
                List.of(new PlaySoundAction(Sound.ITEM_GOAT_HORN_SOUND_1)),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE)),
                new ClearDebrisFromPlotObjective(Material.TALL_GRASS, Material.SHORT_GRASS,
                        Material.OAK_WOOD, Material.OAK_LEAVES,
                        Material.DANDELION, Material.POPPY));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}
