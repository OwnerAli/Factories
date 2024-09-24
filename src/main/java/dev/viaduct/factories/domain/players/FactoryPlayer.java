package dev.viaduct.factories.domain.players;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.banks.impl.ResourceBank;
import dev.viaduct.factories.generators.Generator;
import dev.viaduct.factories.generators.GeneratorHolder;
import dev.viaduct.factories.guis.scoreboards.FactoryScoreboard;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.registries.impl.GeneratorRegistry;
import dev.viaduct.factories.settings.SettingHolder;
import dev.viaduct.factories.tasks.TaskHolder;
import dev.viaduct.factories.tasks.impl.IntroTask;
import dev.viaduct.factories.upgrades.LevelledUpgradeHolder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Getter
public class FactoryPlayer {

    private final Player player;
    private final ResourceBank resourceBank;
    private final FactoryScoreboard scoreboard;
    private final SettingHolder settingHolder;
    private final LevelledUpgradeHolder levelledUpgradeHolder;
    private final GeneratorHolder generatorHolder;
    private final TaskHolder taskHolder;

    public FactoryPlayer(Player player) {
        this.player = player;
        this.resourceBank = new ResourceBank();
        this.scoreboard = new FactoryScoreboard(this);
        this.settingHolder = new SettingHolder();
        this.levelledUpgradeHolder = new LevelledUpgradeHolder();
        this.generatorHolder = new GeneratorHolder();
        this.taskHolder = new TaskHolder();

        resourceBank.addToResource("wood", scoreboard, 5000);
        resourceBank.addToResource("stone", scoreboard, 5000);
    }

    public void addGenerator(Location location, Generator generator) {
        generatorHolder.addGenerator(location, generator);
    }

    public void register() {
        FactoriesPlugin.getRegistryManager()
                .getRegistry(FactoryPlayerRegistry.class)
                .register(player.getUniqueId(), this);
        levelledUpgradeHolder.initializeDefaultUpgrades();
        settingHolder.initializeDefaultPlayerSettings(this);

        FactoriesPlugin.getRegistryManager()
                .getRegistry(GeneratorRegistry.class)
                .getAllValues()
                .forEach(generator -> player.getInventory().addItem(generator.getGeneratorPlaceItem()));

        Bukkit.getScheduler().runTaskLater(FactoriesPlugin.getInstance(), () -> taskHolder.setup(new IntroTask(), this), 60L);
    }

}
