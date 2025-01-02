package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.ContributableResource;
import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Material;

public class Wood extends ContributableResource {

    public Wood() {
        super("Wood", 1.0,
                new MaterialAmountPair(Material.OAK_WOOD, 4),
                new MaterialAmountPair(Material.OAK_FENCE, 0.5),
                new MaterialAmountPair(Material.DARK_OAK_WOOD, 2));
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#a8996f" + getName() + ": ");
    }

    @Override
    public double getContribution() {
        return 0.1;
    }

    @Override
    public String getDisplayName() {
        return getFormattedName().replace(": ", "");
    }

    @Override
    public Material getDisplayMaterial() {
        return Material.OAK_WOOD;
    }

    @Override
    public String getSection() {
        return "&f&lResources    ";
    }

}