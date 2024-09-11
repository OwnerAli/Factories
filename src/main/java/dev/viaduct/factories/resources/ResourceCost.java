package dev.viaduct.factories.resources;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.utils.Chat;

import java.util.Optional;

public record ResourceCost(String resourceName, double cost) {

    @Override
    public String toString() {
        Optional<Resource> resourceOptional = FactoriesPlugin.getInstance()
                .getResourceManager()
                .getResource(resourceName);

        return resourceOptional.map(resource -> Chat.colorizeHex(resource.getFormattedName() + cost))
                .orElseGet(() -> Chat.colorize("&cUnknown Resource: " + cost));
    }
}
