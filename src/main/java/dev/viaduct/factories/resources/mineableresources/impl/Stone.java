package dev.viaduct.factories.resources.mineableresources.impl;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.mineableresources.MineableResource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Stone extends MineableResource {

    public Stone() {
        super("Stone", 1.0,
                new MaterialAmountPair(Material.STONE, 4));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#bfbfbf" + getName() + ": ");
    }

}
