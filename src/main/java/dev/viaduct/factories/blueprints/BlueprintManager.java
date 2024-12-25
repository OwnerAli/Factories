package dev.viaduct.factories.blueprints;

import dev.viaduct.factories.blueprints.progress.BlueprintProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlueprintManager {

    private final List<BlueprintProgress> blueprintsProgressList;

    public BlueprintManager() {
        this.blueprintsProgressList = new ArrayList<>();
    }

    public BlueprintProgress addBlueprintProgress(Blueprint blueprint) {
        BlueprintProgress blueprintProgress = new BlueprintProgress(blueprintsProgressList.size(), blueprint,
                blueprint.getProgressablesList());
        this.blueprintsProgressList.add(blueprintProgress);

        return blueprintProgress;
    }

    public Optional<BlueprintProgress> getBlueprintProgress(int id) {
        return blueprintsProgressList.stream().filter(blueprintProgress -> blueprintProgress.getId() == id).findFirst();
    }

    public void removeBlueprintProgress(BlueprintProgress blueprintProgress) {
        this.blueprintsProgressList.remove(blueprintProgress);
    }

}
