package dev.viaduct.factories.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.registries.impl.GeneratorRegistry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("faca")
@CommandPermission("factories.admin.*")
@SuppressWarnings("unused")
public class AdminCommand extends BaseCommand {

    public AdminCommand() {}

    @Subcommand("completeObjective")
    public void completeObjective(Player player, int amount) {
        FactoryPlayerRegistry.getInstance()
                .get(player)
                .ifPresent(factoryPlayer -> factoryPlayer.getTaskHolder()
                        .completeMultipleObjectivesByOrder(factoryPlayer, amount));
    }

    @Subcommand("givegen")
    @Syntax("<generatorId> <amount> [target (blank for self)]")
    @CommandCompletion("@generatorIds")
    public void giveGenerator(Player player, String generatorId, int amount, @Optional Player target) {
        ItemStack generatorPlaceItem = FactoriesPlugin.getRegistryManager()
                .getRegistry(GeneratorRegistry.class)
                .get(generatorId)
                .orElseThrow()
                .getGeneratorPlaceItem();

        Player recipient = (target == null) ? player : target;
        for (int i = 0; i < amount; i++) {
            recipient.getInventory().addItem(generatorPlaceItem);
        }
    }

    @Subcommand("givebp")
    @Syntax("<blueprintId> <amount> [target (blank for self)]")
    @CommandCompletion("@blueprintIds")
    public void giveBlueprint(Player player, String blueprintId, int amount, @Optional Player target) {
        ItemStack blueprintHiddenItem = BlueprintRegistry.getInstance()
                .get(blueprintId)
                .orElseThrow()
                .getRevealItem();

        Player recipient = (target == null) ? player : target;
        for (int i = 0; i < amount; i++) {
            recipient.getInventory().addItem(blueprintHiddenItem);
        }
    }

    @Subcommand("giveresource")
    @Syntax("<resourceId> <amount> [target (blank for self)]")
    @CommandCompletion("@resourceIds")
    public void giveResource(Player player, String resourceId, int amount, @Optional Player target) {
        Player recipient = (target == null) ? player : target;
        FactoryPlayerRegistry.getInstance()
                .get(recipient)
                .ifPresent(factoryPlayer -> factoryPlayer.getBank()
                        .addToResource(resourceId, factoryPlayer.getScoreboard(), amount));
    }

}