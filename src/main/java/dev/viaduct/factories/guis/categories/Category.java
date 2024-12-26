package dev.viaduct.factories.guis.categories;

import org.bukkit.Material;

public record Category(String id, String displayName, Material displayMaterial,
                       int listPriority) implements Comparable<Category> {

    public Category(String id, String displayName, Material displayMaterial, int listPriority) {
        this.id = id;
        this.displayName = displayName;
        this.displayMaterial = displayMaterial;
        this.listPriority = listPriority;
    }

    @Override
    public int compareTo(Category o) {
        return this.listPriority - o.listPriority;
    }

}