package dev.viaduct.factories.scoreboards.impl;

import dev.viaduct.factories.scoreboards.ScoreboardListable;
import lombok.Getter;

import java.util.List;

public class ResourceListing implements ScoreboardListable {

    private final String title;
    private final List<String> lines;
    @Getter
    private final List<String> teamNames;

    public ResourceListing(String title, List<String> lines, List<String> teamNames) {
        this.title = title;
        this.lines = lines;
        this.teamNames = teamNames;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }
}
