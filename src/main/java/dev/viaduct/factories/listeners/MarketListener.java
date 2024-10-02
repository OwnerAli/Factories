package dev.viaduct.factories.listeners;

import dev.viaduct.factories.guis.markets.Market;
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

    //  Currently just for testing.
    //  Actual mechanic for Market viewing TBD.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId()).ifPresent(market::showToPlayer);
    }
}
