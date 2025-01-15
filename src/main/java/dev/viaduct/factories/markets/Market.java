package dev.viaduct.factories.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import dev.viaduct.factories.contributions.Contributable;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.guis.menus.PlayerMainMenu;
import dev.viaduct.factories.guis.menus.display_items.CategoryDisplayItem;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.BackGuiItem;
import dev.viaduct.factories.guis.menus.gui_items.ContributableGuiItem;
import dev.viaduct.factories.guis.menus.panes.RowBasedPaginatedPane;
import dev.viaduct.factories.guis.rows.Row;
import dev.viaduct.factories.registries.impl.CategoryRegistry;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Market implements Listener {

    private final MarketStockFactory marketStockFactory;

    public Market() {
        marketStockFactory = MarketStockFactory.getInstance();
    }

    public void showToPlayer(FactoryPlayer factoryPlayer) {
        ChestGui marketGui = new ChestGui(6, Chat.colorize("   - Sun Industries Market -"));
        marketGui.setOnTopClick(event -> event.setCancelled(true));

        Pattern pattern = new Pattern(
                "131111111",
                "100000001",
                "100000001",
                "100000001",
                "100000001",
                "111121111"
        );

        // Border / Categories
        PatternPane patternPane = new PatternPane(0, 0, 9, 6, Pane.Priority.LOWEST, pattern);
        patternPane.bindItem('1', new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).build()));
        patternPane.bindItem('2', new BackGuiItem("&eBack", "&7Back to the main menu",
                click -> new PlayerMainMenu().showToPlayer(factoryPlayer)));
        patternPane.bindItem('3', (new GuiItem(new ItemBuilder(Material.BEACON)
                .setName("&2War Contribution Score &f(WCS)")
                .addLoreLines("Higher WCS unlocks new market items!",
                        "",
                        "Click to open contribution menu.")
                .glowing()
                .build(), click -> showToPlayer(factoryPlayer))));

        PaginatedPane contributablePane = new PaginatedPane(2, 2, 5, 2);

        List<GuiItem> contributableItems = new ArrayList<>();
        getContributableResources(factoryPlayer)
                .forEach((contributable, amount) -> contributableItems.add(new ContributableGuiItem(factoryPlayer, contributable, amount, this)));

        if (contributableItems.isEmpty()) {
            contributablePane.populateWithGuiItems(List.of(new GuiItem(new ItemBuilder(Material.BARRIER)
                    .setName("&cNo resources to contribute")
                    .addLoreLines("&7Gain resources by mining and", "&7contribute them here.")
                    .build())));
        } else {
            contributablePane.populateWithGuiItems(contributableItems);
        }

        PaginatedPane categoryPane = new PaginatedPane(3, 0, 5, 1);
        List<GuiItem> categoryItems = new ArrayList<>();
        CategoryRegistry.getInstance().getAllValues()
                .forEach(category -> categoryItems.add(new CategoryDisplayItem(category, false, factoryPlayer, this)));
        categoryPane.populateWithGuiItems(categoryItems);

        marketGui.addPane(patternPane);
        marketGui.addPane(categoryPane);
        marketGui.addPane(contributablePane);
        marketGui.show(factoryPlayer.getPlayer());
    }

    public void showToPlayer(FactoryPlayer factoryPlayer, Category filterCategory) {
        ChestGui chestGui = new ChestGui(6, Chat.colorize("   - Sun Industries Market -"));
        chestGui.setOnTopClick(event -> event.setCancelled(true));

        PatternPane patternPane = getPatternPane(filterCategory);
        patternPane.bindItem('1', new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).build()));
        patternPane.bindItem('2', new BackGuiItem("&eClear Filter", "&7Click to clear", click -> showToPlayer(factoryPlayer)));
        patternPane.bindItem('3', (new GuiItem(new ItemBuilder(Material.BEACON)
                .setName("&2&lContribute")
                .addLoreLines("Contribute to the war effort", "to increase WCS (War Contribution Score).", " ",
                        "Earning higher WCS will", "unlock new items in the market!")
                .build(), click -> showToPlayer(factoryPlayer))));

        PaginatedPane categoryPane = new PaginatedPane(3, 0, 5, 1);
        CategoryRegistry categoryRegistry = CategoryRegistry.getInstance();
        List<GuiItem> categoryItems = new ArrayList<>();
        categoryRegistry.getAllValues()
                .forEach(category -> categoryItems.add(new CategoryDisplayItem(category,
                        filterCategory.equals(category), factoryPlayer, this)));
        categoryPane.populateWithGuiItems(categoryItems);

        // Create new row-based pane
        RowBasedPaginatedPane itemRows = new RowBasedPaginatedPane(2, 1, 6, 4);

        // Create mapping of GuiItems to DisplayItems
        final Map<GuiItem, MarketDisplayItem> guiItemToDisplayItem = new HashMap<>();
        List<GuiItem> marketItems = new ArrayList<>();

        marketStockFactory.getPossibleItems()
                .stream()
                .filter(displayItem -> displayItem.inCategory(filterCategory))
                .forEach(displayItem -> {
                    GuiItem guiItem = displayItem.asGuiItem();
                    guiItemToDisplayItem.put(guiItem, displayItem);
                    marketItems.add(guiItem);
                });

        // Populate the rows using the mapping
        itemRows.populateItemsByRow(marketItems, item -> guiItemToDisplayItem.get(item).getRow().getIndex());

        chestGui.addPane(patternPane);
        chestGui.addPane(categoryPane);
        itemRows.addToGui(chestGui);

        chestGui.show(factoryPlayer.getPlayer());
    }

    private static @NotNull PatternPane getPatternPane(Category filterCategory) {
        Pattern pattern = new Pattern(
                "131111111",
                "100000001",
                "100000001",
                "100000001",
                "100000001",
                "111121111"
        );
        PatternPane patternPane = new PatternPane(0, 0, 9, 6, Pane.Priority.LOWEST, pattern);


        if (filterCategory.id().equalsIgnoreCase("blueprints")) {
            pattern = new Pattern(
                    "131111111",
                    "160000001",
                    "170000001",
                    "180000001",
                    "180000001",
                    "111121111"
            );
            patternPane = new PatternPane(0, 0, 9, 6, Pane.Priority.LOWEST, pattern);
            patternPane.bindItem('6', new GuiItem(new ItemBuilder(Material.IRON_NUGGET)
                    .setName("#a8996fWood Blueprints &f→")
                    .addLoreLines("Blueprints for wood generators.")
                    .glowing()
                    .build()));
            patternPane.bindItem('7', new GuiItem(new ItemBuilder(Material.IRON_NUGGET)
                    .setName("#bfbfbfStone Blueprints &f→")
                    .addLoreLines("Blueprints for stone generators.")
                    .glowing()
                    .build()));
            patternPane.bindItem('8', new GuiItem(new ItemBuilder(Material.IRON_NUGGET)
                    .setName("&cComing Soon")
                    .addLoreLines("Our engineers are hard at work", "developing new blueprints.",
                            "",
                            "Don't forget, you can contribute", "to the war effort to unlock", "new items in the market!")
                    .build()));
        }
        return patternPane;
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