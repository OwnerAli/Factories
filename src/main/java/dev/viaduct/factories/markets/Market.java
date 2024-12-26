package dev.viaduct.factories.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import dev.viaduct.factories.contributions.Contributable;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.guis.menus.PlayerMainMenu;
import dev.viaduct.factories.guis.menus.display_items.CategoryDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.ContributableGuiItem;
import dev.viaduct.factories.guis.menus.panes.OutlineSixRowPane;
import dev.viaduct.factories.guis.menus.panes.TopAndBottomSixPane;
import dev.viaduct.factories.registries.impl.CategoryRegistry;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Market implements Listener {

    private final MarketStockFactory marketStockFactory;

    public Market() {
        marketStockFactory = MarketStockFactory.getInstance();
    }

    public void showToPlayer(FactoryPlayer factoryPlayer) {
        ChestGui marketGui = new ChestGui(6, Chat.colorize("   - Sun Industries Market -"));
        marketGui.setOnTopClick(event -> event.setCancelled(true));

        TopAndBottomSixPane topAndBottomSixPane = new TopAndBottomSixPane(Material.GREEN_STAINED_GLASS_PANE);
        topAndBottomSixPane.addItem(new GuiItem(new ItemBuilder(Material.BEACON)
                .setName("&2&lContribute")
                .addLoreLines("Contribute to the war effort", "to increase WCS (War Contribution Score).", " ",
                        "&7Increase your WCS to unlock", "&7to show us your dedication.", "",
                        "Earning higher WCS will", "unlock new items in the market!")
                .glowing()
                .build()), 0, 0);
        topAndBottomSixPane.addItem(new GuiItem(new ItemBuilder(Material.ARROW)
                .setName("&eBack")
                .addLoreLines("&7Back to the main menu")
                .build(), event -> new PlayerMainMenu().showToPlayer(factoryPlayer)), 4, 5);

        PaginatedPane paginatedPane = new PaginatedPane(0, 1, 5, 4);

        List<GuiItem> contributableItems = new ArrayList<>();
        getContributableResources(factoryPlayer)
                .forEach((contributable, amount) -> contributableItems.add(new ContributableGuiItem(factoryPlayer, contributable, amount, this)));

        if (contributableItems.isEmpty()) {
            paginatedPane.populateWithGuiItems(List.of(new GuiItem(new ItemBuilder(Material.BARRIER)
                    .setName("&cNo resources to contribute")
                    .addLoreLines("&7Gain resources by mining and", "&7contribute them here.")
                    .build())));
        } else {
            paginatedPane.populateWithGuiItems(contributableItems);
        }

        PaginatedPane categoryPane = new PaginatedPane(1, 0, 6, 1);
        List<GuiItem> categoryItems = new ArrayList<>();
        CategoryRegistry.getInstance().getAllValues()
                .forEach(category -> categoryItems.add(new CategoryDisplayItem(category, false, factoryPlayer, this)));
        categoryPane.populateWithGuiItems(categoryItems);

        marketGui.addPane(topAndBottomSixPane);
        marketGui.addPane(categoryPane);
        marketGui.addPane(paginatedPane);
        marketGui.show(factoryPlayer.getPlayer());
    }

    public void showToPlayer(FactoryPlayer factoryPlayer, Category filterCategory) {
        ChestGui chestGui = new ChestGui(6, Chat.colorize("&8Quests"));
        chestGui.setOnTopClick(event -> event.setCancelled(true));

        OutlineSixRowPane outlineSixRowPane = new OutlineSixRowPane(Material.GRAY_STAINED_GLASS_PANE, "Clear filter",
                click -> showToPlayer(factoryPlayer));
        outlineSixRowPane.addItem(new GuiItem(new ItemBuilder(Material.BEACON)
                .setName("&2&lContribute")
                .addLoreLines("Contribute to the war effort", "to increase WCS (War Contribution Score).", " ",
                        "Earning higher WCS will", "unlock new items in the market!")
                .build(), click -> showToPlayer(factoryPlayer)), 0, 0);
        PaginatedPane categoryPane = new PaginatedPane(1, 0, 7, 1);
        PaginatedPane questsPane = new PaginatedPane(0, 1, 9, 5);

        CategoryRegistry categoryRegistry = CategoryRegistry.getInstance();

        List<GuiItem> categoryItems = new ArrayList<>();
        categoryRegistry.getAllValues()
                .forEach(category -> {
                    if (filterCategory.equals(category)) {
                        categoryItems.add(new CategoryDisplayItem(category, true, factoryPlayer, this));
                        return;
                    }
                    categoryItems.add(new CategoryDisplayItem(category, filterCategory.equals(category), factoryPlayer, this));
                });
        categoryPane.populateWithGuiItems(categoryItems);

        List<GuiItem> marketDisplayItems = new ArrayList<>();
        marketStockFactory.getPossibleItems()
                .stream()
                .filter(displayItem -> displayItem.inCategory(filterCategory))
                .forEach(displayItem -> marketDisplayItems.add(displayItem.asGuiItem()));
        questsPane.populateWithGuiItems(marketDisplayItems);

//        PagingButtons categoryPagingButtons = new PagingButtons(Slot.fromIndex(0), 9, categoryPane);
//        categoryPagingButtons.setForwardButton(new GuiItem(new ItemBuilder(Material.ARROW).setName("&fNext").build()));
//        categoryPagingButtons.setBackwardButton(new GuiItem(new ItemBuilder(Material.ARROW).setName("&fPrevious").build()));
//
//        PagingButtons questsPagingButtons = new PagingButtons(Slot.fromIndex(45), 9, questsPane);
//        questsPagingButtons.setForwardButton(new GuiItem(new ItemBuilder(Material.ARROW).setName("&fNext").build()));
//        questsPagingButtons.setBackwardButton(new GuiItem(new ItemBuilder(Material.ARROW).setName("&fPrevious").build()));

        chestGui.addPane(outlineSixRowPane);
        chestGui.addPane(categoryPane);
        chestGui.addPane(questsPane);
//        chestGui.addPane(categoryPagingButtons);
//        chestGui.addPane(questsPagingButtons);
        chestGui.show(factoryPlayer.getPlayer());
    }

    //  TODO: Create logic for adding items to the market.
    private List<GuiItem> getMarketItems() {
        return marketStockFactory.generateMarketStock();
    }

    private Map<Contributable, Double> getContributableResources(FactoryPlayer factoryPlayer) {
        Bank bank = factoryPlayer.getBank();
        return bank
                .getResourceMap()
                .keySet()
                .stream()
                .filter(resource -> resource instanceof Contributable)
                .filter(resource -> bank.getResourceAmt(resource) > 0)
                .collect(HashMap::new, (map, resource) -> map.put((Contributable) resource, bank.getResourceAmt(resource)), Map::putAll);
    }

}