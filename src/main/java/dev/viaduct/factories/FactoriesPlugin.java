package dev.viaduct.factories;

import co.aikar.commands.PaperCommandManager;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import dev.viaduct.factories.blueprints.Blueprint;
import dev.viaduct.factories.blueprints.BlueprintManager;
import dev.viaduct.factories.commands.AdminCommand;
import dev.viaduct.factories.domain.lands.LandManager;
import dev.viaduct.factories.generators.Generator;
import dev.viaduct.factories.listeners.*;
import dev.viaduct.factories.packets.listeners.ScoreboardPacketListener;
import dev.viaduct.factories.registries.RegistryManager;
import dev.viaduct.factories.registries.impl.BlueprintRegistry;
import dev.viaduct.factories.registries.impl.CategoryRegistry;
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

import java.util.List;

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
        Bukkit.getScheduler().runTaskLater(this, this::registerCommands, 40L);

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
        pluginManager.registerEvents(new PlayerGetResourceListener(FactoryPlayerRegistry.getInstance()), this);
        pluginManager.registerEvents(new GridLandListeners(FactoryPlayerRegistry.getInstance()), this);
        pluginManager.registerEvents(new GeneratorListener(FactoryPlayerRegistry.getInstance(), registryManager
                .getRegistry(GeneratorRegistry.class)), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(FactoryPlayerRegistry.getInstance()), this);
        pluginManager.registerEvents(new BlueprintListeners(registryManager
                .getRegistry(BlueprintRegistry.class), blueprintManager), this);
        pluginManager.registerEvents(new CustomItemListeners(), this);
        pluginManager.registerEvents(new SupplyDropListeners(), this);
        pluginManager.registerEvents(new CraftItemListener(), this);
        pluginManager.registerEvents(new ContributionListener(), this);
    }

    private void initRegistries() {
        registryManager = new RegistryManager();

        CategoryRegistry.getInstance().initialize();

        GeneratorRegistry generatorRegistry = new GeneratorRegistry();
        registryManager.registerRegistry(GeneratorRegistry.class, generatorRegistry);

        BlueprintRegistry blueprintRegistry = BlueprintRegistry.getInstance();
        registryManager.registerRegistry(BlueprintRegistry.class, blueprintRegistry);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            generatorRegistry.initialize();
            blueprintRegistry.initialize();
        }, 20L);

        resourceManager = new ResourceManager();
        resourceManager.registerResources();

        upgradeManager = new UpgradeManager();
        upgradeManager.init();

        landManager = new LandManager();
        landManager.initializeResourceCostMap();

        blueprintManager = new BlueprintManager();
    }

    private void registerCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new AdminCommand());

        List<String> generatorIds = registryManager.getRegistry(GeneratorRegistry.class)
                .getAllValues()
                .stream()
                .map(Generator::getId)
                .toList();
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("generatorIds", c -> generatorIds);

        List<String> blueprintIds = BlueprintRegistry.getInstance().getAllValues()
                .stream()
                .map(Blueprint::getId)
                .toList();
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("blueprintIds", c -> blueprintIds);

        List<String> resourceIds = resourceManager.getResourceSet()
                .stream()
                .map(resource -> resource.getName().toLowerCase())
                .toList();
        paperCommandManager.getCommandCompletions().registerAsyncCompletion("resourceIds", c -> resourceIds);
    }

}
