package dev.viaduct.factories.tasks;

import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TaskHolder {

    private final Map<Objective, Integer> objectiveProgressMap;
    private Task task;

    @Setter
    private Area currentTaskArea;

    public TaskHolder() {
        this.objectiveProgressMap = new HashMap<>();
    }

    public void setup(Task task, FactoryPlayer factoryPlayer) {
        this.task = task;
        task.start(factoryPlayer);
    }

    public void completeObjective(FactoryPlayer factoryPlayer, Objective objective) {
        objectiveProgressMap.remove(objective);

        if (!objectiveProgressMap.isEmpty()) return;
        this.task.complete(factoryPlayer);
        this.task = null;

        if (currentTaskArea != null) {
            currentTaskArea.toggleOutline(factoryPlayer.getPlayer());
            currentTaskArea = null;
        }
    }

    public void incrementObjectiveProgress(FactoryPlayer factoryPlayer, Objective objective) {
        if (!objectiveProgressMap.containsKey(objective)) return;

        Player player = factoryPlayer.getPlayer();

        if (objectiveProgressMap.get(objective) + 1 >= objective.getAmount(factoryPlayer)) {
            Chat.sendActionbar(player, "&6&lObjective: &e" + objective.getDescription() +
                    " &f» &e&lCOMPLETE!");
            completeObjective(factoryPlayer, objective);
            return;
        }
        Chat.sendActionbar(player, "&6&lObjective: &e" + objective.getDescription() +
                " &e(" + ((objectiveProgressMap.get(objective) + 1) * 100) / (objective.getAmount(factoryPlayer)) + "%)");
        objectiveProgressMap.put(objective, objectiveProgressMap.get(objective) + 1);
    }

    public void addObjective(Objective objective) {
        objectiveProgressMap.put(objective, 0);
    }

    public boolean doingTask() {
        return task != null;
    }

}
