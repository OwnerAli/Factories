package dev.viaduct.factories.tasks;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.actions.ActionHolder;
import dev.viaduct.factories.actions.impl.MultipleChatMessagesAction;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.rules.Rule;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public abstract class Task {

    private final String id;
    private final Set<Rule> rulesSet;
    private final Set<Objective> objectivesList;
    private final ActionHolder startActions;
    private final ActionHolder completeActions;

    public Task(String id, Set<Rule> rules, List<Action> startActions, List<Action> completeActions,
                Objective... objectivesList) {
        this.id = id;
        this.rulesSet = new HashSet<>(rules);
        this.startActions = new ActionHolder(startActions);
        this.completeActions = new ActionHolder(completeActions);
        this.objectivesList = Set.of(objectivesList);

        PluginManager pluginManager = FactoriesPlugin.getInstance()
                .getServer()
                .getPluginManager();
        this.objectivesList.forEach(objective -> pluginManager.registerEvents(objective, FactoriesPlugin.getInstance()));
        this.rulesSet.forEach(rule -> pluginManager.registerEvents(rule, FactoriesPlugin.getInstance()));
    }

    public void start(FactoryPlayer factoryPlayer) {
        MultipleChatMessagesAction multipleChatMessagesAction =
                new MultipleChatMessagesAction(" ", "&6&lTask &f» " + id, " ", "&fObjectives ");

        objectivesList.forEach(objective -> {
            objective.setupObjectiveForPlayer(factoryPlayer);

            factoryPlayer.getTaskHolder()
                    .addObjective(objective);

            multipleChatMessagesAction
                    .addMessage("&7 • " + objective.getDescription());
        });

        multipleChatMessagesAction.addMessage(" ");
        multipleChatMessagesAction.addMessage("&fRules ");

        rulesSet.forEach(rule -> multipleChatMessagesAction.addMessage("&7 • " + rule.getDescription()));
        multipleChatMessagesAction.addMessage(" ");
        startActions.addAction(multipleChatMessagesAction);
        startActions.executeAllActions(factoryPlayer);
    }

    public void complete(FactoryPlayer factoryPlayer) {
        completeActions.executeAllActions(factoryPlayer);
    }

    public abstract void end(FactoryPlayer factoryPlayer);

}