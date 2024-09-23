package dev.viaduct.factories.blueprints;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.blueprints.progress.Progressable;
import dev.viaduct.factories.conditions.AbstractCondition;
import dev.viaduct.factories.conditions.Condition;
import dev.viaduct.factories.conditions.ConditionHolder;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class Blueprint {

    private final String id;
    private final List<ItemStack> ingredients;
    private final ConditionHolder completionConditions;
    private final List<Progressable> progressablesList;
    private final ActionHolder completionActions;
    private final Consumer<PlayerInteractEvent> onInteract;

    public Blueprint(String id, List<ItemStack> ingredients, List<AbstractCondition> conditions,
                     List<Progressable> progressables, Action... completionActions) {
        this.id = id;
        this.ingredients = ingredients;
        this.completionConditions = new ConditionHolder(conditions);
        this.progressablesList = new ArrayList<>(progressables);
        this.completionActions = new ActionHolder(completionActions);

        this.onInteract = interact -> {
            // Set item in hand to blueprint progression item
            BlueprintManager blueprintManager = FactoriesPlugin.getInstance().getBlueprintManager();
            BlueprintProgress blueprintProgress = blueprintManager.addBlueprintProgress(this);
            interact.getPlayer().getInventory().setItemInMainHand(blueprintProgress.getProgressItem(interact.getPlayer().getWorld()));

            interact.getPlayer().playSound(interact.getPlayer().getLocation(), Sound.ENTITY_BREEZE_HURT,
                    1, 1);
            interact.getPlayer().playSound(interact.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                    1, 1);
            Chat.tell(interact.getPlayer(), "&f&k;; &r&b&lBlueprint Revealed &f&k;;");
            Chat.tell(interact.getPlayer(), "&7* Hint: Drag and Drop ingredients onto the blueprint! *");
        };
    }

    public ItemStack getItem() {
        return new ItemBuilder(Material.FILLED_MAP)
                .setName("&f&k;; &r&b&lHidden Blueprint &f(Right-Click To Reveal) &f&k;;")
                .setPersistentData(new NamespacedKey(FactoriesPlugin.getInstance(), "blueprint"), id)
                .build();
    }

}
