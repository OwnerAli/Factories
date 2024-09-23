package dev.viaduct.factories.utils;

import dev.viaduct.factories.areas.Area;
import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutlineUtils {

    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private final List<Entity> displayEntitiesList;
    final Set<GridSquare> gridSquares;
    private final Area area;

    public OutlineUtils(Area area) {
        this.displayEntitiesList = new ArrayList<>();
        this.gridSquares = area.getGridSquares();
        this.area = area;
    }

    public void toggleOutline(Player player) {
        if (isOutlined()) {
            clearOutline();
        } else {
            outlineGridSquares(player);
        }
    }

    public void outlineGridSquares(Player player) {
        for (GridSquare square : gridSquares) {
            outlineSquare(player, square);
        }
    }

    private void outlineSquare(Player player, GridSquare square) {
//        // Get corner locations based on the square's start and end coordinates
//        Location bottomNorthWest = new Location(player.getWorld(), square.startX(), player.getLocation().getY(), square.startZ());
//        Location topSouthEast = new Location(player.getWorld(), square.getEndX(), player.getLocation().getY() + 5, square.getEndZ());
//
//        // Check neighbors and skip internal edges
//        boolean hasNorthNeighbor = gridSquares.stream().anyMatch(s -> s.getEndZ() == square.startZ() && s.startX() == square.startX());
//        boolean hasSouthNeighbor = gridSquares.stream().anyMatch(s -> s.startZ() == square.getEndZ() && s.startX() == square.startX());
//        boolean hasEastNeighbor = gridSquares.stream().anyMatch(s -> s.startX() == square.getEndX() && s.startZ() == square.startZ());
//        boolean hasWestNeighbor = gridSquares.stream().anyMatch(s -> s.getEndX() == square.startX() && s.startZ() == square.startZ());
//
//        // Outline the external edges only
//        if (!hasNorthNeighbor) {
//            // Outline north edge
//            spawnDisplayAtLocation(player, bottomNorthWest, new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), bottomNorthWest.getY(), topSouthEast.getZ()));
//        }
//
//        if (!hasSouthNeighbor) {
//            // Outline south edge
//            Location bottomSouthEast = new Location(bottomNorthWest.getWorld(), topSouthEast.getX(), bottomNorthWest.getY(), topSouthEast.getZ());
//            spawnDisplayAtLocation(player, bottomSouthEast, topSouthEast);
//        }
//
//        if (!hasEastNeighbor) {
//            // Outline east edge
//            Location topNorthEast = new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), topSouthEast.getY(), topSouthEast.getZ());
//            spawnDisplayAtLocation(player, topNorthEast, topSouthEast);
//        }
//
//        if (!hasWestNeighbor) {
//            // Outline west edge
//            Location topNorthWest = new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), topSouthEast.getY(), bottomNorthWest.getZ());
//            spawnDisplayAtLocation(player, topNorthWest, bottomNorthWest);
//        }
    }

    public void clearOutline() {
        displayEntitiesList.forEach(Entity::remove);
        displayEntitiesList.clear();
    }

    public boolean isOutlined() {
        return !displayEntitiesList.isEmpty();
    }

    private boolean hasAdjacentSquare(GridSquare currentSquare, Direction direction) {
//        for (GridSquare square : area.getGridSquares()) {
//            switch (direction) {
//                case NORTH:
//                    if (currentSquare.startZ() == square.getEndZ() + 1 &&
//                            currentSquare.startX() == square.startX()) {
//                        return true;
//                    }
//                    break;
//                case SOUTH:
//                    if (currentSquare.getEndZ() == square.startZ() - 1 &&
//                            currentSquare.startX() == square.startX()) {
//                        return true;
//                    }
//                    break;
//                case EAST:
//                    if (currentSquare.getEndX() == square.startX() - 1 &&
//                            currentSquare.startZ() == square.startZ()) {
//                        return true;
//                    }
//                    break;
//                case WEST:
//                    if (currentSquare.startX() == square.getEndX() + 1 &&
//                            currentSquare.startZ() == square.startZ()) {
//                        return true;
//                    }
//                    break;
//            }
//        }
//        return false;
        return true;
    }

    private BlockDisplay spawnDisplayAtLocation(Player player, Location startLocation, Location targetLocation) {
        // Step 1: Calculate direction vector from cornerThree to targetLocation
        Vector direction = targetLocation.toVector().subtract(startLocation.toVector());

        // Step 2: Calculate the distance between the two points (this will be used for scaling)
        double distance = direction.length();

        // Step 3: Normalize the direction vector to get a unit vector
        direction.normalize();

        // Step 4: Convert the Bukkit Vector to a JOML Vector3f for the transformation
        Vector3f jomlDirection = new Vector3f((float) direction.getX(), (float) direction.getY(), (float) direction.getZ());

        // Step 5: Compute the axis of rotation and angle to align the block with the direction vector
        // Assuming the block starts aligned with the Z-axis, we'll need to rotate it to align with the direction
        Quaternionf rotation = new Quaternionf().rotateTo(new Vector3f(0, 0, 1), jomlDirection);

        // Step 6: Create the transformation with the calculated rotation, translation, and scaling
        BlockDisplay blockDisplay = player.getWorld().spawn(startLocation, BlockDisplay.class);

        blockDisplay.setBlock(Material.ORANGE_TERRACOTTA.createBlockData());
        blockDisplay.setGlowColorOverride(Color.ORANGE);
        blockDisplay.setPersistent(false);
        blockDisplay.setGlowing(true);

        // Create a new transformation with the calculated values
        Transformation transformation = new Transformation(
                new Vector3f(), // no initial translation
                rotation, // rotation to align the block with the direction
                new Vector3f(0.04f, 0.04f, (float) distance), // scaling the block to stretch along the direction
                new Quaternionf() // no additional rotation
        );

        // Apply the transformation to the entity
        blockDisplay.setTransformation(transformation);

        return blockDisplay;
    }

}