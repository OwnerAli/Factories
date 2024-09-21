package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.ClearDebrisFromPlotObjective;
import dev.viaduct.factories.objectives.impl.UnlockLandObjective;
import dev.viaduct.factories.rules.impl.DenyBlockPlaceRule;
import dev.viaduct.factories.tasks.Task;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class IntroTask extends Task {

    public IntroTask() {
        super("Clear Your Plot", null, true,
                Set.of(new DenyBlockPlaceRule()),
                List.of(new PlaySoundAction(Sound.ITEM_GOAT_HORN_SOUND_1)),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE),
                        new GiveItemAction(new ItemBuilder(Material.FILLED_MAP)
                                .setName("&f&lBlueprint: #a8996fWood Generator")
                                .glowing()
                                .build())),
                new ClearDebrisFromPlotObjective(Material.SHORT_GRASS,
                        Material.OAK_FENCE, Material.MOSS_CARPET, Material.AZALEA, Material.FLOWERING_AZALEA),
                new UnlockLandObjective(1, "Unlock a square of", "land."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}