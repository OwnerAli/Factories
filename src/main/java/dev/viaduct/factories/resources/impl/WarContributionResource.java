package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;

public class WarContributionResource extends Resource {

    public WarContributionResource() {
        super("WCS", 1);
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#4CAF50") + getName() + ": ";
    }

    @Override
    public String getSection() {
        return "&f&lContributions    ";
    }

    @Override
    public int getPriority() {
        return 3;
    }

}
