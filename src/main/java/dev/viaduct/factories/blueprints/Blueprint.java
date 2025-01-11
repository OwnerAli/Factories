package dev.viaduct.factories.blueprints;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.blueprints.progress.Progressable;
import dev.viaduct.factories.conditions.AbstractCondition;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.events.BlueprintRevealEvent;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class Blueprint {

    private final String id;
    private final String displayName;
    private final List<ItemStack> ingredients;
    private final ConditionHolder completionConditions;
    private final List<Progressable> progressablesList;
    private final ActionHolder completionActions;
    private final Consumer<PlayerInteractEvent> onInteract;

    public Blueprint(String id, String displayName, List<ItemStack> ingredients, List<AbstractCondition> conditions,
                     List<Progressable> progressables, Action... completionActions) {
        this.id = id;
        this.displayName = Chat.colorizeHex(displayName);
        this.ingredients = ingredients;
        this.completionConditions = new ConditionHolder(conditions);
        this.progressablesList = new ArrayList<>(progressables);
        this.completionActions = new ActionHolder(completionActions);

        this.onInteract = interact -> {
            BlueprintManager blueprintManager = FactoriesPlugin.getInstance().getBlueprintManager();
            BlueprintProgress blueprintProgress = blueprintManager.addBlueprintProgress(this);

            Player player = interact.getPlayer();
            Bukkit.getServer().getPluginManager().callEvent(new BlueprintRevealEvent(player, blueprintProgress));

            PlayerInventory inventory = player.getInventory();
            ItemStack itemInMainHand = inventory.getItemInMainHand();

            itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
            inventory.addItem(blueprintProgress.getProgressItem(player.getWorld()));

            player.playSound(player.getLocation(), Sound.ENTITY_BREEZE_HURT,
                    1, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    1, 1);
            Chat.tell(player, "&f&k;; &r&b&lBlueprint Started &f&k;;");
        };
    }

    public ItemStack getRevealItem() {
        return new ItemBuilder(Material.FILLED_MAP)
                .setName("%s Blueprint &7(Right-Click To Start)".formatted(getDisplayName()))
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData(new NamespacedKey(FactoriesPlugin.getInstance(), "blueprint"), id)
                .build();
    }

}
