package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Stone extends Resource {

    public Stone() {
        super("Stone", 1.0,
                new MaterialAmountPair(Material.STONE, 4));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#bfbfbf" + getName() + ": ");
    }

}
