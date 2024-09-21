package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Wood extends Resource {

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