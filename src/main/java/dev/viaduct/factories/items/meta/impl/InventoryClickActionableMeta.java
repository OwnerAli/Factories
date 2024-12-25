package dev.viaduct.factories.items.meta.impl;

import dev.viaduct.factories.items.meta.ActionableMeta;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class InventoryClickActionableMeta implements ActionableMeta {

    private final Consumer<InventoryClickEvent> eventConsumer;

    public InventoryClickActionableMeta(Consumer<InventoryClickEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public boolean apply(Object context) {
        if (!(context instanceof InventoryClickEvent inventoryClickEvent)) return false;
        eventConsumer.accept(inventoryClickEvent);

        return true;
    }

    @Override
    public void modifyItem(ItemStack itemStack) {

    }
}
