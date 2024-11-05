package dev.viaduct.factories.guis.menus.display_items;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.conditions.AbstractCondition;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class MarketDisplayItem extends ItemBuilder {

    private final String name;
    private final ItemStack itemToSell;
    private final ConditionHolder conditionHolder;
    private final ActionHolder actionHolder;

    public MarketDisplayItem(String name, ItemStack itemToSell, List<Action> actions, AbstractCondition... conditions) {
        super(itemToSell);
        this.name = name;
        this.itemToSell = itemToSell;
        this.conditionHolder = new ConditionHolder(conditions);
        this.actionHolder = new ActionHolder(actions);

        addLoreLines(" ", "&f&lCOST");
        conditionHolder.getConditionStrings()
                        .forEach(message -> addLoreLine("&7 • "  + message));
    }

}
