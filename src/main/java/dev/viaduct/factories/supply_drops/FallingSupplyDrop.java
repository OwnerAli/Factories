package dev.viaduct.factories.supply_drops;

import lombok.Getter;
import org.bukkit.entity.Entity;

@Getter
public class FallingSupplyDrop {

    private final String supplyDropId;
    private final Entity fallingEntity;

    public FallingSupplyDrop(String supplyDropId, Entity fallingEntity) {
        this.supplyDropId = supplyDropId;
        this.fallingEntity = fallingEntity;
    }
}
