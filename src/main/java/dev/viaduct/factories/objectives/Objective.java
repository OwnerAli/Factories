package dev.viaduct.factories.objectives;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.tasks.TaskHolder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

import java.util.List;

@Getter
@Setter
public abstract class Objective implements Listener {

    private List<String> description;
    private int amount;

    public Objective(int amount, String... description) {
        this.description = List.of(description);
        this.amount = amount;
    }

    public int getAmount(FactoryPlayer factoryPlayer) {
        return amount;
    }

    public void progressObjective(FactoryPlayer factoryPlayer) {
        TaskHolder taskHolder = factoryPlayer.getTaskHolder();
        taskHolder.incrementObjectiveProgress(factoryPlayer, this);
    }

    public abstract void setupObjectiveForPlayer(FactoryPlayer factoryPlayer);

}
