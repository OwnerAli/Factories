package dev.viaduct.factories.actions;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionHolder {

    private final List<Action> actions;

    public ActionHolder(Action... actions) {
        this.actions = new ArrayList<>(Arrays.asList(actions));
    }

    public ActionHolder(List<Action> actions) {
        this.actions = new ArrayList<>(actions);
    }

    public void executeAllActions(FactoryPlayer factoryPlayer) {
        actions.forEach(action -> action.execute(factoryPlayer));
    }

    public void executeAllActions(Player player) {
        FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .get(player)
                .ifPresent(factoryPlayer -> actions.forEach(action ->
                        action.execute(factoryPlayer)));
    }

    public void addAction(Action action) {
        actions.add(action);
    }

}