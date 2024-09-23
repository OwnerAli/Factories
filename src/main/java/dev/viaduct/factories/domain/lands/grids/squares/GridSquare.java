package dev.viaduct.factories.domain.lands.grids.squares;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Set;

@Getter
public class GridSquare {

    private final int startX, startZ, size;

    public GridSquare(int startX, int startZ, int size) {
        this.startX = startX;
        this.startZ = startZ;
        this.size = size;
    }

    public int countBlocksInSquare(World world, Set<Material> blockTypesToCount, Location offsetLocation) {
        int count = 0;

        // Calculate the bounds of the square
        int halfSize = size / 2;

        int minX = startX - halfSize;
        int maxX = startX + halfSize + 1; // +1 to include the edge block
        int minZ = startZ - halfSize;
        int maxZ = startZ + halfSize + 1; // +1 to include the edge block

        // Iterate through the blocks within the square
        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                int adjustedX = x + offsetLocation.getBlockX();
                int adjustedZ = z + offsetLocation.getBlockZ();

                for (int y = 0; y <= 15; y++) {
                    // Adjust coordinates with the offset location
                    Material blockAtLocation = world.getBlockAt(
                                    adjustedX,
                                    y,
                                    adjustedZ)
                            .getType();
                    if (blockTypesToCount.contains(blockAtLocation)) count++;
                }
            }
        }

        return count;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GridSquare gridSquare && gridSquare.getStartX() == startX && gridSquare.getStartZ() == startZ;
    }

}
