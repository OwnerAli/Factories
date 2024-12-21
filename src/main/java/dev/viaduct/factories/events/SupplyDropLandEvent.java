package dev.viaduct.factories.events;

import dev.viaduct.factories.supply_drops.FallingSupplyDrop;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class SupplyDropLandEvent extends CustomEvent {

    private final Location landingLocation;
    private final FallingSupplyDrop fallingSupplyDrop;

    public SupplyDropLandEvent(Location landingLocation, FallingSupplyDrop fallingSupplyDrop) {
        this.landingLocation = landingLocation;
        this.fallingSupplyDrop = fallingSupplyDrop;
    }

}
