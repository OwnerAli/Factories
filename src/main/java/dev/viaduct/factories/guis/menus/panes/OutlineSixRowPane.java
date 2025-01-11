package dev.viaduct.factories.guis.menus.panes;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.viaduct.factories.guis.menus.gui_items.BackGuiItem;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class OutlineSixRowPane extends StaticPane {

    public OutlineSixRowPane(Material borderMaterial, String buttonText, Consumer<InventoryClickEvent> backAction) {
        super(0, 0, 9, 6, Priority.LOWEST);
        for (int i = 0; i < 9; i++) {
            addItem(new GuiItem(new ItemBuilder(borderMaterial)
                    .setName(" ")
                    .build()), i, 0);
            addItem(new GuiItem(new ItemBuilder(borderMaterial)
                    .setName(" ")
                    .build()), i, 5);
        }
        addItem(new BackGuiItem(buttonText, backAction), 4, 5);
    }

}
