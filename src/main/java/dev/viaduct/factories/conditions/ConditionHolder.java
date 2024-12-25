package dev.viaduct.factories.conditions;

import dev.viaduct.factories.domain.players.FactoryPlayer;
import dev.viaduct.factories.registries.impl.FactoryPlayerRegistry;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConditionHolder {

    private final List<AbstractCondition> conditions;

    public ConditionHolder(AbstractCondition... conditions) {
        this.conditions = new ArrayList<>(Arrays.asList(conditions));
    }

    public ConditionHolder(List<AbstractCondition> conditions) {
        this.conditions = new ArrayList<>(conditions);
    }

    public boolean allConditionsMet(FactoryPlayer factoryPlayer) {
        return conditions.stream().allMatch(condition -> condition.isMet(factoryPlayer));
    }

    public boolean allConditionsMet(Player player) {
        Optional<FactoryPlayer> factoryPlayerOptional = FactoryPlayerRegistry.getInstance().get(player);

        return factoryPlayerOptional.filter(factoryPlayer -> conditions.stream()
                .allMatch(condition -> condition.isMet(factoryPlayer))).isPresent();
    }

    public void executeActions(FactoryPlayer factoryPlayer) {
        conditions.forEach(condition -> condition.executeActions(factoryPlayer));
    }

    public List<String> getConditionStrings() {
        List<String> conditionStrings = new ArrayList<>();
        conditions.forEach(condition -> conditionStrings.add(condition.toString()));
        return conditionStrings;
    }

    public boolean isEmpty() {
        return conditions.isEmpty();
    }

}
