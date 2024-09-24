package dev.viaduct.factories.resources.currencies;

import dev.viaduct.factories.resources.Resource;

public class Currency extends Resource {

    public Currency(String name, double incrementAmount) {
        super(name, incrementAmount);
    }

    @Override
    public String getFormattedName() {
        return "";
    }
}
