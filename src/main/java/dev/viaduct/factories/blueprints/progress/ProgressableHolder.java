package dev.viaduct.factories.blueprints.progress;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class ProgressableHolder {

    private final Map<Progressable, Integer> progressMap;

    @Getter
    private final Consumer<InventoryClickEvent> progressConsumer;

    public ProgressableHolder(BlueprintProgress blueprintProgress, List<Progressable> progressables) {
        this.progressMap = new HashMap<>();
        for (Progressable progressable : progressables) {
            progressMap.put(progressable, 0);
        }
        this.progressConsumer = event -> {
            if (progressMap.isEmpty()) {
                blueprintProgress.complete(event, (Player) event.getWhoClicked());
                return;
            }

            List<Progressable> progressablesToProgress = new ArrayList<>(progressMap.keySet());
            progressablesToProgress.forEach(progressable -> {
                if (!(progressable.getTriggerSet().contains(event.getClick()))) return;
                progressable.progress(event, blueprintProgress, progressMap);
            });
        };
    }

    public List<String> getDescription() {
        List<String> descriptionList = new ArrayList<>();
        progressMap.keySet().forEach(progressable -> progressable.getDescription(progressMap.get(progressable))
                .forEach(description -> descriptionList.add("&7 • " + description)));

        return descriptionList;
    }

}
