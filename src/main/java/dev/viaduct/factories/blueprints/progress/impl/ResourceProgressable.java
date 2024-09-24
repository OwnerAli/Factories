package dev.viaduct.factories.blueprints.progress.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.blueprints.progress.Progressable;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResourceProgressable extends Progressable {

    private final String resourceName;

    public ResourceProgressable(double requiredProgress, String resourceName) {
        super(requiredProgress, ClickType.SHIFT_LEFT);
        this.resourceName = resourceName;
    }

    @Override
    public void progress(InventoryClickEvent event, BlueprintProgress blueprintProgress, Map<Progressable, Integer> progressMap) {
        Player player = (Player) event.getWhoClicked();
        Optional<FactoryPlayer> factoryPlayerOptional = FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .get(player);
        if (factoryPlayerOptional.isEmpty()) return;

        FactoryPlayer factoryPlayer = factoryPlayerOptional.get();

        double playerResourceAmount = factoryPlayer.getResourceBank().getResourceAmt(resourceName);

        if (playerResourceAmount < 1) return;
        Integer currentProgress = progressMap.get(this);
        if (currentProgress == null) currentProgress = 0;

        double amountNeeded = Math.min(playerResourceAmount, getRequiredProgress() - currentProgress);
        progressMap.put(this, (int) (currentProgress + amountNeeded));
        factoryPlayer.getResourceBank().removeFromResource(resourceName, factoryPlayer, amountNeeded);
        super.progress(event, blueprintProgress, progressMap);
    }

    @Override
    public List<String> getDescription(double progress) {
        return List.of(resourceName.toUpperCase() + " &f» " + progress + "/" + getRequiredProgress());
    }

}
