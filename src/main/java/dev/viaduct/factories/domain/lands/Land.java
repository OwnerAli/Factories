package dev.viaduct.factories.domain.lands;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.lands.grids.GridSquare;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.exceptions.MaxLevelReachedException;
import dev.viaduct.factories.upgrades.Upgrade;
import dev.viaduct.factories.upgrades.UpgradeManager;
import dev.viaduct.factories.upgrades.impl.LevelledUpgrade;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Land {

    private final Island island;
    private final Location locOfCenterOfIsland;
    private final FactoryPlayer factoryPlayer;

    private final Set<GridSquare> accessibleSquares = new HashSet<>();
    private final Set<Location> purchaseSquareTexts = new HashSet<>();
    private final int squareSize = 5;

    public Land(FactoryPlayer factoryPlayer) {
        // Redo this
        this.island = BentoBox.getInstance()
                .getIslands()
                .getIsland(Bukkit.getWorld(FactoriesPlugin.getInstance()
                                .getConfig()
                                .getString("world.world-name")),
                        factoryPlayer.getPlayer().getUniqueId());
        this.locOfCenterOfIsland = island.getCenter();
        this.factoryPlayer = factoryPlayer;

        accessibleSquares.add(new GridSquare(0, 0));
    }

    // Calculate the grid square a location is in
    public GridSquare getGridSquare(Location location) {
        int relativeX = location.getBlockX() - locOfCenterOfIsland.getBlockX();
        int relativeZ = location.getBlockZ() - locOfCenterOfIsland.getBlockZ();

        // Shift the relative coordinates to ensure the center of the island is within the center of a grid
        int gridX = (int) Math.floor((double) (relativeX + squareSize / 2) / squareSize);
        int gridZ = (int) Math.floor((double) (relativeZ + squareSize / 2) / squareSize);

        return new GridSquare(gridX, gridZ);
    }

    public boolean inAccessibleSquare(Location location) {
        GridSquare square = getGridSquare(location);
        return accessibleSquares.contains(square);
    }

    public boolean purchaseAccessibleSquare(Location location) {
        GridSquare square = getGridSquare(location);
        addAccessibleSquare(square);
        return true;
    }

    public void spawnPurchaseSquareText(Location playerEyeLocation) {
        Vector playerDirectionPlusTwo = playerEyeLocation.getDirection().multiply(2).normalize();
        Location location = playerEyeLocation.add(playerDirectionPlusTwo);

        spawnTextDisplay(location);
    }

    public void addAccessibleSquare(GridSquare square) {
        accessibleSquares.add(square);
    }

    private void spawnTextDisplay(Location location) {
        if (purchaseSquareTexts.stream()
                .anyMatch(location1 -> location1.distance(location) < 3)) return;
        if (purchaseSquareTexts.contains(location)) return;

        TextDisplay display = location.getWorld().spawn(location, TextDisplay.class,
                textDisplay -> {
                    textDisplay.setPersistent(false);
                    textDisplay.setText(Chat.colorize("&e&l* Click to purchase this square *"));
                    textDisplay.setBillboard(Display.Billboard.CENTER);
                });

        purchaseSquareTexts.add(location);

        Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(), () -> {
            display.remove();
            purchaseSquareTexts.remove(location);
        }, 20 * 5);
    }

    private int getSizeInBlocks() throws MaxLevelReachedException {
        Upgrade upgrade = FactoriesPlugin.getInstance()
                .getUpgradeManager()
                .getUpgrade(UpgradeManager.UpgradeName.LAND_SIZE_UPGRADE);
        int landSizeUpgradeLevel = factoryPlayer.getLevelledUpgradeHolder()
                .getUpgradeLevel(UpgradeManager.UpgradeName.LAND_SIZE_UPGRADE);

        if (landSizeUpgradeLevel == 0) {
            return upgrade.getBaseValue();
        }
        return ((LevelledUpgrade<Integer>) upgrade).getDataForLevel(landSizeUpgradeLevel).getValue();
    }

}