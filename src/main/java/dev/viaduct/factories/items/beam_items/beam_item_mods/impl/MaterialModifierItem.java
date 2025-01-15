package dev.viaduct.factories.items.beam_items.beam_item_mods.impl;

import dev.viaduct.factories.items.beam_items.beam_item_mods.AbstractModifierItem;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MaterialModifierItem extends AbstractModifierItem {

    private final Material material;

    public MaterialModifierItem(String id, Material material) {
        super(id);
        this.material = material;
    }

    @Override
    public void modifyBeamConfiguration(BeamConfiguration beamConfiguration, Player player) {
        beamConfiguration.setBeamMaterial(material);
        Chat.tell(player, "&eBeam material set to " + material.name());
    }

}
