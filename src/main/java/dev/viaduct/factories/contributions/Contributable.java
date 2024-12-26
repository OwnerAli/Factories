package dev.viaduct.factories.contributions;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import org.bukkit.Material;

public interface Contributable {

    double getContribution();

    String getDisplayName();

    Material getDisplayMaterial();

    void contribute(FactoryPlayer factoryPlayer, double amount);

}
