package dev.viaduct.factories.resources;

import dev.viaduct.factories.contributions.Contributable;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.domain.players.FactoryPlayer;

public abstract class ContributableResource extends Resource implements Contributable {

    protected ContributableResource(String name, double incrementAmount) {
        super(name, incrementAmount);
    }

    protected ContributableResource(String name, double incrementAmount, MaterialAmountPair... materialAmountPairs) {
        super(name, incrementAmount, materialAmountPairs);
    }

    @Override
    public void contribute(FactoryPlayer factoryPlayer, double amountOfResource) {
        double playerResourceAmt = factoryPlayer.getBank().getResourceAmt(this);

        if (playerResourceAmt < amountOfResource) return;
        final double amount = Math.min(playerResourceAmt, amountOfResource);

        // Contribution per one resource
        double contribution = getContribution();

        // Get amount of resources to contribute based on contribution
        final double totalContribution = amount * contribution;

        Bank bank = factoryPlayer.getBank();
        bank.removeFromResource(this, factoryPlayer.getScoreboard(), amount);
        bank.addToResource("WCS", factoryPlayer.getScoreboard(), totalContribution);
    }

}
