package dev.viaduct.factories.guis.scoreboards;

import dev.viaduct.factories.domain.banks.impl.CurrencyBank;
import dev.viaduct.factories.domain.banks.impl.ResourceBank;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.resources.Resource;
import dev.viaduct.factories.scoreboards.ScoreboardListable;
import dev.viaduct.factories.scoreboards.impl.ResourceListing;
import dev.viaduct.factories.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.util.List;

public class FactoryScoreboard {

    private final Scoreboard scoreboard;
    private final Objective objective;
    private final ResourceBank resourceBank;
    private final CurrencyBank currencyBank;

    private final int START_INDEX;
    private final int NUM_OF_LISTINGS;
    private int lastScore;

    public FactoryScoreboard(FactoryPlayer factoryPlayer) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = scoreboard.registerNewObjective("Factories", Criteria.DUMMY, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Chat.colorizeHex("#FFD700&lFactories"));

        this.resourceBank = factoryPlayer.getResourceBank();
        this.currencyBank = factoryPlayer.getCurrencyBank();

        START_INDEX = 17;
        NUM_OF_LISTINGS = 2;

        setupScoreboard();
        factoryPlayer.getPlayer().setScoreboard(scoreboard);
    }

    public void setupScoreboard() {
        //  Get ResourceListings
        ResourceListing[] listings = new ResourceListing[NUM_OF_LISTINGS];
        listings[0] = resourceBank.getScoreboardData();
        listings[1] = currencyBank.getScoreboardData();
        int index = START_INDEX;

        //  make divider for scoreboard under title
        Score divider = objective.getScore("               ");
        divider.setScore(index--); // index 15

        //  add each listing's data to the scoreboard.
        for (int i = 0; i < NUM_OF_LISTINGS; i++) {
            Score title = objective.getScore(Chat.colorizeHex(listings[i].getTitle()));
            title.setScore(index--);

            List<String> teamNames = listings[i].getTeamNames();
            List<String> lines = listings[i].getLines();

            for (int j = 0; j < listings[i].getLines().size(); j++) {
                Team team = scoreboard.registerNewTeam(teamNames.get(j));
                team.addEntry(ChatColor.values()[index] + "");
                team.setPrefix(Chat.colorize("  &f• ") + lines.get(j));
                Score score = objective.getScore(ChatColor.values()[index] + "");
                score.setScore(index);
                index--;
            }

            lastScore = index;
        }
    }

    public void addToScoreboard(ScoreboardListable scoreboardListable) {
        int originalLastScore = lastScore;

        Score divider = objective.getScore(Chat.colorize("&f               "));
        divider.setScore(lastScore);

        lastScore--;

        Score resourceTitle = objective.getScore(Chat.colorize(scoreboardListable.getTitle()));
        resourceTitle.setScore(lastScore); // index 14

        lastScore--;

        scoreboardListable.getLines().forEach(line -> {
            objective.getScore(Chat.colorizeHex(line)).setScore(lastScore);
            lastScore--;
        });

        lastScore = originalLastScore;
    }

    public void removeFromScoreboard(ScoreboardListable scoreboardListable) {
        scoreboard.resetScores(Chat.colorize(scoreboardListable.getTitle()));
        scoreboard.resetScores(Chat.colorize("&f               "));
        scoreboardListable.getLines().forEach(scoreboard::resetScores);
    }

    public void updateResourceLine(Resource resource, double amount) {
        scoreboard.getTeam(resource.getName())
                .setPrefix(Chat.colorize("  &f• ") + resource.getFormattedName() + amount);
    }

    public void removeLine(String line) {
        scoreboard.resetScores(line);
    }

}