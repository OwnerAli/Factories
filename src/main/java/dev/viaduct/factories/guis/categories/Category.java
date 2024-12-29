package dev.viaduct.factories.guis.categories;

import org.bukkit.Material;

public record Category(String id, String displayName, Material displayMaterial,
                       int listPriority) implements Comparable<Category> {

    public static Category defaultCategory() {
        return new Category("default", "&7&lDefault", Material.BARRIER, 0);
    }

    @Override
    public int compareTo(Category o) {
        return this.listPriority - o.listPriority;
    }

}