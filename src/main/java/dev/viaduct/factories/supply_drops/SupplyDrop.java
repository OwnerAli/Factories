package dev.viaduct.factories.supply_drops;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.SupplyDropClaimEvent;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.registries.impl.SupplyDropRegistry;
import dev.viaduct.factories.supply_drops.display.DisplayProperties;
import dev.viaduct.factories.utils.Chat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Getter
public class SupplyDrop {

    private final String id;
    private final DisplayProperties displayProperties;
    private final Consumer<PlayerInteractEvent> interactEventConsumer;
    private final ItemStack[] items;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SupplyDrop(String id, DisplayProperties displayProperties, ItemStack[] items, Consumer<PlayerInteractEvent> interactEventConsumer) {
        this.id = id;
        this.displayProperties = displayProperties;
        this.items = items;
        this.interactEventConsumer = interactEventConsumer;
    }

    public SupplyDrop(String id, DisplayProperties displayProperties, ItemStack[] items) {
        this.id = id;
        this.displayProperties = displayProperties;
        this.items = items;
        this.interactEventConsumer = event -> {
            event.setCancelled(true);

            Player player = event.getPlayer();
            Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance()
                    .get(player);
            if (factoryPlayerOptional.isEmpty()) {
                Chat.log("Failed to claim supply drop: FactoryPlayer is not present");
                return;
            }

            FactoryPlayer factoryPlayer = factoryPlayerOptional.get();
            new SupplyDropClaimEvent(factoryPlayer, this, event.getClickedBlock().getLocation()).call();
        };
    }

    public void scheduleSpawn(Location location, long timeToSpawnInSeconds) {
        CompletableFuture.runAsync(() -> {
            try {
                scheduler.schedule(() -> Bukkit.getScheduler().runTask(FactoriesPlugin.getInstance(), () -> spawnFallingSupplyDrop(location)),
                        timeToSpawnInSeconds, TimeUnit.SECONDS).get();
            } catch (Exception e) {
                Chat.log("Failed to schedule supply drop spawn: " + e.getMessage());
            }
        });
    }

    public void spawnFallingSupplyDrop(Location location) {
        // Spawn the supply drop falling block 10 blocks above the location
        World world = location.getWorld();

        if (world == null) {
            Chat.log("Failed to spawn supply drop: World is null");
            return;
        }

        // Location of the falling block
        // 10 blocks above the location
        // Clone the location to avoid modifying the original location
        Location offestLocation = location.clone().add(0, 10, 0);

        FallingBlock fallingBlock = world.spawnFallingBlock(offestLocation,
                displayProperties.fallingBlockMaterial().createBlockData());
        fallingBlock.setHurtEntities(false);

        shutdownScheduler();

        FallingSupplyDrop fallingSupplyDrop = new FallingSupplyDrop(id, fallingBlock);
        SupplyDropRegistry.getInstance().addFallingSupplyDrop(fallingSupplyDrop);
    }

    public void landFallenSupplyDrop(Location location, FallingSupplyDrop fallingSupplyDrop) {
        SupplyDropRegistry.getInstance().addLandedDrop(location, fallingSupplyDrop.getSupplyDropId());
        Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(),
                () -> location.getBlock().setType(displayProperties.landingBlockMaterial()),
                1L);
    }

    public void clearSupplyDrop(Location location) {
        SupplyDropRegistry.getInstance().removeLandedDrop(location);
        location.getBlock().setType(Material.AIR);
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }

}