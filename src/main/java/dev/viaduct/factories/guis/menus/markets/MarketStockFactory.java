package dev.viaduct.factories.guis.menus.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.conditions.impl.ResourceCondition;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import dev.viaduct.factories.resources.ResourceManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MarketStockFactory {

    private final ResourceManager resourceManager;
    private final List<MarketDisplayItem> possibleItems;

    private MarketStockFactory() {
        resourceManager = FactoriesPlugin.getInstance().getResourceManager();
        //  stock list of potential items.
        possibleItems = new ArrayList<>();
        possibleItems.add(new MarketDisplayItem("Bundle O' Sticks", new ItemStack(Material.STICK, 20),
                List.of(new GiveItemAction(new ItemStack(Material.STICK, 20))),
                new ResourceCondition("wood", 10)));
    }

    public List<GuiItem> generateMarketStock() {
        List<GuiItem> marketStock = new ArrayList<>();
        for (MarketDisplayItem possibleItem : possibleItems) {
            MarketGuiItem item = new MarketGuiItem(possibleItem);
            marketStock.add(item);
        }
        return marketStock;
    }

    public static MarketStockFactory getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final MarketStockFactory instance = new MarketStockFactory();
    }

}