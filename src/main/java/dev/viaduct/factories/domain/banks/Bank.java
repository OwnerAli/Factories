package dev.viaduct.factories.domain.banks;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.scoreboards.FactoryScoreboard;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.scoreboards.impl.ResourceListing;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public abstract class Bank {

    protected final Map<Resource, Double> resourceMap;

    public Bank() {
        resourceMap = new HashMap<>();
    }

    public void addToResource(Resource resource, FactoryScoreboard factoryScoreboard, double amount) {
        resourceMap.put(resource, resourceMap.getOrDefault(resource, 0.0) + amount);
        factoryScoreboard.updateResourceLine(resource, amount);
    }

    public void addToResource(String resourceName, FactoryScoreboard factoryScoreboard, double amount) {
        resourceMap.keySet().stream()
                .filter(resource -> resource.getName().equalsIgnoreCase(resourceName))
                .findAny()
                .ifPresent(resource -> {
                    addToResource(resource, factoryScoreboard, amount);
                    factoryScoreboard.updateResourceLine(resource, amount);
                });
    }

    public void removeFromResource(Resource resource, FactoryScoreboard factoryScoreboard, double amount) {
        resourceMap.put(resource, resourceMap.getOrDefault(resource, 0.0) - amount);
        factoryScoreboard.updateResourceLine(resource, amount);
    }

    public void removeFromResource(String resourceName, FactoryPlayer factoryPlayer, double amount) {
        resourceMap.keySet().stream()
                .filter(resource -> resource.getName().equalsIgnoreCase(resourceName))
                .findAny()
                .ifPresent(resource -> {
                    removeFromResource(resource, factoryPlayer.getScoreboard(), amount);
                    factoryPlayer.getScoreboard().updateResourceLine(resource, amount);
                });
    }

    public double getResourceAmt(Resource resource) {
        System.out.println(resource.getName());
        return resourceMap.get(resource);
    }

    public double getResourceAmt(String resourceName) {
        Optional<Resource> resourceOptional = FactoriesPlugin.getInstance()
                .getResourceManager()
                .getResource(resourceName);
        if (resourceOptional.isEmpty()) return 0;
        return resourceMap.get(resourceOptional.get());
    }

    public abstract ResourceListing getScoreboardData();

}
