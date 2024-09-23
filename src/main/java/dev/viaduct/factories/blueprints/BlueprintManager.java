package dev.viaduct.factories.blueprints;

import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.utils.Chat;

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
        Chat.log("Removed blueprint progress for " + blueprintProgress.getBlueprintId() + " with id " + blueprintProgress.getId());
        this.blueprintsProgressList.remove(blueprintProgress);

        Chat.log("Blueprint progress list size: " + blueprintsProgressList.size());
        Chat.log("Blueprint progress list: " + blueprintsProgressList);
    }

}
