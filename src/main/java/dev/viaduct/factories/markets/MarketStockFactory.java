package dev.viaduct.factories.markets;

import dev.viaduct.factories.actions.impl.GiveItemAction;
import dev.viaduct.factories.conditions.impl.ResourceCondition;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
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
        possibleItems.add(new MarketDisplayItem("wood_gen_t1_bp", BlueprintRegistry.getInstance()
                .get("wood_generator_t1")
                .orElseThrow()
                .getRevealItem(),
                CategoryRegistry.getInstance().get("blueprints").orElseThrow(),
                Row.FIRST,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("wood_generator_t1")
                        .orElseThrow()
                        .getRevealItem())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 3, false)));

        possibleItems.add(new MarketDisplayItem("wood_gen_t2_bp", BlueprintRegistry.getInstance()
                .get("wood_generator_t2")
                .orElseThrow()
                .getRevealItem(),
                CategoryRegistry.getInstance().get("blueprints").orElseThrow(),
                Row.FIRST,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("wood_generator_t2")
                        .orElseThrow()
                        .getRevealItem())),
                new ResourceCondition("wood", 20, true),
                new ResourceCondition("WCS", 15, false)));

        possibleItems.add(new MarketDisplayItem("stone_gen_t1_bp", BlueprintRegistry.getInstance()
                .get("stone_generator_t1")
                .orElseThrow()
                .getRevealItem(),
                CategoryRegistry.getInstance()
                        .get("blueprints")
                        .orElseThrow(),
                Row.SECOND,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("stone_generator_t1")
                        .orElseThrow()
                        .getRevealItem())),
                new ResourceCondition("wood", 45, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 25, false)));

        possibleItems.add(new MarketDisplayItem("stone_gen_t2_bp", BlueprintRegistry.getInstance()
                .get("stone_generator_t2")
                .orElseThrow()
                .getRevealItem(),
                CategoryRegistry.getInstance()
                        .get("blueprints")
                        .orElseThrow(),
                Row.SECOND,
                List.of(new GiveItemAction(BlueprintRegistry.getInstance()
                        .get("stone_generator_t2")
                        .orElseThrow()
                        .getRevealItem())),
                new ResourceCondition("wood", 100, true),
                new ResourceCondition("stone", 100, true),
                new ResourceCondition("WCS", 70, false)));

        // Basic Axe
        MarketDisplayItem basicAxe = new MarketDisplayItem("basic_axe", new ItemBuilder(Material.WOODEN_AXE)
                .setName("#70543EPioneer Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.WOODEN_AXE)
                        .setName("#70543EPioneer Axe")
                        .build())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 10, false));

        // Basic Pickaxe
        MarketDisplayItem basicPickaxe = new MarketDisplayItem("basic_pickaxe", new ItemBuilder(Material.WOODEN_PICKAXE)
                .setName("#70543EPioneer Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.WOODEN_PICKAXE)
                        .setName("#70543EPioneer Pickaxe")
                        .build())),
                new ResourceCondition("wood", 10, true),
                new ResourceCondition("WCS", 35, false));

        // Industrial Axe
        MarketDisplayItem industrialAxe = new MarketDisplayItem("industrial_axe", new ItemBuilder(Material.STONE_AXE)
                .setName("#7F8C8DIndustrial Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.STONE_AXE)
                        .setName("#7F8C8DIndustrial Axe")
                        .build())),
                new ResourceCondition("wood", 100, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 50, false));

        // Industrial Pickaxe
        MarketDisplayItem industrialPickaxe = new MarketDisplayItem("industrial_pickaxe", new ItemBuilder(Material.STONE_PICKAXE)
                .setName("#7F8C8DIndustrial Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.STONE_PICKAXE)
                        .setName("#7F8C8DIndustrial Pickaxe")
                        .build())),
                new ResourceCondition("wood", 100, true),
                new ResourceCondition("stone", 50, true),
                new ResourceCondition("WCS", 65, false));

        // Orbital Axe
        MarketDisplayItem orbitalAxe = new MarketDisplayItem("orbital_axe", new ItemBuilder(Material.IRON_AXE)
                .setName("#F1C40FOrbital Axe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.IRON_AXE)
                        .setName("#F1C40FOrbital Axe")
                        .build())),
                new ResourceCondition("wood", 1000, true),
                new ResourceCondition("stone", 500, true),
                new ResourceCondition("WCS", 120, false));

        // Orbital Pickaxe
        MarketDisplayItem orbitalPickaxe = new MarketDisplayItem("orbital_pickaxe", new ItemBuilder(Material.IRON_PICKAXE)
                .setName("#F1C40FOrbital Pickaxe")
                .build(), CategoryRegistry.getInstance().get("tools").orElseThrow(),
                List.of(new GiveItemAction(new ItemBuilder(Material.IRON_PICKAXE)
                        .setName("#F1C40FOrbital Pickaxe")
                        .build())),
                new ResourceCondition("wood", 1000, true),
                new ResourceCondition("stone", 500, true),
                new ResourceCondition("WCS", 200, false));

        possibleItems.add(basicAxe);
        possibleItems.add(basicPickaxe);
        possibleItems.add(industrialAxe);
        possibleItems.add(industrialPickaxe);
        possibleItems.add(orbitalAxe);
        possibleItems.add(orbitalPickaxe);
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