package dev.viaduct.factories.rules.impl;

import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.rules.Rule;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class DenyBlockPlaceRule implements Rule {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        FactoryPlayerRegistry.getInstance()
                .get(event.getPlayer())
                .ifPresent(factoryPlayer -> {
                    if (ignoreRule(factoryPlayer)) return;
                    if (!factoryPlayer.getTaskHolder().doingTask()) return;
                    if (!factoryPlayer.getTaskHolder().getTask()
                            .getRulesSet()
                            .contains(this)) return;
                    event.setCancelled(true);
                    Chat.tell(factoryPlayer.getPlayer(), getDescription());
                });
    }

    @Override
    public String getDescription() {
        return "&cYou are not allowed to place blocks!";
    }

}