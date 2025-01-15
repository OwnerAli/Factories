package dev.viaduct.factories.items.beam_items.projectiles;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import dev.viaduct.factories.items.beam_items.runnables.BeamMovementTask;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@Getter
public class BeamProjectile {

    private final BeamConfiguration config;
    private final BeamTransformer beamTransformer;
    private Display display;

    public BeamProjectile(BeamConfiguration config) {
        this.config = config;
        this.beamTransformer = config.getBeamType().getBeamTransformer();
    }

    public void fire(Player player) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        // Calculate spawn position
        Vector offset = beamTransformer.getSpawnOffset(direction);
        Location spawnLocation = eyeLocation.clone()
                .add(0, -0.2, 0)
                .add(offset);

        Material beamMaterial = config.getBeamMaterial();
        if (beamMaterial.isBlock()) {
            display = player.getWorld().spawn(spawnLocation, BlockDisplay.class, display -> {
                display.setBlock(beamMaterial.createBlockData());
                beamTransformer.transform(display);
            });
        } else if (beamMaterial.isItem()) {
            display = player.getWorld().spawn(spawnLocation, ItemDisplay.class, display -> {
                display.setItemStack(new ItemStack(beamMaterial));
                beamTransformer.transform(display);
            });
        }
        display.setRotation(eyeLocation.getYaw(), eyeLocation.getPitch());

        // Start movement
        new BeamMovementTask(player, eyeLocation, this)
                .runTaskTimerAsynchronously(FactoriesPlugin.getInstance(), 1L, 1L);
    }

}
