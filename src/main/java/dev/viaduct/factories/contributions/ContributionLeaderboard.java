package dev.viaduct.factories.contributions;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.utils.RankingUtil;
import world.bentobox.bentobox.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ContributionLeaderboard {

    private final List<FactoryPlayer> contributorsRankings;

    public ContributionLeaderboard() {
        this.contributorsRankings = new ArrayList<>();
    }

    public CompletableFuture<Optional<Pair<FactoryPlayer, FactoryPlayer>>> updateRankingsAndGetNewFirstAsync(FactoryPlayer lastContributor) {
        // Ensure thread safety by creating a local copy of the rankings
        List<FactoryPlayer> localRankings = new ArrayList<>(contributorsRankings);

        return CompletableFuture.supplyAsync(() -> {
            // Add the last contributor if not already present
            if (!localRankings.contains(lastContributor)) {
                localRankings.add(lastContributor);
            }

            // Store the current first-place player
            FactoryPlayer firstPlacePlayer = localRankings.get(0);

            // Sort contributors by WCS amount in descending order
            localRankings.sort(Comparator.comparingDouble(player -> -player.getBank().getResourceAmt("WCS")));

            // Get the new first-place player after sorting
            FactoryPlayer newFirstPlace = localRankings.isEmpty() ? null : localRankings.get(0);

            // Return the updated local rankings
            synchronized (contributorsRankings) {
                contributorsRankings.clear();
                contributorsRankings.addAll(localRankings);
            }

            // Return the first-place change, if any
            return (firstPlacePlayer != newFirstPlace && newFirstPlace != null)
                    ? Optional.of(new Pair<>(newFirstPlace, firstPlacePlayer))
                    : Optional.<Pair<FactoryPlayer, FactoryPlayer>>empty(); // Explicit type parameter
        }).thenApply(optionalChange -> {
            // Update the rankings on the scoreboard on the main thread
            contributorsRankings.forEach(player ->
                    player.getScoreboard().updateRanking(RankingUtil.getRanking(getRanking(player)))
            );
            return optionalChange;
        });
    }

    public int getRanking(FactoryPlayer player) {
        return contributorsRankings.contains(player) ? contributorsRankings.indexOf(player) + 1 : -1;
    }

    //#region Lazy Initialization
    public static ContributionLeaderboard getInstance() {
        return ContributionLeaderboard.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final ContributionLeaderboard instance = new ContributionLeaderboard();
    }
    //#endregion

}