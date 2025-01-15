package dev.viaduct.factories.items.beam_items;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.items.CustomItem;
import dev.viaduct.factories.items.beam_items.beam_item_mods.AbstractModifierItem;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import dev.viaduct.factories.items.beam_items.data.BeamConfigPersistentData;
import dev.viaduct.factories.items.beam_items.projectiles.BeamProjectile;
import dev.viaduct.factories.items.meta.impl.InventoryClickActionableMeta;
import dev.viaduct.factories.items.meta.impl.PlayerInteractActionableMeta;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Optional;

public abstract class AbstractBeamItem extends CustomItem {

    public static final NamespacedKey BEAM_CONFIG_KEY = new NamespacedKey(FactoriesPlugin.getInstance(), "beam_config");

    private final BeamConfiguration config;

    protected AbstractBeamItem(String id, BeamConfiguration config) {
        super(id, new PlayerInteractActionableMeta(
                        AbstractBeamItem::handleBeamFiring,
                        Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK),
                new InventoryClickActionableMeta(click -> {
                    if (click.getCursor() == null) return;
                    if (click.getCursor().getType() == Material.AIR) return;
                    if (!click.getCursor().hasItemMeta()) return;
                    CustomItem.getCustomItem(click.getCursor()).ifPresent(customItem -> {
                        if (!(customItem instanceof AbstractModifierItem abstractModifierItem)) return;
                        click.setCancelled(true);

                        click.getCursor().setAmount(click.getCursor().getAmount() - 1);

                        // Get the config from the clicked item
                        ItemMeta itemMeta = click.getCurrentItem().getItemMeta();
                        BeamConfiguration beamConfiguration = itemMeta.getPersistentDataContainer()
                                .get(BEAM_CONFIG_KEY, new BeamConfigPersistentData());

                        abstractModifierItem.modifyBeamConfiguration(beamConfiguration, (Player) click.getWhoClicked());
                        itemMeta.getPersistentDataContainer().set(BEAM_CONFIG_KEY, new BeamConfigPersistentData(), beamConfiguration);
                        click.getCurrentItem().setItemMeta(itemMeta);
                    });
                }));
        this.config = config;
    }

    private static void handleBeamFiring(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item == null) return;
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null) return;
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        BeamConfiguration beamConfiguration = persistentDataContainer.get(BEAM_CONFIG_KEY, new BeamConfigPersistentData());

        if (beamConfiguration == null) return;
        new BeamProjectile(beamConfiguration).fire(event.getPlayer());
    }

    @Override
    public Optional<ItemStack> makeCustomItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return Optional.empty();

        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(BEAM_CONFIG_KEY, new BeamConfigPersistentData(), config);

        itemStack.setItemMeta(itemMeta);

        return super.makeCustomItem(itemStack);
    }

}
