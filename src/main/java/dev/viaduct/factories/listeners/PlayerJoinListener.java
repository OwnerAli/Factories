package dev.viaduct.factories.listeners;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.CustomItemRegistry;
import dev.viaduct.factories.registries.impl.SupplyDropRegistry;
import dev.viaduct.factories.supply_drops.SupplyDrop;
import dev.viaduct.factories.supply_drops.properties.Properties;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        super();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new FactoryPlayer(event.getPlayer()).register();
        CustomItemRegistry.getInstance().initialize(event.getPlayer());
        SupplyDrop supplyDrop = new SupplyDrop("test", Properties.defaultProperties(),
                new ItemStack[]{new ItemBuilder(Material.OAK_HANGING_SIGN).glowing().build()});
        SupplyDropRegistry.getInstance()
                .register(supplyDrop.getId(), supplyDrop);
        supplyDrop.scheduleSpawn(event.getPlayer().getLocation(), 5);
    }

}