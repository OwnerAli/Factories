package dev.viaduct.factories.items;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.items.meta.CustomItemMeta;
import dev.viaduct.factories.registries.impl.CustomItemRegistry;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class CustomItem {

    private static final NamespacedKey CUSTOM_ITEM_ID_KEY = new NamespacedKey(FactoriesPlugin.getInstance(), "custom_item_id");

    private final String id;
    private final Set<CustomItemMeta> itemMetaSet;

    public CustomItem(String id) {
        this.id = id;
        this.itemMetaSet = new HashSet<>();
    }

    public CustomItem(String id, CustomItemMeta... itemMetaSet) {
        this.id = id;
        this.itemMetaSet = Set.of(itemMetaSet);
    }

    public void addMeta(CustomItemMeta itemMeta) {
        this.itemMetaSet.add(itemMeta);
    }

    public void applyMeta(Object... context) {
        for (CustomItemMeta itemMeta : itemMetaSet) {
            Arrays.stream(context).forEach(itemMeta::apply);
        }
    }

    public Optional<ItemStack> makeCustomItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) return Optional.empty();

        // Get the item meta
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Get the persistent data container from the item meta
        PersistentDataContainer persistentDataContainer =
                itemMeta.getPersistentDataContainer();

        // Set the custom item id in the persistent data container
        persistentDataContainer.set(CUSTOM_ITEM_ID_KEY, PersistentDataType.STRING, id);

        // Set the item meta back to the item stack
        itemStack.setItemMeta(itemMeta);

        return Optional.of(itemStack);
    }

    public static Optional<CustomItem> getCustomItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) return Optional.empty();

        // Get the item meta
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Get the persistent data container from the item meta
        PersistentDataContainer persistentDataContainer =
                itemMeta.getPersistentDataContainer();

        // Get the custom item id from the persistent data container
        String customItemId = persistentDataContainer.get(CUSTOM_ITEM_ID_KEY, PersistentDataType.STRING);

        // Get the custom item from the custom item registry

        return CustomItemRegistry.getInstance().get(customItemId);
    }

}
