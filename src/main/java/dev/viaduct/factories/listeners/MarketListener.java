package dev.viaduct.factories.listeners;

import dev.viaduct.factories.guis.menus.markets.Market;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MarketListener implements Listener {

    private final FactoryPlayerRegistry factoryPlayerRegistry;
    private final Market market;

    public MarketListener(FactoryPlayerRegistry factoryPlayerRegistry, Market market) {
        this.factoryPlayerRegistry = factoryPlayerRegistry;
        this.market = market;
    }

    //  TODO: Determine how the market should be opened by the player.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        factoryPlayerRegistry.get(event.getPlayer()).ifPresent(market::showToPlayer);
    }
}
