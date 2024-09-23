package dev.viaduct.factories.domain.lands.grids.squares.impl;

import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import dev.viaduct.factories.resources.ResourceCost;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PurchasableSquare extends GridSquare {

    private final List<ResourceCost> resourceCostList;

    public PurchasableSquare(int startX, int startZ, int size, ResourceCost... resourceCosts) {
        super(startX, startZ, size);
        this.resourceCostList = new ArrayList<>(List.of(resourceCosts));
    }

}
