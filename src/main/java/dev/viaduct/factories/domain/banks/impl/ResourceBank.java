package dev.viaduct.factories.domain.banks.impl;

import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.resources.mineableresources.MineableResource;
import org.bukkit.Material;

import java.util.Optional;

public class ResourceBank extends Bank {

    public ResourceBank() { super(); }

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
}
