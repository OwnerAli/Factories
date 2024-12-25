package dev.viaduct.factories.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dev.viaduct.factories.registries.RegistryManager;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.entity.Player;

@CommandAlias("faca")
@CommandPermission("factories.admin.*")
public class AdminCommand extends BaseCommand {

    private final RegistryManager registryManager;

    public AdminCommand(RegistryManager registryManager) {
        this.registryManager = registryManager;
    }

    @Subcommand("completeObjective")
    public void completeObjective(Player player, int amount) {
        FactoryPlayerRegistry.getInstance()
                .get(player)
                .ifPresent(factoryPlayer -> factoryPlayer.getTaskHolder()
                        .completeMultipleObjectivesByOrder(factoryPlayer, amount));
    }

}
