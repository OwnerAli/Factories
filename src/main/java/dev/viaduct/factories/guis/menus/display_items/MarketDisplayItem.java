package dev.viaduct.factories.guis.menus.display_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.conditions.AbstractCondition;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.guis.categories.Categorizable;
import dev.viaduct.factories.guis.categories.Category;
import dev.viaduct.factories.guis.menus.gui_items.MarketGuiItem;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class MarketDisplayItem extends ItemBuilder implements Categorizable {

    private final String name;
    private final ItemStack itemToSell;
    private final Category category;
    private final ConditionHolder conditionHolder;
    private final ActionHolder actionHolder;

    public MarketDisplayItem(String name, ItemStack itemToSell, Category category, List<Action> actions, AbstractCondition... conditions) {
        super(itemToSell);
        this.name = name;
        this.itemToSell = itemToSell;
        this.category = category;
        this.conditionHolder = new ConditionHolder(conditions);
        this.actionHolder = new ActionHolder(actions);

        addLoreLines(" ", "&f&lCOST");
        conditionHolder.getConditionStrings()
                .forEach(message -> addLoreLine("&7 • " + message));
    }

    public GuiItem asGuiItem() {
        return new MarketGuiItem(this);
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