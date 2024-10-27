package dev.viaduct.factories.listeners;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.blueprints.BlueprintManager;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class BlueprintListeners implements Listener {

    private final BlueprintRegistry blueprintRegistry;
    private final BlueprintManager blueprintManager;

    public BlueprintListeners(BlueprintRegistry blueprintRegistry, BlueprintManager blueprintManager) {
        this.blueprintRegistry = blueprintRegistry;
        this.blueprintManager = blueprintManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK &&
                event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (!event.getItem().hasItemMeta()) return;
        if (event.getItem().getItemMeta().getPersistentDataContainer().isEmpty()) return;

        String blueprintId = event.getItem().getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(FactoriesPlugin.getInstance(), "blueprint"),
                        PersistentDataType.STRING);

        if (blueprintId != null) {
            blueprintRegistry.get(blueprintId)
                    .ifPresent(bp -> bp.getOnInteract().accept(event));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().isEmpty()) return;

        Integer blueprintProgressIndex = event.getCurrentItem().getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(FactoriesPlugin.getInstance(), "blueprint_progress"),
                        PersistentDataType.INTEGER);

        if (blueprintProgressIndex == null) return;

        blueprintManager.getBlueprintProgress(blueprintProgressIndex)
                .ifPresent(blueprintProgress -> {
                    if (event.getCursor() == null || event.getCursor().getType()
                            == Material.AIR &&
                            !blueprintProgress.passesAllConditions((Player) event.getWhoClicked())) return;
                    event.setCancelled(true);
                    blueprintProgress.addProgress(event);
                });
    }

}
