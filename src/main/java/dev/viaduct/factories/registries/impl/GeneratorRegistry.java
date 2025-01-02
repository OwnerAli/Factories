package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.generators.Generator;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.stone.StoneGeneratorT1;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.stone.StoneGeneratorT2;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.wood.WoodGeneratorT1;
import dev.viaduct.factories.generators.manual_generators.block_generators.impl.wood.WoodGeneratorT2;
import dev.viaduct.factories.registries.Registry;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class GeneratorRegistry extends Registry<String, Generator> {

    public static final NamespacedKey GENERATOR_ID_KEY = new NamespacedKey(FactoriesPlugin.getInstance(), "generator_id");

    public void initialize() {
        WoodGeneratorT1 woodGeneratorT1 = new WoodGeneratorT1("wood_generator_t1");
        WoodGeneratorT2 woodGeneratorT2 = new WoodGeneratorT2("wood_generator_t2");
        StoneGeneratorT1 stoneGeneratorT1 = new StoneGeneratorT1("stone_generator_t1");
        StoneGeneratorT2 stoneGeneratorT2 = new StoneGeneratorT2("stone_generator_t2");

        register(woodGeneratorT1.getId(), woodGeneratorT1);
        register(woodGeneratorT2.getId(), woodGeneratorT2);
        register(stoneGeneratorT1.getId(), stoneGeneratorT1);
        register(stoneGeneratorT2.getId(), stoneGeneratorT2);
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