package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Credit extends Resource {
    public Credit() {
        //  TODO: Determine adequate material icon for credits.
        super("Credit", 1.00, new MaterialAmountPair(Material.EMERALD, 1));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorize("#FFD700" + getName() + ": ");
    }
}
