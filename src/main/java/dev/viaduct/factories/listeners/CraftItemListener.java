package dev.viaduct.factories.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftItemListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        event.setResult(Event.Result.DENY);
    }

}