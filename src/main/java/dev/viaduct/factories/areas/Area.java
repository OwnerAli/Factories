package dev.viaduct.factories.areas;

import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import dev.viaduct.factories.utils.OutlineUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Set;

@Getter
public class Area {

    private final Set<GridSquare> gridSquares;
    private final OutlineUtils outlineUtils;
    private int amountOfMatchingBlocksInArea;

    public Area(Set<GridSquare> gridSquares) {
        this.gridSquares = gridSquares;
        this.outlineUtils = new OutlineUtils(this);
    }

    public void toggleOutline(Player player) {
        outlineUtils.toggleOutline(player);
    }

    public void countBlocksInArea(Location location, Set<Material> materials) {
        gridSquares.forEach(gridSquare -> amountOfMatchingBlocksInArea += gridSquare.countBlocksInSquare(location.getWorld(),
                materials, location));
    }

}