package dev.viaduct.factories.guis.menus;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import dev.viaduct.factories.guis.menus.panes.TopAndBottomSixPane;
import dev.viaduct.factories.supply_drops.SupplyDrop;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SupplyDropLootMenu {

    public void showToPlayer(Player player, SupplyDrop supplyDrop) {
        ChestGui gui = new ChestGui(6, "Claiming supply drop: " + supplyDrop.getId());

        TopAndBottomSixPane topAndBottomSixPane = new TopAndBottomSixPane(supplyDrop.getProperties().landingBlockMaterial());
        PaginatedPane paginatedPane = new PaginatedPane(0, 1, 9, 4);
        paginatedPane.populateWithItemStacks(Arrays.asList(supplyDrop.getItems()));

        gui.addPane(topAndBottomSixPane);
        gui.addPane(paginatedPane);
        gui.show(player);
    }

}
