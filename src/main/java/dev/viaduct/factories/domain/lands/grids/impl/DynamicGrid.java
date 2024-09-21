package dev.viaduct.factories.domain.lands.grids.impl;

import dev.viaduct.factories.domain.lands.grids.Grid;
import dev.viaduct.factories.domain.lands.grids.squares.impl.PurchasableSquare;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public abstract class DynamicGrid extends Grid {

    private final int squareSize;
    private final int gridWidth;
    private final int gridLength;
    private final int gridDepth;
    private final int gridHeight;
    private final Location center;

    public DynamicGrid(int squareSize, int gridWidth, int gridLength, int gridDepth, int gridHeight, Location center) {
        super();
        this.squareSize = squareSize;
        this.gridWidth = gridWidth;
        this.gridLength = gridLength;
        this.gridDepth = gridDepth;
        this.gridHeight = gridHeight;
        this.center = center;
    }

    public PurchasableSquare getGridSquare(Location location) {
        return getGridSquare(location.getBlockX(), location.getBlockZ());
    }

    public boolean isLocationInGrid(Location location) {
        return gridSquaresList.contains(getGridSquare(location));
    }

    public abstract void generateManually();

    public abstract void generateAutomatically();

    private PurchasableSquare getGridSquare(int x, int z) {
        int relativeX = x - center.getBlockX();
        int relativeZ = z - center.getBlockZ();

        int gridX = (int) Math.floor((double) (relativeX + squareSize / 2) / squareSize);
        int gridZ = (int) Math.floor((double) (relativeZ + squareSize / 2) / squareSize);

        return gridSquaresList.stream()
                .filter(gridSquare -> gridSquare.getStartX() == gridX && gridSquare.getStartZ() == gridZ)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Location not in grid"));
    }

}
