package dev.viaduct.factories.guis.scoreboards;

import dev.viaduct.factories.domain.banks.Bank;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.scoreboards.ScoreboardListable;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.NumberUtils;
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

        test();
        factoryPlayer.getPlayer().setScoreboard(scoreboard);
    }

    public void test() {
        int score = 20;

        objective.getScore("               ").setScore(score--);

        objective.getScore(Chat.colorize("&f&lYou")).setScore(score--);
        createUpdatingLine(Chat.colorize("  &f• WCS: "), bank.getResourceAmt("WCS"), "wcs", score--);

        objective.getScore("                  ").setScore(score--);

        objective.getScore(Chat.colorize("&f&lResources      ")).setScore(score--);
        createUpdatingLine("  &f• Wood: ", bank.getResourceAmt("wood"), "wood", score--);
        createUpdatingLine("  &f• Stone: ", bank.getResourceAmt("stone"), "stone", score--);

        objective.getScore("                    ").setScore(score--);

        lastScore = score;
    }

    public void addScoreboardLine() {
        Score divider = objective.getScore("                ");
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
        double resourceAmt = bank.getResourceAmt(resource);
        scoreboard.getTeam(resource.getName().toLowerCase())
                .setPrefix(Chat.colorize("  &f• ") + resource.getFormattedName() + NumberUtils.formatNumber(resourceAmt));
    }

    public void removeLine(String line) {
        scoreboard.resetScores(line);
    }

    private void createUpdatingLine(String line, Object value, int score) {
        Team team = scoreboard.registerNewTeam("line-" + score);
        team.addEntry(ChatColor.values()[score] + "");
        team.setPrefix(Chat.colorize("  &f• ") + line + value);
        objective.getScore(ChatColor.values()[score] + "").setScore(score);
    }

    private void createUpdatingLine(String line, Object value, String id, int score) {
        Team team = scoreboard.registerNewTeam(id);
        team.addEntry(ChatColor.values()[score] + "");
        team.setPrefix(Chat.colorize("  &f• ") + line + value);
        objective.getScore(ChatColor.values()[score] + "").setScore(score);
    }

}