package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.actions.impl.PlaySoundAction;
import dev.viaduct.factories.blueprints.Blueprint;
import dev.viaduct.factories.blueprints.progress.impl.ItemStackProgressable;
import dev.viaduct.factories.blueprints.progress.impl.ResourceProgressable;
import dev.viaduct.factories.registries.Registry;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlueprintRegistry extends Registry<String, Blueprint> {

    public void giveTestBP(Player player) {
        Blueprint bp = new Blueprint("Wood Generator",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ItemStackProgressable(new ItemStack(Material.DIAMOND, 1)),
                        new ResourceProgressable(3.0, "wood")),
                new GiveItemAction(FactoriesPlugin.getRegistryManager()
                        .getRegistry(GeneratorRegistry.class)
                        .get("oak_wood_generator")
                        .get()
                        .getGeneratorPlaceItem()),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));
        register(bp.getId(), bp);
        player.getInventory().addItem(bp.getItem());
    }

}
