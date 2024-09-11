package dev.viaduct.factories.domain.lands;

import dev.viaduct.factories.resources.ResourceCost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandManager {

    private final Map<Integer, List<ResourceCost>> resourceCostMap;

    public LandManager() {
        this.resourceCostMap = new HashMap<>();
    }

    public List<ResourceCost> getResourceCost(int distanceFromCenter) {
        return resourceCostMap.get(distanceFromCenter);
    }

    public void initializeResourceCostMap() {
        resourceCostMap.put(1, List.of(new ResourceCost("wood", 100)));
        resourceCostMap.put(2, List.of(new ResourceCost("wood", 200)));
        resourceCostMap.put(3, List.of(new ResourceCost("wood", 200), new ResourceCost("stone", 200)));
    }

}
