package dev.viaduct.factories.guis.menus.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.menus.panes.TopAndBottomFivePane;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.List;

public class Market implements Listener {

    private final MarketStockFactory marketStockFactory;

    public Market() {
        marketStockFactory = MarketStockFactory.getInstance();
    }

    public void showToPlayer(FactoryPlayer factoryPlayer) {
        ChestGui marketGui = new ChestGui(6, Chat.colorize("&b&l-- Sun Industries Market --"));

        //  TODO: Determine the adequate color and structure for the GUI pane.
        TopAndBottomFivePane topAndBottomFivePane = new TopAndBottomFivePane(Material.RED_STAINED_GLASS_PANE);
        PaginatedPane paginatedPane = new PaginatedPane(0, 1, 5, 4);

        paginatedPane.populateWithGuiItems(getMarketItems());

        marketGui.addPane(topAndBottomFivePane);
        marketGui.addPane(paginatedPane);

        marketGui.show(factoryPlayer.getPlayer());
    }

    //  TODO: Create logic for adding items to the market.
    private List<GuiItem> getMarketItems() {
        return marketStockFactory.generateMarketStock();
    }
}
