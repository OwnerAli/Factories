package dev.viaduct.factories.domain.lands.displays;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.ResourceCost;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class BuyLandDisplay {

    private final Location location;
    private final List<TextDisplay> textDisplayList;
    private Interaction interaction;

    public BuyLandDisplay(Location location, Set<BuyLandDisplay> purchaseSquareTexts, List<ResourceCost> resourceCostList, FactoryPlayer factoryPlayer) {
        this.location = location;
        this.textDisplayList = new ArrayList<>();

        if (resourceCostList == null) {
            textDisplayList.add(location.getWorld().spawn(location, TextDisplay.class,
                    textDisplay -> {
                        textDisplay.setPersistent(false);
                        textDisplay.setText(Chat.colorize("&c&l* No More Upgrades! *"));
                        textDisplay.setBillboard(Display.Billboard.CENTER);
                    }));
            Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(), () -> {
                textDisplayList.forEach(TextDisplay::remove);
                purchaseSquareTexts.remove(this);
            }, 20);
            return;
        }

        for (ResourceCost resourceCost : resourceCostList) {
            if (factoryPlayer.getBank().getResourceAmt(resourceCost.resourceName()) < resourceCost.cost()) {
                textDisplayList.add(location.getWorld().spawn(location, TextDisplay.class,
                        textDisplay -> {
                            textDisplay.setPersistent(false);
                            textDisplay.setText(Chat.colorize("&c&l* Not Enough Resources! *"));
                            textDisplay.setBillboard(Display.Billboard.CENTER);
                        }));
                spawnCostTexts(resourceCostList);
                Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(), () -> {
                    textDisplayList.forEach(TextDisplay::remove);
                    purchaseSquareTexts.remove(this);
                }, 20);
                return;
            }
        }

        textDisplayList.add(location.getWorld().spawn(location, TextDisplay.class,
                textDisplay -> {
                    textDisplay.setPersistent(false);
                    textDisplay.setText(Chat.colorize("&e&l* Right-Click to Buy *"));
                    textDisplay.setBillboard(Display.Billboard.CENTER);
                }));
        spawnCostTexts(resourceCostList);

        this.interaction = location.getWorld().spawn(location, Interaction.class,
                interactionEntity -> {
                    interactionEntity.setPersistent(false);
                    interactionEntity.setInteractionHeight(0.3f);
                    interactionEntity.setInteractionWidth(2);
                });
        Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(), () -> {
            textDisplayList.forEach(TextDisplay::remove);
            interaction.remove();
            purchaseSquareTexts.remove(this);
        }, 20 * 5);
    }

    private void spawnCostTexts(List<ResourceCost> resourceCostList) {
        double index = 1;

        for (ResourceCost resourceCost : resourceCostList) {
            TextDisplay textDisplay = location.getWorld().spawn(location.clone().subtract(0, 0.5 * index, 0), TextDisplay.class,
                    textDisplayEntity -> {
                        textDisplayEntity.setPersistent(false);
                        textDisplayEntity.setText(resourceCost.toString());
                        textDisplayEntity.setBillboard(Display.Billboard.CENTER);
                    });
            this.textDisplayList.add(textDisplay);
            index += 0.7;
        }
    }

}