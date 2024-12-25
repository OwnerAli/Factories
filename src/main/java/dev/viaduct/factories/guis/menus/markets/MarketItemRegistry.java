package dev.viaduct.factories.guis.menus.markets;

import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.conditions.impl.ResourceCondition;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MarketItemRegistry {

    private static MarketItemRegistry instance;
    private final Map<String, MarketGuiItem> itemRegistry;

    private MarketItemRegistry() {
        itemRegistry = new HashMap<>();
        //  Items defined here.

        itemRegistry.put("Bundle O' Sticks",
                new MarketGuiItem(new MarketDisplayItem("Bundle O' Sticks", new ItemStack(Material.STICK, 20),
                        List.of(new GiveItemAction(new ItemStack(Material.STICK, 20))),
                        new ResourceCondition("wood", 10, true))));
    }

    public static MarketItemRegistry getInstance() {
        if (instance == null) {
            synchronized (MarketItemRegistry.class) {
                instance = new MarketItemRegistry();
            }
        }
        return instance;
    }

}
