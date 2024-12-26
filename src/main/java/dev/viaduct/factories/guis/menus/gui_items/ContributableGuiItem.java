package dev.viaduct.factories.guis.menus.gui_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.contributions.Contributable;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.markets.Market;
import dev.viaduct.factories.utils.ItemBuilder;

public class ContributableGuiItem extends GuiItem {

    public ContributableGuiItem(FactoryPlayer factoryPlayer, Contributable contributable, double amountOfResource, Market market) {
        super(new ItemBuilder(contributable.getDisplayMaterial())
                        .setName("Contribute " + contributable.getDisplayName())
                        .addLoreLines("",
                                "&fClick → Contributes 1 &7(%.1f WCS)".formatted(contributable.getContribution()),
                                "&fShift-Click → Contributes All &7(%.1f WCS)".formatted(contributable.getContribution() * amountOfResource))
                        .build(),
                click -> {
                    click.setCancelled(true);
                    if (click.isShiftClick()) {
                        contributable.contribute(factoryPlayer, amountOfResource);
                    } else {
                        contributable.contribute(factoryPlayer, 1);
                    }
                    market.showToPlayer(factoryPlayer);
                });
    }

}
