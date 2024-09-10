package dev.viaduct.factories.areas;

import dev.viaduct.factories.utils.OutlineUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Set;

public class Area {

    private final Location firstCorner;
    private final Location secondCorner;
    private final OutlineUtils outlineUtils;

    @Getter
    private int amountOfMatchingBlocksInArea;

    public Area(Location firstCorner, Location secondCorner) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;

        this.outlineUtils = new OutlineUtils(firstCorner, secondCorner);
    }

    public void toggleOutline(Player player) {
        outlineUtils.toggleOutline(player);
    }

    public void updateAmountOfMatchingBlocksInArea(Set<Material> materials) {
        for (int x = firstCorner.getBlockX(); x <= secondCorner.getBlockX(); x++) {
            for (int y = firstCorner.getBlockY(); y <= secondCorner.getBlockY(); y++) {
                for (int z = firstCorner.getBlockZ(); z <= secondCorner.getBlockZ(); z++) {
                    Material materialAtLoc = firstCorner.getWorld().getBlockAt(x, y, z).getType();
                    if (materials.contains(materialAtLoc)) amountOfMatchingBlocksInArea++;
                }
            }
        }
    }

}
