package dev.viaduct.factories.domain.banks.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.resources.mineableresources.MineableResource;
import dev.viaduct.factories.scoreboards.impl.ResourceListing;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResourceBank extends Bank {

    public ResourceBank() {
        super();
        FactoriesPlugin.getInstance()
                .getResourceManager()
                .getResourceSet()
                .stream().filter(resource -> resource instanceof MineableResource)
                .map(resource -> (MineableResource)resource)
                .forEach(resource -> resourceMap.put(resource, 0.0));
    }

    public boolean isResourceMaterial(Material material) {
        return resourceMap.keySet().stream()
                .filter(resource -> resource instanceof MineableResource)
                .map(resource -> (MineableResource)resource)
                .anyMatch(resource -> resource.isValidMaterial(material));
    }

    public Optional<MineableResource> getResourceByMaterial(Material material) {
        return resourceMap.keySet().stream()
                .filter(resource -> resource instanceof MineableResource)
                .map(resource -> (MineableResource)resource)
                .filter(resource -> resource.isValidMaterial(material))
                .findAny();
    }

    @Override
    public ResourceListing getScoreboardData() {
        String title = "&f&lResources    ";

        List<String> resourceList = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        for (Resource resource :  resourceMap.keySet()) {
            resourceList.add(resource.getFormattedName() + resourceMap.get(resource));
            teamNames.add(resource.getName());
        }
        return new ResourceListing(title, resourceList, teamNames);
    }
}
