package dev.viaduct.factories.utils;

import dev.viaduct.factories.areas.Area;
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

public class OutlineUtils {

    private final List<Entity> displayEntitiesList;

    private final Location firstCorner;
    private final Location secondCorner;

    public OutlineUtils(Area area) {
        this.displayEntitiesList = new ArrayList<>();
        this.firstCorner = area.getFirstCorner();
        this.secondCorner = area.getSecondCorner();
    }

    public void toggleOutline(Player player) {
        if (isOutlined()) {
            clearOutline();
        } else {
            outlineRegion(player);
        }
    }

    public void outlineRegion(Player player) {
        // get the 8 vertices of the region
        Location bottomNorthWest = firstCorner; // This is the original cornerOne
        Location topSouthEast = secondCorner;  // This is the original cornerTwo

        // North-East corner (X is max, Z is max) at the bottom
        Location bottomNorthEast = new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), bottomNorthWest.getY(), topSouthEast.getZ());

        // South-East corner (X is max, Z is min) at the bottom
        Location bottomSouthEast = new Location(bottomNorthWest.getWorld(), topSouthEast.getX(), bottomNorthWest.getY(), topSouthEast.getZ());

        // South-West corner (X is min, Z is min) at the bottom
        Location bottomSouthWest = new Location(bottomNorthWest.getWorld(), topSouthEast.getX(), bottomNorthWest.getY(), bottomNorthWest.getZ());

        // North-West corner (X is min, Z is max) at the top
        Location topNorthWest = new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), topSouthEast.getY(), bottomNorthWest.getZ());

        // North-East corner (X is max, Z is max) at the top
        Location topNorthEast = new Location(bottomNorthWest.getWorld(), bottomNorthWest.getX(), topSouthEast.getY(), topSouthEast.getZ());

        // South-East corner (X is max, Z is min) at the top
        Location topSouthWest = new Location(bottomNorthWest.getWorld(), topSouthEast.getX(), topSouthEast.getY(), bottomNorthWest.getZ());

        // spawn the displays at the top corners
        BlockDisplay topNorthWestNorthEast = spawnDisplayAtLocation(player, topNorthWest, topNorthEast);
        BlockDisplay topNorthEastSouthEast = spawnDisplayAtLocation(player, topNorthEast, topSouthEast);
        BlockDisplay topSouthEastSouthWest = spawnDisplayAtLocation(player, topSouthEast, topSouthWest);
        BlockDisplay topSouthWestNorthWest = spawnDisplayAtLocation(player, topSouthWest, topNorthWest);

        // spawn the displays at the bottom corners
        BlockDisplay bottomNorthWestNorthEast = spawnDisplayAtLocation(player, bottomNorthWest, bottomNorthEast);
        BlockDisplay bottomNorthEastSouthEast = spawnDisplayAtLocation(player, bottomNorthEast, bottomSouthEast);
        BlockDisplay bottomSouthEastSouthWest = spawnDisplayAtLocation(player, bottomSouthEast, bottomSouthWest);
        BlockDisplay bottomSouthWestNorthWest = spawnDisplayAtLocation(player, bottomSouthWest, bottomNorthWest);

        // spawn the displays at the vertical edges
        BlockDisplay northWestNorthEast = spawnDisplayAtLocation(player, bottomNorthWest, topNorthWest);
        BlockDisplay northEastSouthEast = spawnDisplayAtLocation(player, bottomNorthEast, topNorthEast);
        BlockDisplay southEastSouthWest = spawnDisplayAtLocation(player, bottomSouthEast, topSouthEast);
        BlockDisplay southWestNorthWest = spawnDisplayAtLocation(player, bottomSouthWest, topSouthWest);

        displayEntitiesList.add(topNorthWestNorthEast);
        displayEntitiesList.add(topNorthEastSouthEast);
        displayEntitiesList.add(topSouthEastSouthWest);
        displayEntitiesList.add(topSouthWestNorthWest);

        displayEntitiesList.add(bottomNorthWestNorthEast);
        displayEntitiesList.add(bottomNorthEastSouthEast);
        displayEntitiesList.add(bottomSouthEastSouthWest);
        displayEntitiesList.add(bottomSouthWestNorthWest);

        displayEntitiesList.add(northWestNorthEast);
        displayEntitiesList.add(northEastSouthEast);
        displayEntitiesList.add(southEastSouthWest);
        displayEntitiesList.add(southWestNorthWest);
    }

    public void clearOutline() {
        displayEntitiesList.forEach(Entity::remove);
        displayEntitiesList.clear();
    }

    public boolean isOutlined() {
        return !displayEntitiesList.isEmpty();
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