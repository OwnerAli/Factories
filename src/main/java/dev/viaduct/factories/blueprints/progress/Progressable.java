package dev.viaduct.factories.blueprints.progress;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class Progressable {

    private final double requiredProgress;
    private final EnumSet<ClickType> triggerSet;

    protected Progressable(double requiredProgress, ClickType... triggers) {
        this.requiredProgress = requiredProgress;
        this.triggerSet = EnumSet.noneOf(ClickType.class);
        triggerSet.addAll(Arrays.asList(triggers));
    }

    public void progress(InventoryClickEvent event, BlueprintProgress blueprintProgress, Map<Progressable, Integer> progressMap) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        event.setCurrentItem(blueprintProgress.getProgressItem(player.getWorld()));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

        if (!(progressMap.get(this) >= requiredProgress)) return;
        progressMap.remove(this);

        if (progressMap.isEmpty()) {
            blueprintProgress.complete(event, player);
        }
    }

    public abstract List<String> getDescription(double progress);

}