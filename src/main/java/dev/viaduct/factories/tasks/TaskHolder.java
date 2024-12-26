package dev.viaduct.factories.tasks;

import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.scoreboards.FactoryScoreboard;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class TaskHolder {

    private final Map<Objective, Integer> objectiveProgressMap;

    private Objective currentObjective;
    private Task task;

    @Setter
    private Area currentTaskArea;

    public TaskHolder() {
        this.objectiveProgressMap = new LinkedHashMap<>();
    }

    public void setup(Task task, FactoryPlayer factoryPlayer) {
        this.task = task;
        task.start(factoryPlayer);

        if (task.isOrderMatters()) {
            this.currentObjective = objectiveProgressMap.keySet().iterator().next();
        }

        factoryPlayer.getScoreboard()
                .addToScoreboard(task);
    }

    public void completeObjective(FactoryPlayer factoryPlayer, Objective objective) {
        objectiveProgressMap.remove(objective);

        if (!objectiveProgressMap.isEmpty()) {
            factoryPlayer.getPlayer().playNote(factoryPlayer.getPlayer().getLocation(),
                    Instrument.BELL,
                    new Note(7));

            FactoryScoreboard scoreboard = factoryPlayer.getScoreboard();
            for (int i = 0; i < objective.getDescription().size(); i++) {
                if (i == 0) {
                    scoreboard.removeLine(Chat.colorize("&f • " + objective.getDescription().get(i)));
                    continue;
                }

                String objectiveDescription = objective.getDescription().get(i);
                scoreboard.removeLine(Chat.colorize("&f   " + objectiveDescription));
            }

            if (task.isOrderMatters()) {
                currentObjective = objectiveProgressMap.keySet().iterator().next();
            }
            return;
        }
        this.task.complete(factoryPlayer);

        if (this.task.getFollowUpTask() != null) {
            setup(this.task.getFollowUpTask(), factoryPlayer);
            return;
        }

        this.task = null;

        if (currentTaskArea != null) {
            currentTaskArea.toggleOutline(factoryPlayer.getPlayer());
            currentTaskArea = null;
        }
    }

    public void completeMultipleObjectivesByOrder(FactoryPlayer factoryPlayer, int amountToComplete) {
        if (objectiveProgressMap.isEmpty()) return;
        for (int i = 0; i < amountToComplete; i++) {
            completeObjective(factoryPlayer, objectiveProgressMap.keySet().iterator().next());
        }
    }

    public boolean incrementObjectiveProgress(FactoryPlayer factoryPlayer, Objective objective) {
        if (task == null) return false;
        if (task.isOrderMatters() && !objective.equals(currentObjective)) return false;
        if (!objectiveProgressMap.containsKey(objective)) return false;

        Player player = factoryPlayer.getPlayer();

        if (objectiveProgressMap.get(objective) + 1 >= objective.getAmount(factoryPlayer)) {
            Chat.sendActionbar(player, "&6&lObjective: &e" + objective.getDescription().get(0) +
                    " &f» &e&lCOMPLETE!");
            completeObjective(factoryPlayer, objective);
            return true;
        }
        Chat.sendActionbar(player, "&6&lObjective: &e" + objective.getDescription().get(0) +
                " &e(" + ((objectiveProgressMap.get(objective) + 1) * 100) / (objective.getAmount(factoryPlayer)) + "%)");
        objectiveProgressMap.put(objective, objectiveProgressMap.get(objective) + 1);
        return true;
    }

    public void addObjective(Objective objective) {
        objectiveProgressMap.put(objective, 0);
    }

    public boolean doingTask() {
        return task != null;
    }

}
