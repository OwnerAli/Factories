package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import org.bukkit.inventory.ItemStack;

public class GiveItemAction implements Action {

    private final ItemStack itemStack;

    public GiveItemAction(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        factoryPlayer.getPlayer().getInventory()
                .addItem(itemStack);
    }

}
