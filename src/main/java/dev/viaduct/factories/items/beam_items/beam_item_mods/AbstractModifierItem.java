package dev.viaduct.factories.items.beam_items.beam_item_mods;

import dev.viaduct.factories.items.CustomItem;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import org.bukkit.entity.Player;

public abstract class AbstractModifierItem extends CustomItem {

    public AbstractModifierItem(String id) {
        super(id);
    }

    public abstract void modifyBeamConfiguration(BeamConfiguration beamConfiguration, Player player);

}
