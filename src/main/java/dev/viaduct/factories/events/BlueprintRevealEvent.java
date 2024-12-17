package dev.viaduct.factories.events;

import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class BlueprintRevealEvent extends FactoryPlayerEvent {

    private final BlueprintProgress blueprintProgress;

    public BlueprintRevealEvent(Player player, BlueprintProgress blueprintProgress) {
        super(player);
        this.blueprintProgress = blueprintProgress;
    }

}
