package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.registries.Registry;
import dev.viaduct.factories.supply_drops.FallingSupplyDrop;
import dev.viaduct.factories.supply_drops.SupplyDrop;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class SupplyDropRegistry extends Registry<String, SupplyDrop> {

    private final Map<Location, String> landedDrops;
    private final Map<Entity, FallingSupplyDrop> fallingSupplyDrops;

    public SupplyDropRegistry() {
        this.landedDrops = new HashMap<>();
        this.fallingSupplyDrops = new HashMap<>();
    }

    public Optional<SupplyDrop> getLandedDrop(Location location) {
        return Optional.ofNullable(landedDrops.get(location))
                .flatMap(this::get);
    }

    public void removeLandedDrop(Location location) {
        landedDrops.remove(location);
    }

    public void addFallingSupplyDrop(FallingSupplyDrop fallingSupplyDrop) {
        fallingSupplyDrops.put(fallingSupplyDrop.getFallingEntity(), fallingSupplyDrop);
    }

    public void removeFallingSupplyDrop(Entity entity) {
        fallingSupplyDrops.remove(entity);
    }

    public Optional<FallingSupplyDrop> getFallingSupplyDrop(Entity entity) {
        return Optional.ofNullable(fallingSupplyDrops.get(entity));
    }

    public void addLandedDrop(Location location, String supplyDropId) {
        landedDrops.put(location, supplyDropId);
    }

    //#region Lazy Initialization
    public static SupplyDropRegistry getInstance() {
        return SupplyDropRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final SupplyDropRegistry instance = new SupplyDropRegistry();
    }
    //#endregion

}
