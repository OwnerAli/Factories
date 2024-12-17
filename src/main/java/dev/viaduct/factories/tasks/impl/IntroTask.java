package dev.viaduct.factories.tasks.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.impl.ClearDebrisFromPlotObjective;
import dev.viaduct.factories.objectives.impl.UnlockLandObjective;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.rules.impl.DenyBlockPlaceRule;
import dev.viaduct.factories.tasks.Task;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Set;

public class IntroTask extends Task {

    public IntroTask() {
        super("Clear Your Plot", new IntroFollowUpTask(), true,
                Set.of(new DenyBlockPlaceRule()),
                List.of(new PlaySoundAction(Sound.ITEM_GOAT_HORN_SOUND_1)),
                List.of(new PlaySoundAction(Sound.UI_TOAST_CHALLENGE_COMPLETE),
                        new GiveItemAction(FactoriesPlugin.getRegistryManager()
                                .getRegistry(BlueprintRegistry.class)
                                .get("oak_wood_generator")
                                .get()
                                .getRevealItem())),
                new ClearDebrisFromPlotObjective(Material.OAK_FENCE),
                new UnlockLandObjective(1, "Unlock a square of", "land."));
    }

    @Override
    public void end(FactoryPlayer factoryPlayer) {
    }

}