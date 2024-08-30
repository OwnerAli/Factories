package dev.viaduct.factories.resources.impl;

import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.utils.Chat;

public class Wood extends Resource {

    public Wood() {
        super("Wood");
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#a8996fWood: ");
    }

}