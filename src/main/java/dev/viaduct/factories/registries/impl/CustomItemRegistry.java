package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.guis.menus.PlayerMainMenu;
import dev.viaduct.factories.items.CustomItem;
import dev.viaduct.factories.items.meta.impl.InventoryClickActionableMeta;
import dev.viaduct.factories.items.meta.impl.PlayerInteractActionableMeta;
import dev.viaduct.factories.registries.Registry;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CustomItemRegistry extends Registry<String, CustomItem> {

    public void initialize(Player player) {
        // Menu Custom Item
        CustomItem menuItem = new CustomItem("menu",
                new InventoryClickActionableMeta(inventoryClickEvent -> {
                    inventoryClickEvent.setCancelled(true);
                    new PlayerMainMenu().showToPlayer((Player) inventoryClickEvent.getWhoClicked());
                }),
                new PlayerInteractActionableMeta(playerInteractEvent ->
                        new PlayerMainMenu().showToPlayer(playerInteractEvent.getPlayer()),
                        Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK));

        // Add the custom item to the registry
        register(menuItem.getId(), menuItem);

        Optional<ItemStack> itemStack = menuItem.makeCustomItem(new ItemBuilder(Material.NETHER_STAR)
                .setName("&eMain Menu (Right Click)")
                .build());

        if (itemStack.isEmpty()) return;
        player.getInventory().setItem(8, itemStack.get());
    }

    // Lazy Initialization
    public static CustomItemRegistry getInstance() {
        return CustomItemRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final CustomItemRegistry instance = new CustomItemRegistry();
    }

}
