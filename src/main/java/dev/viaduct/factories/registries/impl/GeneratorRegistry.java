package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.generators.Generator;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.EnergyGenerator;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.OakWoodGenerator;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.OakWoodGeneratorT2;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.StoneGenerator;
import dev.viaduct.factories.registries.Registry;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class GeneratorRegistry extends Registry<String, Generator> {

    public static final NamespacedKey GENERATOR_ID_KEY = new NamespacedKey(FactoriesPlugin.getInstance(), "generator_id");

    public void initialize() {
        OakWoodGenerator oakWoodGenerator = new OakWoodGenerator("oak_wood_generator");
        OakWoodGeneratorT2 oakWoodGeneratorT2 = new OakWoodGeneratorT2("oak_wood_generator_t2");
        StoneGenerator stoneGenerator = new StoneGenerator("stone_generator");
        EnergyGenerator energyGenerator = new EnergyGenerator("energy_generator");

        register(oakWoodGenerator.getId(), oakWoodGenerator);
        register(oakWoodGeneratorT2.getId(), oakWoodGeneratorT2);
        register(stoneGenerator.getId(), stoneGenerator);
        register(energyGenerator.getId(), energyGenerator);
    }

    public Optional<Generator> getGeneratorFromPlacedItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) return Optional.empty();
        if (itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) return Optional.empty();
        return get(itemStack.getItemMeta().getPersistentDataContainer().get(GENERATOR_ID_KEY,
                PersistentDataType.STRING));
    }

    //#region Lazy Initialization
    public static GeneratorRegistry getInstance() {
        return GeneratorRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final GeneratorRegistry instance = new GeneratorRegistry();
    }
    //#endregion

}