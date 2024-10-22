package dev.viaduct.factories.guis.menus.markets;

import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class MarketItemRegistry {

    private static MarketItemRegistry instance;
    private Map<String, MarketGuiItem> itemRegistry;

    private MarketItemRegistry() {
        itemRegistry = new HashMap<>();
        //  Items defined here.
        itemRegistry.put("Bundle O' Sticks",
                new MarketGuiItem(new MarketDisplayItem("Bundle O' Sticks",10.00, Material.STICK, new ArrayList<>())));
    }

    public static MarketItemRegistry getInstance() {
        if (instance == null) {
            synchronized (MarketItemRegistry.class) {
                if (instance == null) {
                    instance = new MarketItemRegistry();
                }
            }
        }
        return instance;
    }
}
