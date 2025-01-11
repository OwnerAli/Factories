package dev.viaduct.factories.listeners;

import dev.viaduct.factories.contributions.ContributionLeaderboard;
import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.events.WCSContributionEvent;
import dev.viaduct.factories.utils.Chat;
import dev.viaduct.factories.utils.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ContributionListener implements Listener {

    @EventHandler
    public void onContribution(WCSContributionEvent event) {
        ContributionLeaderboard.getInstance()
                .updateRankingsAndGetNewFirstAsync(event.getFactoryPlayer())
                .thenAccept(factoryPlayerPairOptional -> factoryPlayerPairOptional.ifPresent(factoryPlayerFactoryPlayerPair -> {
                            FactoryPlayer newFirstPlace = factoryPlayerFactoryPlayerPair.getKey();
                            FactoryPlayer oldFirstPlace = factoryPlayerFactoryPlayerPair.getValue();
                            Bukkit.broadcastMessage(Chat.colorizeHex("#FFC107&lSun Industries &f→"));
                            Bukkit.broadcastMessage(Chat.colorize("&fWe have a new leader!"));
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage(Chat.colorize("&e" + newFirstPlace.getPlayer().getName() + " &fhas taken the lead from &e" +
                                    oldFirstPlace.getPlayer().getName() + " &fwith a total of &e" + NumberUtils.formatNumber(newFirstPlace.getBank()
                                    .getResourceAmt("WCS")) + " &fcontributed!"));
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage(Chat.colorizeHex("&7&oWe thank you for staying committed to the cause."));
                        }
                ));
    }

}
