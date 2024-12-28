package dev.viaduct.factories.guis.scoreboards;

import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.scoreboards.ScoreboardListable;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.Comparator;
import java.util.List;

public class FactoryScoreboard {

    private final FactoryPlayer factoryPlayer;
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Bank bank;
    private int lastScore;

    public FactoryScoreboard(FactoryPlayer factoryPlayer) {
        this.factoryPlayer = factoryPlayer;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = scoreboard.registerNewObjective("Factories", Criteria.DUMMY, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Chat.colorizeHex("#FFD700&lFactories"));

        this.bank = factoryPlayer.getBank();

        addScoreboardLine();
        factoryPlayer.getPlayer().setScoreboard(scoreboard);
    }

    public void addScoreboardLine() {
        Score divider = objective.getScore("               ");
        divider.setScore(15); // index 15

        Score resourceTitle = objective.getScore(Chat.colorizeHex("&f&lResources    "));
        resourceTitle.setScore(14); // index 14

        // get all resources
        List<Resource> resources = bank.getResourceMap().keySet()
                .stream()
                .sorted(Comparator.comparingInt(Resource::getPriority))
                .toList();

        int index = 13;

        // iterate over resources
        for (Resource resource : resources) {
            Team team = scoreboard.registerNewTeam(resource.getName());
            team.addEntry(ChatColor.values()[index] + "");
            team.setPrefix(Chat.colorize("  &f• ") + resource.getFormattedName() + bank.getResourceAmt(resource));
            Score score = objective.getScore(ChatColor.values()[index] + "");
            score.setScore(index);
            index--;
        }

        lastScore = index;
    }

    public void addToScoreboard(ScoreboardListable scoreboardListable) {
        int originalLastScore = lastScore;

        Score divider = objective.getScore(Chat.colorize("&f               "));
        divider.setScore(lastScore);

        lastScore--;

        Score resourceTitle = objective.getScore(Chat.colorize(scoreboardListable.getSection()));
        resourceTitle.setScore(lastScore); // index 14

        lastScore--;

        scoreboardListable.getLines(factoryPlayer).forEach(line -> {
            objective.getScore(Chat.colorizeHex(line)).setScore(lastScore);
            lastScore--;
        });

        lastScore = originalLastScore;
    }

    public void removeFromScoreboard(ScoreboardListable scoreboardListable) {
        scoreboard.resetScores(Chat.colorize(scoreboardListable.getSection()));
        scoreboard.resetScores(Chat.colorize("&f               "));
        scoreboardListable.getLines(factoryPlayer).forEach(scoreboard::resetScores);
    }

    public void updateResourceLine(Resource resource) {
        scoreboard.getTeam(resource.getName())
                .setPrefix(Chat.colorize("  &f• ") + resource.getFormattedName() + bank.getResourceAmt(resource));
    }

    public void removeLine(String line) {
        scoreboard.resetScores(line);
    }

}