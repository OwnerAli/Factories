package dev.viaduct.factories.events;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.supply_drops.SupplyDrop;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class SupplyDropClaimEvent extends FactoryPlayerEvent {

    private final SupplyDrop supplyDrop;
    private final Location location;

    public SupplyDropClaimEvent(FactoryPlayer factoryPlayer, SupplyDrop supplyDrop, Location location) {
        super(factoryPlayer);
        this.supplyDrop = supplyDrop;
        this.location = location;
    }

}
