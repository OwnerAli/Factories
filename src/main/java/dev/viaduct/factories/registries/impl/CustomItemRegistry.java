package dev.viaduct.factories.registries.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.guis.menus.PlayerMainMenu;
import dev.viaduct.factories.items.CustomItem;
import dev.viaduct.factories.items.meta.impl.InventoryClickActionableMeta;
import dev.viaduct.factories.items.meta.impl.PlayerInteractActionableMeta;
import dev.viaduct.factories.markets.Market;
import dev.viaduct.factories.registries.Registry;
import dev.viaduct.factories.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.Optional;

public class CustomItemRegistry extends Registry<String, CustomItem> {

    public void initialize(Player joined) {
        // Menu Custom Item
        CustomItem menuItem = new CustomItem("menu",
                new InventoryClickActionableMeta(inventoryClickEvent -> {
                    inventoryClickEvent.setCancelled(true);

                    Player clickPlayer = (Player) inventoryClickEvent.getWhoClicked();
                    Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance()
                            .get(clickPlayer);

                    if (factoryPlayerOptional.isEmpty()) return;
                    clickPlayer.updateInventory();

                    if (inventoryClickEvent.getClick().isShiftClick()) {
                        new Market().showToPlayer(factoryPlayerOptional.get());
                    } else {
                        new PlayerMainMenu().showToPlayer(factoryPlayerOptional.get());
                    }
                }),
                new PlayerInteractActionableMeta(playerInteractEvent -> {
                    Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance()
                            .get(playerInteractEvent.getPlayer());

                    if (factoryPlayerOptional.isEmpty()) return;

                    if (playerInteractEvent.getPlayer().isSneaking()) {
                        new Market().showToPlayer(factoryPlayerOptional.get());
                    } else {
                        new PlayerMainMenu().showToPlayer(factoryPlayerOptional.get());
                    }
                }, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK));

        CustomItem basicLaserTool = new CustomItem("basic-laser-tool", new PlayerInteractActionableMeta(
                playerInteractEvent -> {
                    Player player = playerInteractEvent.getPlayer();

                    Location playerEyeLocation = player.getEyeLocation();
                    Vector direction = playerEyeLocation.getDirection();

                    // Calculate initial position
                    Vector rightOffset = direction.getCrossProduct(new Vector(0, 1, 0)).normalize().multiply(0.2);

                    Location spawnLocation = playerEyeLocation.clone().add(0, -0.1, 0).add(rightOffset);

                    // Spawn the block display
                    BlockDisplay blockDisplay = player.getWorld().spawn(spawnLocation, BlockDisplay.class,
                            display -> {
                                display.setBlock(Material.LIGHT_BLUE_TERRACOTTA.createBlockData());
                                Transformation transformation = display.getTransformation();
                                transformation.getScale().set(0.5);
                                display.setTransformation(transformation);
                            });
                    blockDisplay.setRotation(playerEyeLocation.getYaw(), playerEyeLocation.getPitch());

                    // Wait one tick before starting movement
                    new BukkitRunnable() {
                        private int ticks = 0;
                        private final double SPEED = 1.0;
                        private final Vector velocity = direction.clone().multiply(SPEED);

                        @Override
                        public void run() {
                            int MAX_TICKS = 40;
                            if (ticks >= MAX_TICKS || blockDisplay.isDead()) {
                                blockDisplay.remove();
                                this.cancel();
                                return;
                            }

                            // Calculate next position
                            Location nextLocation = blockDisplay.getLocation().add(velocity);

                            // Check for block collision before moving
                            checkBlockCollision(blockDisplay.getLocation(), nextLocation);

                            // If no collision, continue movement
                            blockDisplay.teleport(nextLocation);
                            blockDisplay.setTeleportDuration(1);

                            ticks++;
                        }

                        private void checkBlockCollision(Location current, Location next) {
                            if (blockDisplay.isDead()) {
                                this.cancel();
                                return;
                            }

                            // Get the vector between current and next position
                            Vector movement = next.toVector().subtract(current.toVector());
                            double distance = movement.length();
                            Vector direction = movement.normalize();

                            // Check points along the path for blocks
                            for (double d = 0; d <= distance; d += 0.1) {
                                Location checkLoc = current.clone().add(direction.clone().multiply(d));

                                // Round the location to block coordinates
                                Location blockLoc = new Location(
                                        checkLoc.getWorld(),
                                        Math.floor(checkLoc.getX()),
                                        Math.floor(checkLoc.getY()),
                                        Math.floor(checkLoc.getZ())
                                );

                                FactoryPlayerRegistry.getInstance()
                                        .get(player)
                                        .ifPresent(factoryPlayer -> factoryPlayer.getGeneratorHolder().getGenerator(blockLoc)
                                                .ifPresent(generator -> {
                                                    blockDisplay.remove();
                                                    Block block = blockLoc.getBlock();

                                                    if (block.getType().isAir()) return;
                                                    if (!block.getType().isSolid()) return;
                                                    if (!block.getType().equals(generator.getGeneratorPlaceItem().getType())) {
                                                        generator.getProgressDisplay().incrementProgress(block.getLocation(), factoryPlayer);
                                                        return;
                                                    }
                                                    generator.getBreakConsumer().accept(new BlockBreakEvent(block, player));
                                                }));
                            }
                        }

                    }.runTaskTimer(FactoriesPlugin.getInstance(), 1L, 1L);
                }, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK));

        // Add the custom item to the registry
        register(menuItem.getId(), menuItem);
        register(basicLaserTool.getId(), basicLaserTool);

        Optional<ItemStack> itemStack = menuItem.makeCustomItem(new ItemBuilder(Material.NETHER_STAR)
                .setName("&eMain Menu")
                .addLoreLines("",
                        "&fClick → Opens &eMain Menu.",
                        "&fShift-Click → Opens &eMarketplace.")
                .build());

        Optional<ItemStack> laserToolItem = basicLaserTool.makeCustomItem(new ItemBuilder(Material.DIAMOND_HOE)
                .setName("&eBasic Laser Tool")
                .addLoreLines("",
                        "&fRight-Click → To mine a block in front of you.")
                .build());

        if (itemStack.isEmpty()) return;
        if (laserToolItem.isEmpty()) return;

        PlayerInventory playerInv = joined.getInventory();

        playerInv.setItem(8, itemStack.get());
        playerInv.setItem(0, laserToolItem.get());
    }

    //#region Lazy Initialization
    public static CustomItemRegistry getInstance() {
        return CustomItemRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final CustomItemRegistry instance = new CustomItemRegistry();
    }
    //#endregion

}
