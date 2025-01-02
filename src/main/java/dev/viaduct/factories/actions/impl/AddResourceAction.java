package dev.viaduct.factories.actions.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.actions.Action;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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

            Player player = factoryPlayer.getPlayer();

            player.sendTitle(
                    Chat.colorizeHex("#FF4500&l(!) Alert (!)"),
                    Chat.colorizeHex("#FFC107Sun Industries has added " + amount + " " + resource.getName() + " to your account."),
                    10, 40, 10
            );
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f); // Bright ping
            player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 0.5f, 1.0f); // Celebratory finish
        });
    }

}
