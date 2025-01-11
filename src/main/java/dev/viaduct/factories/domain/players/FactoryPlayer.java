package dev.viaduct.factories.domain.players;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.generators.Generator;
import dev.viaduct.factories.generators.GeneratorHolder;
import dev.viaduct.factories.guis.scoreboards.FactoryScoreboard;
import dev.viaduct.factories.registries.impl.CustomItemRegistry;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import dev.viaduct.factories.settings.SettingHolder;
import dev.viaduct.factories.tasks.TaskHolder;
import dev.viaduct.factories.tasks.impl.IntroTask;
import dev.viaduct.factories.upgrades.LevelledUpgradeHolder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;

@Getter
public class FactoryPlayer {

    private final Player player;
    private final Bank bank;
    private final FactoryScoreboard scoreboard;
    private final SettingHolder settingHolder;
    private final LevelledUpgradeHolder levelledUpgradeHolder;
    private final GeneratorHolder generatorHolder;
    private final TaskHolder taskHolder;

    public FactoryPlayer(Player player) {
        this.player = player;
        this.bank = new Bank();
        this.scoreboard = new FactoryScoreboard(this);
        this.settingHolder = new SettingHolder();
        this.levelledUpgradeHolder = new LevelledUpgradeHolder();
        this.generatorHolder = new GeneratorHolder();
        this.taskHolder = new TaskHolder();

        bank.addToResource("wood", scoreboard, 0);
        bank.addToResource("stone", scoreboard, 0);
        bank.addToResource("WCS", scoreboard, 0);
    }

    public void addGenerator(Location location, Generator generator) {
        generatorHolder.addGenerator(location, generator);
    }

    public void register() {
        FactoryPlayerRegistry.getInstance().register(player.getUniqueId(), this);

        BentoBox bentoBox = BentoBox.getInstance();
        User user = User.getInstance(player);
        boolean hasIsland = bentoBox.getIslandsManager()
                .hasIsland(Bukkit.getWorld("factories_world"), user);
        
        if (hasIsland) {
            setupLand();
        }

        levelledUpgradeHolder.initializeDefaultUpgrades();
        CustomItemRegistry.getInstance().initialize(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(FactoriesPlugin.getInstance(), () -> taskHolder.setup(new IntroTask(), this), 60L);
    }

    public void setupLand() {
        Bukkit.getScheduler().runTaskAsynchronously(FactoriesPlugin.getInstance(),
                () -> settingHolder.initializeDefaultPlayerSettings(this));
    }

}