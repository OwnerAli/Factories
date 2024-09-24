package dev.viaduct.factories.resources.mineableresources.impl;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.mineableresources.MineableResource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Wood extends MineableResource {

    public Wood() {
        super("Wood", 1.0,
                new MaterialAmountPair(Material.OAK_WOOD, 4),
                new MaterialAmountPair(Material.OAK_FENCE, 0.5));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#a8996f" + getName() + ": ");
    }

}