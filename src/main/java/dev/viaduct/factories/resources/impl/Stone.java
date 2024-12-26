package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.ContributableResource;
import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Stone extends ContributableResource {

    public Stone() {
        super("Stone", 1.0,
                new MaterialAmountPair(Material.STONE, 4));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#bfbfbf" + getName() + ": ");
    }

    @Override
    public double getContribution() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return getFormattedName().replace(": ", "");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.STONE;
    }

}
