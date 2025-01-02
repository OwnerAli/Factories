package dev.viaduct.factories.blueprints.progress;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.blueprints.Blueprint;
import dev.viaduct.factories.events.BlueprintCompleteEvent;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class BlueprintProgress {

    private final int id;
    private final String blueprintId;
    private final ProgressableHolder progressableHolder;

    public BlueprintProgress(int id, Blueprint blueprint, List<Progressable> progressableList) {
        this.id = id;
        this.blueprintId = blueprint.getId();
        this.progressableHolder = new ProgressableHolder(this, progressableList);
    }

    public void addProgress(InventoryClickEvent event) {
        progressableHolder.getProgressConsumer().accept(event);
    }

    public boolean passesAllConditions(Player player) {
        return FactoriesPlugin.getRegistryManager()
                .getRegistry(BlueprintRegistry.class)
                .get(blueprintId)
                .map(bp -> bp.getCompletionConditions().allConditionsMet(player))
                .orElse(false);
    }

    public void complete(InventoryClickEvent event, Player player) {
        FactoriesPlugin.getRegistryManager().getRegistry(BlueprintRegistry.class).get(blueprintId)
                .ifPresent(bp -> {
                    FactoriesPlugin.getInstance().getBlueprintManager().removeBlueprintProgress(this);
                    bp.getCompletionActions().executeAllActions(player);
                    event.setCurrentItem(new ItemStack(Material.AIR));
                    Bukkit.getPluginManager().callEvent(new BlueprintCompleteEvent(player, this));
                });
    }

    public ItemStack getProgressItem(World world) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.FILLED_MAP)
                .addLoreLines("&e&oSun Industries Blueprints™", "&7Unlock the future with advanced",
                        "designs that enable your", "factories!")
                .setMapImage(world, "/images/Blueprint.png")
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        FactoriesPlugin.getRegistryManager()
                .getRegistry(BlueprintRegistry.class)
                .get(blueprintId)
                .ifPresent(bp -> {
                    itemBuilder.setName("&f* " + bp.getDisplayName() + " Blueprint &f*");
                    if (!bp.getCompletionConditions().isEmpty()) {
                        itemBuilder.addLoreLines(" ", "&f&lConditions ");
                        bp.getCompletionConditions().getConditionStrings()
                                .forEach(line -> itemBuilder.addLoreLine(" &7 • " + line));
                    }

                    itemBuilder.addLoreLines(" ", "&f&lTo Do ");
                    progressableHolder.getDescription().forEach(itemBuilder::addLoreLine);
                });

        itemBuilder.setPersistentData(new NamespacedKey(FactoriesPlugin.getInstance(), "blueprint_progress"), id);

        return itemBuilder.build();
    }

}