package dev.viaduct.factories.resources;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Resource {

    private final String name;
    private final double incrementAmount;
    private final List<MaterialAmountPair> materialAmountPairsList;

    protected Resource(String name, double incrementAmount) {
        this.name = name;
        this.incrementAmount = incrementAmount;
        this.materialAmountPairsList = new ArrayList<>();
    }

    public abstract String getFormattedName();

}
