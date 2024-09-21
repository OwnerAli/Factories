package dev.viaduct.factories.domain.lands.grids;

import dev.viaduct.factories.domain.lands.grids.squares.impl.PurchasableSquare;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    protected final List<PurchasableSquare> gridSquaresList;

    public Grid() {
        this.gridSquaresList = new ArrayList<>();
    }

    public void addGridSquare(PurchasableSquare gridSquare) {
        gridSquaresList.add(gridSquare);
    }

}
