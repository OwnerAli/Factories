package dev.viaduct.factories.objectives;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

@Getter
@Setter
public abstract class Objective implements Listener {

    private String description;
    private int amount;

    public Objective(String description, int amount) {
        this.description = description;
        this.amount = amount;
    }

    public abstract int getAmount(FactoryPlayer factoryPlayer);

    public abstract void setupObjectiveForPlayer(FactoryPlayer factoryPlayer);

    public abstract void progressObjective(FactoryPlayer factoryPlayer);

}
