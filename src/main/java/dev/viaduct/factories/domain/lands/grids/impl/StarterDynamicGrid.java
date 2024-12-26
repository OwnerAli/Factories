package dev.viaduct.factories.domain.lands.grids.impl;

import dev.viaduct.factories.domain.lands.grids.squares.impl.PurchasableSquare;
import dev.viaduct.factories.resources.ResourceCost;
import org.bukkit.Location;

public class StarterDynamicGrid extends DynamicGrid {

    public StarterDynamicGrid(int squareSize, int gridWidth, int gridLength, int gridDepth, int gridHeight, Location center) {
        super(squareSize, gridWidth, gridLength, gridDepth, gridHeight, center);

        generateManually();
    }

    /*
    GUIDE:
    - NORTH: X = 0, Z = -1
    - NORTH EAST: X = 1, Z = -1
    - NORTH WEST: X = -1, Z = -1
    - EAST: X = 1, Z = 0
    - SOUTH: X = 0, Z = 1
    - SOUTH EAST: X = 0, Z = 0
    - SOUTH WEST: X = -1, Z = 1
    - WEST: X = -1, Z = 0
     */
    @Override
    public void generateManually() {
        int squareSize = getSquareSize();

        // You have to add this to orient the grid
        addGridSquare(new PurchasableSquare(0, 0, squareSize, new ResourceCost("wood", 3))); // CENTER

        // Add the original positive quadrant
        addGridSquare(new PurchasableSquare(0, -1, squareSize, new ResourceCost("wood", 3))); // NORTH
        addGridSquare(new PurchasableSquare(1, -1, squareSize, new ResourceCost("wood", 50))); // NORTH EAST
        addGridSquare(new PurchasableSquare(-1, -1, squareSize, new ResourceCost("wood", 50))); // NORTH WEST
        addGridSquare(new PurchasableSquare(1, 0, squareSize, new ResourceCost("wood", 3))); // EAST
        addGridSquare(new PurchasableSquare(0, 1, squareSize, new ResourceCost("wood", 3))); // SOUTH
        addGridSquare(new PurchasableSquare(1, 1, squareSize, new ResourceCost("wood", 50))); // SOUTH EAST
        addGridSquare(new PurchasableSquare(-1, 1, squareSize, new ResourceCost("wood", 50))); // SOUTH WEST
        addGridSquare(new PurchasableSquare(-1, 0, squareSize, new ResourceCost("wood", 3))); // WEST

        // Add one more layer of squares
        addGridSquare(new PurchasableSquare(0, -2, squareSize, new ResourceCost("wood", 50))); // NORTH
        addGridSquare(new PurchasableSquare(1, -2, squareSize, new ResourceCost("wood", 50))); // NORTH EAST
        addGridSquare(new PurchasableSquare(-1, -2, squareSize, new ResourceCost("wood", 50))); // NORTH WEST
        addGridSquare(new PurchasableSquare(2, -1, squareSize, new ResourceCost("wood", 50))); // NORTH EAST
        addGridSquare(new PurchasableSquare(-2, -1, squareSize, new ResourceCost("wood", 50))); // NORTH WEST
        addGridSquare(new PurchasableSquare(2, 0, squareSize, new ResourceCost("wood", 50))); // EAST
        addGridSquare(new PurchasableSquare(-2, 0, squareSize, new ResourceCost("wood", 50))); // WEST
        addGridSquare(new PurchasableSquare(0, 2, squareSize, new ResourceCost("wood", 50))); // SOUTH
        addGridSquare(new PurchasableSquare(1, 2, squareSize, new ResourceCost("wood", 50))); // SOUTH EAST
        addGridSquare(new PurchasableSquare(-1, 2, squareSize, new ResourceCost("wood", 50))); // SOUTH WEST
    }

    // generate grid squares offsetting by the center
    @Override
    public void generateAutomatically() {
        int squareSize = getSquareSize();

        for (int x = 0; x < getGridWidth(); x++) {
            for (int z = 0; z < getGridLength(); z++) {
                // Add the original positive quadrant
                addGridSquare(new PurchasableSquare(x, z, squareSize, new ResourceCost("wood", 7 + x)));

                // Add the symmetric quadrants by reflecting over the x and z axes
                if (x != 0) addGridSquare(new PurchasableSquare(-x, z, squareSize,
                        new ResourceCost("wood", 7 + x))); // Negative x, positive z
                if (z != 0) addGridSquare(new PurchasableSquare(x, -z, squareSize,
                        new ResourceCost("wood", 7 + x)));
                if (x != 0 && z != 0) addGridSquare(new PurchasableSquare(-x, -z, squareSize,
                        new ResourceCost("wood", 7 + x))); // Negative x, negative z
            }
        }
    }

}
