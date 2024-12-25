package dev.viaduct.factories.listeners;

import dev.viaduct.factories.items.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemListeners implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        CustomItem.getCustomItem(event.getCurrentItem())
                .ifPresent(customItem -> customItem.applyMeta(event));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        CustomItem.getCustomItem(event.getItem())
                .ifPresent(customItem -> customItem.applyMeta(event));
    }

}
