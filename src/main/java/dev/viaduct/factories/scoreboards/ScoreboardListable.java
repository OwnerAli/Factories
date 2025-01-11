package dev.viaduct.factories.scoreboards;

import dev.viaduct.factories.domain.players.FactoryPlayer;

import java.util.List;

public interface ScoreboardListable {

    default String getSection() {
        return "";
    }

    default int getPriority() {
        return 0;
    }

    default List<String> getLines() {
        return List.of();
    }

    default List<String> getLines(FactoryPlayer factoryPlayer) {
        return List.of();
    }

}
