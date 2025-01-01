package dev.viaduct.factories.guis.menus.display_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.conditions.AbstractCondition;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.guis.categories.Categorizable;
import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import dev.viaduct.factories.guis.rows.Row;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

@Getter
public class MarketDisplayItem extends ItemBuilder implements Categorizable {

    private final String name;
    private final ItemStack itemToSell;
    private final Category category;
    private final Row row;
    private final ConditionHolder conditionHolder;
    private final ActionHolder actionHolder;


    public MarketDisplayItem(String name, ItemStack itemToSell, Category category, List<Action> actions, AbstractCondition... conditions) {
        super(itemToSell);
        this.name = name;
        this.itemToSell = itemToSell;
        this.category = category;
        this.row = Row.FIRST;
        this.conditionHolder = new ConditionHolder(conditions);
        this.actionHolder = new ActionHolder(actions);

        addLoreLines(" ", "&f&lCOST");
        conditionHolder.getConditionStrings()
                .forEach(message -> addLoreLine("&7 • " + message));
    }

    public MarketDisplayItem(String name, ItemStack itemToSell, Category category, Row row, List<Action> actions, AbstractCondition... conditions) {
        super(itemToSell);
        this.name = name;
        this.itemToSell = itemToSell;
        this.category = category;
        this.row = row;
        this.conditionHolder = new ConditionHolder(conditions);
        this.actionHolder = new ActionHolder(actions);

        addLoreLines(" ", "&f&lCOST");
        conditionHolder.getConditionStrings()
                .forEach(message -> addLoreLine("&7 • " + message));
    }

    public MarketDisplayItem(String name, ItemStack itemToSell, List<Action> actions, AbstractCondition... conditions) {
        super(itemToSell);
        this.name = name;
        this.itemToSell = itemToSell;
        this.category = Category.defaultCategory();
        this.row = Row.FIRST;
        this.conditionHolder = new ConditionHolder(conditions);
        this.actionHolder = new ActionHolder(actions);

        addLoreLines(" ", "&f&lCOST");
        conditionHolder.getConditionStrings()
                .forEach(message -> addLoreLine("&7 • " + message));
    }

    public GuiItem asGuiItem() {
        return new MarketGuiItem(this);
    }

    public GuiItem asGuiItem(Consumer<InventoryClickEvent> clickAction) {
        return new MarketGuiItem(this, clickAction);
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public boolean inCategory(Category category) {
        return this.category.equals(category);
    }

}