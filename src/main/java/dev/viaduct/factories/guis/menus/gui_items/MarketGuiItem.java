package dev.viaduct.factories.guis.menus.gui_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.menus.display_items.MarketDisplayItem;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MarketGuiItem extends GuiItem {

    public MarketGuiItem(MarketDisplayItem displayItem) {
        super(displayItem.build(),
                click -> {
                    // Stop player from taking item out of the GUI
                    click.setCancelled(true);

                    if (!(click.getWhoClicked() instanceof Player player)) return;
                    Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance().get(player);
                    factoryPlayerOptional.ifPresent(factoryPlayer -> {
                        ConditionHolder conditionHolder = displayItem.getConditionHolder();

                        if (!conditionHolder.allConditionsMet(factoryPlayer)) return;
                        conditionHolder.executeActions(factoryPlayer);
                        displayItem.getActionHolder().executeAllActions(factoryPlayer);
                    });
                });
    }

}