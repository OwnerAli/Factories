package dev.viaduct.factories.domain.lands.grids;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.TextDisplay;

public class GridSquare {

    private final int gridX;
    private final int gridZ;

    private TextDisplay purchaseSquareText;

    public GridSquare(int gridX, int gridZ) {
        this.gridX = gridX;
        this.gridZ = gridZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GridSquare other = (GridSquare) obj;
        return gridX == other.gridX && gridZ == other.gridZ;
    }

    @Override
    public int hashCode() {
        return 31 * gridX + gridZ;
    }

    private Location getCenter(World world) {
        return new Location(world, gridX, 0, gridZ);
    }

}
