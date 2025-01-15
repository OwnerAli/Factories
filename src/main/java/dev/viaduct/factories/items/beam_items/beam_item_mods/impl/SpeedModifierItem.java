package dev.viaduct.factories.items.beam_items.beam_item_mods.impl;

import dev.viaduct.factories.items.beam_items.beam_item_mods.AbstractModifierItem;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.entity.Player;

public class SpeedModifierItem extends AbstractModifierItem {

    private final int speedModifierPercentage;

    public SpeedModifierItem(String id, int speedModifierPercentage) {
        super(id);
        this.speedModifierPercentage = speedModifierPercentage;
    }

    @Override
    public void modifyBeamConfiguration(BeamConfiguration beamConfiguration, Player player) {
        double currentSpeed = beamConfiguration.getSpeed();
        double newSpeed = currentSpeed * (1 + speedModifierPercentage / 100.0);
        beamConfiguration.setSpeed(newSpeed);
        Chat.tell(player, "&eSpeed modifier applied!");
        Chat.tell(player, "&eNew speed: &a" + newSpeed);
    }

}
