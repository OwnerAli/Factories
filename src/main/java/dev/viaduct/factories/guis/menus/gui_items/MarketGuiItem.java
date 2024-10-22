package dev.viaduct.factories.guis.menus.gui_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;

public class MarketGuiItem extends GuiItem {

    public MarketGuiItem(MarketDisplayItem displayItem) {
        super(displayItem.build());
    }
}
