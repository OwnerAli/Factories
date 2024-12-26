package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.registries.Registry;
import org.bukkit.Material;

public class CategoryRegistry extends Registry<String, Category> {

    public void initialize() {
        CategoryRegistry.getInstance().register("blueprints", new Category("blueprints", "&b&lBlueprints",
                Material.MAP, 1));
        CategoryRegistry.getInstance().register("tools", new Category("tools", "&e&lTools",
                Material.NETHERITE_PICKAXE, 0));
    }

    //#region Lazy Initialization
    public static CategoryRegistry getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final CategoryRegistry instance = new CategoryRegistry();
    }
    //#endregion

}
