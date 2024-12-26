package dev.viaduct.factories.guis.menus.display_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.markets.Market;
import dev.viaduct.factories.utils.ItemBuilder;

import java.util.List;

public class CategoryDisplayItem extends GuiItem {

    public CategoryDisplayItem(Category category, boolean current, FactoryPlayer factoryPlayer, Market menu) {
        super(new ItemBuilder(category.displayMaterial())
                        .setName(category.displayName())
                        .glowing()
                        .addLoreLines(current ? List.of("&7Click to deselect this category.") :
                                List.of("&fClick to select this category."))
                        .build(),
                click -> {
                    if (current) {
                        menu.showToPlayer(factoryPlayer);
                        return;
                    }
                    menu.showToPlayer(factoryPlayer, category);
                });
    }

}
