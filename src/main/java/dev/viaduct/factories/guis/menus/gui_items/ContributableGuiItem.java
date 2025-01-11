package dev.viaduct.factories.guis.menus.gui_items;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import dev.viaduct.factories.contributions.Contributable;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.WCSContributionEvent;
import dev.viaduct.factories.markets.Market;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Sound;

public class ContributableGuiItem extends GuiItem {

    public ContributableGuiItem(FactoryPlayer factoryPlayer, Contributable contributable, double amountOfResource, Market market) {
        super(new ItemBuilder(contributable.getDisplayMaterial())
                        .setName(contributable.getDisplayName().replace(": ", " ") + " &7(%.1fx WCS/Resource)".formatted(contributable.getContribution()))
                        .addLoreLines("",
                                "&e&nCLICK &f→ Contribute &e1 &7(%.1fx WCS)".formatted(contributable.getContribution()),
                                "&e&nSHIFT-CLICK &f→ Contribute &e%s &7(%.1fx WCS)".formatted(amountOfResource, contributable.getContribution() * amountOfResource))
                        .build(),
                click -> {
                    click.setCancelled(true);
                    factoryPlayer.getPlayer().playSound(factoryPlayer.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 10);
                    if (click.isShiftClick()) {
                        contributable.contribute(factoryPlayer, amountOfResource);
                        new WCSContributionEvent(factoryPlayer, amountOfResource).call();
                    } else {
                        contributable.contribute(factoryPlayer, 1);
                        new WCSContributionEvent(factoryPlayer, 1).call();
                    }
                    market.showToPlayer(factoryPlayer);
                });
    }

}
