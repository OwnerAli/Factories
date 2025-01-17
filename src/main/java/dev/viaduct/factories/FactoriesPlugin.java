package dev.viaduct.factories;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import dev.viaduct.factories.blueprints.BlueprintManager;
import dev.viaduct.factories.domain.lands.LandManager;
import dev.viaduct.factories.listeners.*;
import dev.viaduct.factories.packets.listeners.ScoreboardPacketListener;
import dev.viaduct.factories.registries.RegistryManager;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.registries.impl.GeneratorRegistry;
import dev.viaduct.factories.resources.ResourceManager;
import dev.viaduct.factories.upgrades.UpgradeManager;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.addons.GameModeAddon;
import world.bentobox.bentobox.api.addons.Pladdon;

@Getter
public class FactoriesPlugin extends Pladdon {

    private GameModeAddon addon;

    @Getter
    public static FactoriesPlugin instance;
    @Getter
    public static RegistryManager registryManager;

    public ResourceManager resourceManager;
    public UpgradeManager upgradeManager;
    public LandManager landManager;
    public BlueprintManager blueprintManager;

    @Override
    public Addon getAddon() {
        if (addon == null) {
            addon = new Factories();
        }
        return addon;
    }

    @Override
    public void onLoad() {
        super.onLoad();

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;
        initRegistries();
        registerListeners();

        PacketEvents.getAPI().getEventManager().registerListener(new ScoreboardPacketListener(),
                PacketListenerPriority.LOW);

        PacketEvents.getAPI().init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        PacketEvents.getAPI().terminate();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerGetResourceListener(registryManager
                .getRegistry(FactoryPlayerRegistry.class)), this);
        pluginManager.registerEvents(new GridLandListeners(registryManager
                .getRegistry(FactoryPlayerRegistry.class)), this);
        pluginManager.registerEvents(new GeneratorListener(registryManager
                .getRegistry(FactoryPlayerRegistry.class), registryManager
                .getRegistry(GeneratorRegistry.class)), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(registryManager
                .getRegistry(FactoryPlayerRegistry.class)), this);
        pluginManager.registerEvents(new BlueprintListeners(registryManager
                .getRegistry(BlueprintRegistry.class), blueprintManager), this);
    }

    private void initRegistries() {
        registryManager = new RegistryManager();

        registryManager.registerRegistry(FactoryPlayerRegistry.class, new FactoryPlayerRegistry());
        GeneratorRegistry registry = new GeneratorRegistry();
        registryManager.registerRegistry(GeneratorRegistry.class, registry);
        registryManager.registerRegistry(BlueprintRegistry.class, new BlueprintRegistry());

        Bukkit.getScheduler().runTaskLater(this, registry::initialize, 20L);

        resourceManager = new ResourceManager();
        resourceManager.registerResources();

        upgradeManager = new UpgradeManager();
        upgradeManager.init();

        landManager = new LandManager();
        landManager.initializeResourceCostMap();

        blueprintManager = new BlueprintManager();
    }

}
