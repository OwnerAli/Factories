package dev.viaduct.factories.guis.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.menus.panes.TopAndBottomSixPane;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Market {

    public void Market() {}

    public void showToPlayer(FactoryPlayer factoryPlayer) {
        ChestGui gui = new ChestGui(6, Chat.colorize("&b&lMarket"));
        gui.setOnTopClick(event -> event.setCancelled(true));

        TopAndBottomSixPane topAndBottomSixPane = new TopAndBottomSixPane(Material.BLUE_STAINED_GLASS_PANE);

        PaginatedPane paginatedPane = new PaginatedPane(0, 1, 9, 4);
        paginatedPane.setOnClick(event -> factoryPlayer.getPlayer().closeInventory());

        List<GuiItem> upgradeGuiItems = getGuiItems(factoryPlayer);

        paginatedPane.populateWithGuiItems(upgradeGuiItems);

        gui.addPane(topAndBottomSixPane);
        gui.addPane(paginatedPane);
        gui.show(factoryPlayer.getPlayer());
    }

    //  TODO: Replace placeholder with actual available items, in a generalized manner.
    public @NotNull List<GuiItem> getGuiItems(FactoryPlayer factoryPlayer) {
        List<GuiItem> marketItems = new ArrayList<>();
        GuiItem placeholder = new GuiItem(new ItemStack(Material.ACACIA_BOAT));
        marketItems.add(placeholder);
        return marketItems;
    }
}
