package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GiveItemAction implements Action, RewardAction {

    private final ItemStack itemStack;

    public GiveItemAction(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        Player player = factoryPlayer.getPlayer();
        PlayerInventory inventory = player
                .getInventory();

        if (inventory.firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), itemStack);
            Chat.tell(player, "&cAttempted to give you an item, but your inventory is full." +
                    "\n&7* Dropping item on the ground. *");
            return;
        }
        inventory.addItem(itemStack);
    }

    @Override
    public String getRewardMessage() {
        return Chat.colorizeHex(itemStack.getAmount() + "x " + (itemStack.hasItemMeta() ? itemStack.getItemMeta().getDisplayName() :
                itemStack.getType().name()));
    }
}
