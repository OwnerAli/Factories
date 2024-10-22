package dev.viaduct.factories.guis.menus.display_items;

import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@Getter
public class MarketDisplayItem extends ItemBuilder {
    private final String name;
    private final double price;
    private final Material icon;

    public MarketDisplayItem(String name, double price, Material icon, List<String> description) {
        super(icon);
        this.name = name;
        this.price = price;
        this.icon = icon;

        this.setLore(description);

        addLoreLines("", "Price: " + price);
    }
}
