package dev.viaduct.factories.guis.menus.display_items;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public abstract class MarketItem  {

    private final double price;
    private final Material icon;

    //  TODO: Discuss the potential kinds of items to be found in a marketplace:
    //  Ex. Effects? Blueprints? Resources?
    public MarketItem(Material icon, double price) {
        this.icon = icon;
        this.price = price;
    }
}
