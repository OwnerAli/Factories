package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import lombok.Getter;

@Getter
public class WCSContributionEvent extends FactoryPlayerEvent {

    private final double amount;

    public WCSContributionEvent(FactoryPlayer factoryPlayer, double amount) {
        super(factoryPlayer);
        this.amount = amount;
    }

}
