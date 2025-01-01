package dev.viaduct.factories.markets;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.conditions.impl.ResourceCondition;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import dev.viaduct.factories.guis.rows.Row;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.registries.impl.CategoryRegistry;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MarketStockFactory {

    private final List<MarketDisplayItem> possibleItems;

    private MarketStockFactory() {
        //  stock list of potential items.
        possibleItems = new ArrayList<>();
        possibleItems.add(new MarketDisplayItem("oak_wood_blueprint", BlueprintRegistry.getInstance()
                .get("oak_wood_generator")
                .get().getRevealItem(),
                CategoryRegistry.getInstance().get("blueprints").orElseThrow(),
                Row.FIRST,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("oak_wood_generator")
                        .get().getRevealItem())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 3, false)));

        possibleItems.add(new MarketDisplayItem("oak_wood_t2_blueprint", BlueprintRegistry.getInstance()
                .get("oak_wood_generator_t2")
                .get().getRevealItem(),
                CategoryRegistry.getInstance().get("blueprints").orElseThrow(),
                        Row.FIRST,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("oak_wood_generator_t2")
                        .get().getRevealItem())),
                new ResourceCondition("wood", 20, true),
                new ResourceCondition("WCS", 50, false)));

        possibleItems.add(new MarketDisplayItem("stone_blueprint", BlueprintRegistry.getInstance()
                .get("stone_generator")
                .get().getRevealItem(),
                CategoryRegistry.getInstance().get("blueprints").orElseThrow(),
                Row.SECOND,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("stone_generator")
                        .get().getRevealItem())),
                new ResourceCondition("wood", 20, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 50, false)));

        // Basic Axe
        MarketDisplayItem basicAxe = new MarketDisplayItem("basic_axe", new ItemBuilder(Material.WOODEN_AXE)
                .setName("#70543EPioneer Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.WOODEN_AXE)
                        .setName("#70543EPioneer Axe")
                        .build())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 50, false));

        // Basic Pickaxe
        MarketDisplayItem basicPickaxe = new MarketDisplayItem("basic_pickaxe", new ItemBuilder(Material.WOODEN_PICKAXE)
                .setName("#70543EPioneer Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.WOODEN_PICKAXE)
                        .setName("#70543EPioneer Pickaxe")
                        .build())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 50, false));

        // Industrial Axe
        MarketDisplayItem industrialAxe = new MarketDisplayItem("industrial_axe", new ItemBuilder(Material.STONE_AXE)
                .setName("#7F8C8DIndustrial Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.STONE_AXE)
                        .setName("#7F8C8DIndustrial Axe")
                        .build())),
                new ResourceCondition("wood", 100, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 500, false));

        // Industrial Pickaxe
        MarketDisplayItem industrialPickaxe = new MarketDisplayItem("industrial_pickaxe", new ItemBuilder(Material.STONE_PICKAXE)
                .setName("#7F8C8DIndustrial Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.STONE_PICKAXE)
                        .setName("#7F8C8DIndustrial Pickaxe")
                        .build())),
                new ResourceCondition("wood", 100, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 500, false));

        // Orbital Axe
        MarketDisplayItem orbitalAxe = new MarketDisplayItem("orbital_axe", new ItemBuilder(Material.IRON_AXE)
                .setName("#F1C40FOrbital Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.IRON_AXE)
                        .setName("#F1C40FOrbital Axe")
                        .build())),
                new ResourceCondition("wood", 1000, true),
                new ResourceCondition("stone", 500, true),
                new ResourceCondition("WCS", 5000, false));

        // Orbital Pickaxe
        MarketDisplayItem orbitalPickaxe = new MarketDisplayItem("orbital_pickaxe", new ItemBuilder(Material.IRON_PICKAXE)
                .setName("#F1C40FOrbital Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.IRON_PICKAXE)
                        .setName("#F1C40FOrbital Pickaxe")
                        .build())),
                new ResourceCondition("wood", 1000, true),
                new ResourceCondition("stone", 500, true),
                new ResourceCondition("WCS", 5000, false));

        possibleItems.add(basicAxe);
        possibleItems.add(basicPickaxe);
        possibleItems.add(industrialAxe);
        possibleItems.add(industrialPickaxe);
        possibleItems.add(orbitalAxe);
        possibleItems.add(orbitalPickaxe);
    }

    public List<GuiItem> generateMarketStock() {
        List<GuiItem> marketStock = new ArrayList<>();
        for (MarketDisplayItem possibleItem : possibleItems) {
            MarketGuiItem item = new MarketGuiItem(possibleItem);
            marketStock.add(item);
        }
        return marketStock;
    }

    //#region Lazy Initialization
    public static MarketStockFactory getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final MarketStockFactory instance = new MarketStockFactory();
    }
    //#endregion

}
