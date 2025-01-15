package dev.viaduct.factories.items.beam_items.runnables;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import dev.viaduct.factories.items.beam_items.projectiles.BeamProjectile;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BeamMovementTask extends BukkitRunnable {

    private final Player shooter;
    private final BeamConfiguration config;
    private final Display display;
    private final Vector velocity;

    private int distanceTraveled;

    public BeamMovementTask(Player shooter, Location startLocation, BeamProjectile projectile) {
        this.shooter = shooter;
        this.config = projectile.getConfig();
        this.display = projectile.getDisplay();
        this.velocity = startLocation.getDirection().multiply(config.getSpeed());
    }

    @Override
    public void run() {
        if (distanceTraveled >= config.getMaxDistance() || display.isDead()) {
            Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), display::remove);
            this.cancel();
            return;
        }

        Location nextLocation = display.getLocation().add(velocity);
        checkBlockCollision(display.getLocation(), nextLocation);

        if (!display.isDead()) {
            Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), () -> {
                display.teleport(nextLocation);
                display.setTeleportDuration(1);
            });
        }

        distanceTraveled++;
    }

    private void checkBlockCollision(Location current, Location next) {
        if (display.isDead()) return;

        Vector movement = next.toVector().subtract(current.toVector());
        double distance = movement.length();
        Vector direction = movement.normalize();

        for (double d = 0; d <= distance; d += 0.1) {
            Location checkLoc = current.clone().add(direction.clone().multiply(d));
            Location blockLoc = new Location(
                    checkLoc.getWorld(),
                    Math.floor(checkLoc.getX()),
                    Math.floor(checkLoc.getY()),
                    Math.floor(checkLoc.getZ())
            );

            Block block = blockLoc.getBlock();

            FactoryPlayerRegistry.getInstance()
                    .get(shooter)
                    .ifPresentOrElse(
                            factoryPlayer -> handleFactoryPlayerCollision(block, factoryPlayer),
                            () -> handleNormalBlockCollision(block)
                    );
        }
    }

    private void handleFactoryPlayerCollision(Block block, FactoryPlayer factoryPlayer) {
        if (block.getType().isAir() || !block.getType().isSolid()) return;

        factoryPlayer.getGeneratorHolder().getGenerator(block.getLocation())
                .ifPresent(generator -> {
                    Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), display::remove);

                    if (block.getType().equals(generator.getGeneratorPlaceItem().getType())) {
                        Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), () ->
                                generator.getBreakConsumer().accept(new BlockBreakEvent(block, shooter)));
                    } else {
                        generator.getProgressDisplay().incrementProgress(block.getLocation(), factoryPlayer);
                    }
                });
    }

    private void handleNormalBlockCollision(Block block) {
        if (block.getType().isAir() || !block.getType().isSolid()) return;
        Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), display::remove);
    }

}
