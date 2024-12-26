package dev.viaduct.factories.guis.menus.gui_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class BackGuiItem extends GuiItem {

    public BackGuiItem(String buttonText, Consumer<InventoryClickEvent> action) {
        super(new ItemBuilder(Material.ARROW)
                .setName("&e" + buttonText)
                .build(), action);
    }

}
