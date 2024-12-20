package dev.viaduct.factories.items.meta.impl;

import dev.viaduct.factories.items.meta.CustomItemMeta;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.function.Consumer;

public class PlayerInteractMeta implements CustomItemMeta {

    private final List<Action> actionList;
    private final Consumer<PlayerInteractEvent> eventConsumer;

    public PlayerInteractMeta(Consumer<PlayerInteractEvent> eventConsumer, Action... actions) {
        this.actionList = List.of(actions);
        this.eventConsumer = eventConsumer;
    }

    @Override
    public boolean apply(Object context) {
        if (!(context instanceof PlayerInteractEvent playerInteractEvent)) return false;
        if (!actionList.contains(playerInteractEvent.getAction())) return false;
        eventConsumer.accept(playerInteractEvent);
        return true;
    }

}
