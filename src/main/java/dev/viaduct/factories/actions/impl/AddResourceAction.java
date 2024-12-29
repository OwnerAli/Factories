package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;

import java.util.Optional;

public class AddResourceAction implements Action {

    private final String resourceName;
    private final double amount;

    public AddResourceAction(String resourceName, double amount) {
        this.resourceName = resourceName;
        this.amount = amount;
    }

    @Override
    public void execute(FactoryPlayer factoryPlayer) {
        Optional<Resource> resourceOptional = FactoriesPlugin.getInstance().getResourceManager()
                .getResource(resourceName);

        resourceOptional.ifPresent(resource -> {
            factoryPlayer.getBank()
                    .addToResource(resource, factoryPlayer.getScoreboard(), amount);
            Chat.tell(factoryPlayer.getPlayer(), "Sun Industries has added " + amount + " " + resource.getName() + " to your account.");
        });
    }

}
