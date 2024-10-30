package dev.viaduct.factories.guis.menus.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import dev.viaduct.factories.resources.ResourceManager;
import lombok.Getter;
import org.bukkit.Material;

import java.util.*;

@Getter
public class MarketStockFactory {

    private static MarketStockFactory instance;
    private final ResourceManager resourceManager;
    private final List<MarketDisplayItem> possibleItems;

    private MarketStockFactory() {
        resourceManager = FactoriesPlugin.getInstance().getResourceManager();
        //  stock list of potential items.
        possibleItems = new ArrayList<>();
        possibleItems.add(new MarketDisplayItem("Wood",
                10.00, Material.OAK_WOOD, new ArrayList<>()));
        possibleItems.add(new MarketDisplayItem("Stone",
                12.00, Material.STONE, new ArrayList<>()));
    }

    public static MarketStockFactory getInstance() {
        if (instance == null) {
            synchronized (MarketStockFactory.class) {
                if (instance == null) {
                    instance = new MarketStockFactory();
                }
            }
        }
        return instance;
    }

    public List<GuiItem> generateMarketStock() {
        List<GuiItem> marketStock = new ArrayList<>();
        for (MarketDisplayItem possibleItem : possibleItems) {
            MarketGuiItem item = new MarketGuiItem(possibleItem);
            marketStock.add(item);
        }
        return marketStock;
    }
}
