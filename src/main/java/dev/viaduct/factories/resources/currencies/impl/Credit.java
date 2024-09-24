package dev.viaduct.factories.resources.currencies.impl;

import dev.viaduct.factories.resources.currencies.Currency;
import dev.viaduct.factories.utils.Chat;

public class Credit extends Currency {
    public Credit() {
        super("Credits", 1.0);
    }

    @Override
    public String getFormattedName() {
        return Chat.colorizeHex("#FFD700" + getName() + ": ");
    }
}
