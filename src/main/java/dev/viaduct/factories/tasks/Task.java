package dev.viaduct.factories.tasks;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.actions.impl.MultipleChatMessagesAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.rules.Rule;
import dev.viaduct.factories.scoreboards.ScoreboardListable;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public abstract class Task implements ScoreboardListable {

    private final String id;
    private final Task followUpTask;
    private final boolean orderMatters;
    private final Set<Rule> rulesSet;
    private final List<Objective> objectivesList;
    private final ActionHolder startActions;
    private final ActionHolder completeActions;

    public Task(String id, Task followUpTask, boolean orderMatters, Set<Rule> rules, List<Action> startActions, List<Action> completeActions,
                Objective... objectivesList) {
        this.id = id;
        this.followUpTask = followUpTask;
        this.orderMatters = orderMatters;
        this.rulesSet = new HashSet<>(rules);
        this.startActions = new ActionHolder(startActions);
        this.completeActions = new ActionHolder(completeActions);
        this.objectivesList = List.of(objectivesList);

        PluginManager pluginManager = FactoriesPlugin.getInstance()
                .getServer()
                .getPluginManager();
        this.objectivesList.forEach(objective -> pluginManager.registerEvents(objective, FactoriesPlugin.getInstance()));
        this.rulesSet.forEach(rule -> pluginManager.registerEvents(rule, FactoriesPlugin.getInstance()));
    }

    public void start(FactoryPlayer factoryPlayer) {
        MultipleChatMessagesAction multipleChatMessagesAction =
                new MultipleChatMessagesAction(" ", "&6&lTask &f» " + id, " ", "&fObjectives ");

        for (Objective objective : getObjectivesList()) {
            objective.setupObjectiveForPlayer(factoryPlayer);
            factoryPlayer.getTaskHolder().addObjective(objective);

            for (int i = 0; i < objective.getDescription().size(); i++) {
                if (i == 0) {
                    multipleChatMessagesAction.addMessage(Chat.colorize("&7 • " + objective.getDescription().get(i)));
                    continue;
                }

                String objectiveDescription = objective.getDescription().get(i);
                multipleChatMessagesAction.addMessage(Chat.colorize("&7   " + objectiveDescription));
            }

        }

        multipleChatMessagesAction.addMessage(" ");
        multipleChatMessagesAction.addMessage("&fRules ");

        rulesSet.forEach(rule -> multipleChatMessagesAction.addMessage("&7 • " + rule.getDescription()));
        multipleChatMessagesAction.addMessage(" ");
        multipleChatMessagesAction.addMessage("&fRewards ");

        completeActions.getRewardMessages().forEach(message -> multipleChatMessagesAction.addMessage("&7 • " + message));

        multipleChatMessagesAction.addMessage(" ");

        startActions.addAction(multipleChatMessagesAction);
        startActions.executeAllActions(factoryPlayer);
    }

    public void complete(FactoryPlayer factoryPlayer) {
        completeActions.executeAllActions(factoryPlayer);
        factoryPlayer.getScoreboard().removeFromScoreboard(this);
    }

    @Override
    public String getTitle() {
        return "&6&lTasks";
    }

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();

        for (Objective objective : getObjectivesList()) {

            for (int i = 0; i < objective.getDescription().size(); i++) {
                if (i == 0) {
                    lines.add(Chat.colorize("&f • " + objective.getDescription().get(i)));
                    continue;
                }

                String objectiveDescription = objective.getDescription().get(i);
                lines.add(Chat.colorize("&f   " + objectiveDescription));
            }

        }

        return lines;
    }

    public abstract void end(FactoryPlayer factoryPlayer);

}