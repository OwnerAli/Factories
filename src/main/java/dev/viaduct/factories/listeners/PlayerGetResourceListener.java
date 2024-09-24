package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.banks.impl.ResourceBank;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerGetResourceListener implements Listener {

    private final FactoryPlayerRegistry factoryPlayerRegistry;

    public PlayerGetResourceListener(FactoryPlayerRegistry factoryPlayerRegistry) {
        this.factoryPlayerRegistry = factoryPlayerRegistry;
    }

    @EventHandler
    public void onResourceBreak(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();
        //  Get the material from broken block.
        Material material = brokenBlock.getType();

        //  Check to see if the event player's resource bank already contains an
        //  amount of the resource material. If so, increment the amount.
        factoryPlayerRegistry.get(event.getPlayer().getUniqueId())
                .ifPresent(factoryPlayer -> {
                    ResourceBank factoryPlayerBank = factoryPlayer.getResourceBank();
                    factoryPlayerBank.getResourceByMaterial(material)
                            .ifPresent(resource -> {
                                resource.getMaterialAmountPairsList()
                                        .stream()
                                        .filter(pair -> pair.material().equals(material))
                                        .forEach(pair -> factoryPlayerBank.addToResource(resource,
                                                factoryPlayer.getScoreboard(), pair.amount()));
                            });
                });
    }

}
