package dev.viaduct.factories.guis.menus;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.menus.panes.TopAndBottomSixPane;
import dev.viaduct.factories.markets.Market;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;

public class PlayerMainMenu {

    public void showToPlayer(FactoryPlayer factoryPlayer) {
        ChestGui gui = new ChestGui(6, "Main Menu");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        TopAndBottomSixPane topAndBottomSixPane = new TopAndBottomSixPane(Material.YELLOW_STAINED_GLASS_PANE);

        topAndBottomSixPane.addItem(new GuiItem(new ItemBuilder(Material.DIAMOND)
                .setName("&eMarketplace")
                .addLoreLines("Sun Industries Marketplace™",
                        "",
                        "Contribute to the war effort by", "exporting goods and buying supplies.",
                        "",
                        "Click to open marketplace.")
                .glowing()
                .build(),
                click -> new Market().showToPlayer(factoryPlayer)), 4, 2);

        topAndBottomSixPane.addItem(new GuiItem(new ItemBuilder(Material.ARROW)
                .setName("&eClose")
                .addLoreLines("&7Close the menu")
                .build(), event -> factoryPlayer.getPlayer().closeInventory()), 4, 5);

        gui.addPane(topAndBottomSixPane);
        gui.show(factoryPlayer.getPlayer());
    }

}