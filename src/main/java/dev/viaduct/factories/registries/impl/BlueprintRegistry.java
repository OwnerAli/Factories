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
        GeneratorRegistry generatorRegistry = FactoriesPlugin.getRegistryManager()
                .getRegistry(GeneratorRegistry.class);

        registerWoodGens(generatorRegistry);
        registerStoneGens(generatorRegistry);
    }

    private void registerWoodGens(GeneratorRegistry generatorRegistry) {
        ItemStack oakWoodGeneratorPlaceItem = generatorRegistry
                .get("wood_generator_t1")
                .orElseThrow()
                .getGeneratorPlaceItem();
        ItemStack woodGeneratorT2PlaceItem = generatorRegistry
                .get("wood_generator_t2")
                .orElseThrow()
                .getGeneratorPlaceItem();

        Blueprint woodGenT1 = new Blueprint("wood_generator_t1",
                "#a8996fWood Generator T1",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ResourceProgressable(3.0, "wood")),
                new GiveItemAction(oakWoodGeneratorPlaceItem),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));

        Blueprint woodGenT2 = new Blueprint("wood_generator_t2",
                "#a8996fWood Generator T2",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ItemStackProgressable(oakWoodGeneratorPlaceItem),
                        new ResourceProgressable(20.0, "wood")),
                new GiveItemAction(woodGeneratorT2PlaceItem),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));

        register(woodGenT1.getId(), woodGenT1);
        register(woodGenT2.getId(), woodGenT2);
    }

    private void registerStoneGens(GeneratorRegistry generatorRegistry) {
        ItemStack stoneGeneratorT1PlaceItem = generatorRegistry
                .get("stone_generator_t1")
                .orElseThrow()
                .getGeneratorPlaceItem();
        ItemStack stoneGeneratorT2PlaceItem = generatorRegistry
                .get("stone_generator_t2")
                .orElseThrow()
                .getGeneratorPlaceItem();

        Blueprint stoneGenT1 = new Blueprint("stone_generator_t1",
                "#bfbfbfStone Generator T1",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ResourceProgressable(50.0, "wood"),
                        new ResourceProgressable(10.0, "stone")),
                new GiveItemAction(stoneGeneratorT1PlaceItem),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));

        Blueprint stoneGenT2 = new Blueprint("stone_generator_t2", "#bfbfbfStone Generator T2",
                List.of(new ItemStack(Material.DIAMOND, 1),
                        new ItemStack(Material.IRON_INGOT, 2)),
                List.of(),
                List.of(new ItemStackProgressable(new ItemStack(stoneGeneratorT1PlaceItem)),
                        new ResourceProgressable(65, "wood"),
                        new ResourceProgressable(20, "stone")),
                new GiveItemAction(stoneGeneratorT2PlaceItem),
                new PlaySoundAction(Sound.ENTITY_PLAYER_LEVELUP),
                new PlaySoundAction(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE));

        register(stoneGenT1.getId(), stoneGenT1);
        register(stoneGenT2.getId(), stoneGenT2);
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
