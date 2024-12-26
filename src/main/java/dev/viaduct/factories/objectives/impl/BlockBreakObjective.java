package dev.viaduct.factories.objectives.impl;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.objectives.Objective;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

@Getter
public class BlockBreakObjective extends Objective {

    private final Material blockType;

    public BlockBreakObjective(Material blockType, int amount) {
        super(amount, "Break " + amount + " " + blockType.name() + " Blocks");
        this.blockType = blockType;
    }

    public BlockBreakObjective(Material blockType, int amount, String... description) {
        super(amount, description);
        this.blockType = blockType;
    }

    @Override
    public void setupObjectiveForPlayer(FactoryPlayer factoryPlayer) {

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != blockType) return;

        Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance()
                .get(event.getPlayer());
        factoryPlayerOptional.ifPresent(factoryPlayer -> {
            if (!progressObjective(factoryPlayer)) return;
            factoryPlayer.getBank()
                    .getResourceByMaterial(event.getBlock().getType())
                    .ifPresent(resource -> resource.getMaterialAmountPairsList()
                            .stream()
                            .filter(pair -> pair.material().equals(event.getBlock().getType()))
                            .forEach(pair -> Chat.tell(event.getPlayer(), "&6&lObjective &f» &e+" + pair.amount() + "x " +
                                    resource.getFormattedName().replace(": ", ""))));
            factoryPlayer.getPlayer().playSound(factoryPlayer.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        });
    }

}