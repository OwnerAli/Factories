package dev.viaduct.factories.items.meta.impl;

import dev.viaduct.factories.items.meta.CustomItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class SlotLockedMeta implements CustomItemMeta {

    private final Consumer<InventoryClickEvent> eventConsumer;

    public SlotLockedMeta(Consumer<InventoryClickEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public boolean apply(Object context) {
        if (!(context instanceof InventoryClickEvent inventoryClickEvent)) return false;
        eventConsumer.accept(inventoryClickEvent);

        return true;
    }

}
