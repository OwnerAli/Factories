package dev.viaduct.factories.domain.lands;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.lands.displays.BuyLandDisplay;
import dev.viaduct.factories.domain.lands.grids.impl.DynamicGrid;
import dev.viaduct.factories.domain.lands.grids.impl.StarterDynamicGrid;
import dev.viaduct.factories.domain.lands.grids.squares.GridSquare;
import dev.viaduct.factories.domain.lands.grids.squares.impl.PurchasableSquare;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.LandUnlockEvent;
import dev.viaduct.factories.resources.ResourceCost;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Land {

    private final Island island;
    private final Location locOfCenterOfIsland;
    private final FactoryPlayer factoryPlayer;

    private final DynamicGrid grid;

    private final Set<GridSquare> accessibleSquares = new HashSet<>();
    private final Set<BuyLandDisplay> purchaseSquareTexts = new HashSet<>();
    private final LandManager landManager;
    private final int squareSize = 5;

    public Land(FactoryPlayer factoryPlayer, LandManager landManager) {
        // Redo this
        this.island = BentoBox.getInstance()
                .getIslands()
                .getIsland(Bukkit.getWorld(FactoriesPlugin.getInstance()
                                .getConfig()
                                .getString("world.world-name")),
                        factoryPlayer.getPlayer().getUniqueId());
        this.landManager = landManager;
        this.locOfCenterOfIsland = island.getCenter();
        this.factoryPlayer = factoryPlayer;

        this.grid = new StarterDynamicGrid(5, 5, 5, 5, 5, locOfCenterOfIsland);
        PurchasableSquare gridSquare = grid.getGridSquare(locOfCenterOfIsland);
        gridSquare.countBlocksInSquare(locOfCenterOfIsland.getWorld(), Set.of(Material.SHORT_GRASS), locOfCenterOfIsland);
        accessibleSquares.add(gridSquare);
    }

    // Calculate the grid square a location is in
    public boolean inAccessibleSquare(Location location) {
        GridSquare gridSquare;

        try {
            gridSquare = grid.getGridSquare(location);

            return accessibleSquares.stream().anyMatch(square -> square.equals(gridSquare));
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public boolean purchaseAccessibleSquare(Location location) {
        PurchasableSquare gridSquare = grid.getGridSquare(location);

        if (accessibleSquares.contains(gridSquare)) return false;
        if (gridSquare.getResourceCostList() == null) return false;

        for (ResourceCost resourceCost : gridSquare.getResourceCostList()) {
            if (factoryPlayer.getBank().getResourceAmt(resourceCost.resourceName())
                    < resourceCost.cost()) return false;
        }

        for (ResourceCost resourceCost : gridSquare.getResourceCostList()) {
            factoryPlayer.getBank().removeFromResource(resourceCost.resourceName(),
                    factoryPlayer, resourceCost.cost());
        }
        addAccessibleSquare(gridSquare);
        LandUnlockEvent landUnlockEvent = new LandUnlockEvent(factoryPlayer, gridSquare);
        Bukkit.getPluginManager().callEvent(landUnlockEvent);

        Player player = factoryPlayer.getPlayer();

        Chat.tell(player, "&aYou have purchased a new square of land!");
        player.playSound(location, Sound.BLOCK_CHERRY_WOOD_PLACE, 1, 1);
        player.playSound(location, Sound.BLOCK_COPPER_GRATE_STEP, 1, 1);
        return true;
    }

    public void spawnPurchaseSquareText(Location playerEyeLocation) {
        Vector playerDirectionPlusTwo = playerEyeLocation.getDirection().multiply(3).normalize();
        Location location = playerEyeLocation.add(playerDirectionPlusTwo);

        spawnTextDisplay(location);
    }

    public void addAccessibleSquare(GridSquare square) {
        accessibleSquares.add(square);
    }

    public boolean hasInteractedEntity(Entity entity) {
        return purchaseSquareTexts.stream()
                .map(BuyLandDisplay::getInteraction)
                .anyMatch(interaction -> interaction.equals(entity));
    }

    private void spawnTextDisplay(Location location) {
        // Prevents the text from being too close to other text displays
        if (purchaseSquareTexts.stream()
                .map(BuyLandDisplay::getLocation)
                .anyMatch(location1 -> location1.distance(location) < 3)) return;

        // Prevents the text from spawning if it is already there
        if (purchaseSquareTexts.stream().map(BuyLandDisplay::getLocation)
                .anyMatch(loc -> loc.equals(location))) return;

        // Spawn the text display, and add it to the set of text displays

        PurchasableSquare gridSquare;
        try {
            gridSquare = grid.getGridSquare(location);
            purchaseSquareTexts.add(new BuyLandDisplay(location, purchaseSquareTexts, gridSquare.getResourceCostList(), factoryPlayer));
        } catch (IllegalArgumentException ignored) {
            purchaseSquareTexts.add(new BuyLandDisplay(location, purchaseSquareTexts, null, factoryPlayer));
        }
    }

//    public List<ResourceCost> calculateSquareCost(GridSquare square) {
//        GridSquare center = new GridSquare(0, 0, squareSize);
//
//        int distanceX = square.startX() - center.startX();
//        int distanceZ = square.startZ() - center.startZ();
//
//        // Calculate Euclidean distance from the center
//        double distanceFromCenter = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
//
//        return landManager.getResourceCost((int) distanceFromCenter);
//    }

}