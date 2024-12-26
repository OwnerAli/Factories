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
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlueprintRegistry extends Registry<String, Blueprint> {

    public void initialize() {
        Blueprint oakWoodGenerator = new Blueprint("oak_wood_generator",
                "#a8996fOak Wood Generator",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ResourceProgressable(3.0, "wood")),
                new GiveItemAction(FactoriesPlugin.getRegistryManager()
                        .getRegistry(GeneratorRegistry.class)
                        .get("oak_wood_generator")
                        .get()
                        .getGeneratorPlaceItem()),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));
        Blueprint stoneGenerator = new Blueprint("stone_generator",
                "#bfbfbfStone Generator",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ItemStackProgressable(new ItemStack(Material.DIAMOND, 1)),
                        new ResourceProgressable(20.0, "stone")),
                new GiveItemAction(FactoriesPlugin.getRegistryManager()
                        .getRegistry(GeneratorRegistry.class)
                        .get("stone_generator")
                        .get()
                        .getGeneratorPlaceItem()),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));
        Blueprint energyGenerator = new Blueprint("energy_generator",
                "#bfbfbfEnergy Generator",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ItemStackProgressable(new ItemStack(Material.DIAMOND, 1)),
                        new ResourceProgressable(20.0, "wood"),
                        new ResourceProgressable(20.0, "stone")),
                new GiveItemAction(FactoriesPlugin.getRegistryManager()
                        .getRegistry(GeneratorRegistry.class)
                        .get("energy_generator")
                        .get()
                        .getGeneratorPlaceItem()),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));

        register(oakWoodGenerator.getId(), oakWoodGenerator);
        register(stoneGenerator.getId(), stoneGenerator);
        register(energyGenerator.getId(), energyGenerator);
    }

    //#region Lazy Initialization
    public static BlueprintRegistry getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final BlueprintRegistry instance = new BlueprintRegistry();
    }
    //#endregion

}
