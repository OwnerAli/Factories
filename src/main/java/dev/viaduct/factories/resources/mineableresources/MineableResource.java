package dev.viaduct.factories.resources.mineableresources;

import dev.viaduct.factories.resources.MaterialAmountPair;
import dev.viaduct.factories.resources.Resource;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class MineableResource extends Resource {

    private final List<MaterialAmountPair> materialAmountPairsList;

    protected MineableResource(String name, double incrementAmount, MaterialAmountPair... materialAmountPairs) {
        super(name, incrementAmount);
        this.materialAmountPairsList = Arrays.stream(materialAmountPairs).toList();
    }

    public boolean isValidMaterial(Material material) {
        return materialAmountPairsList.stream().map(MaterialAmountPair::material)
                .anyMatch(material1 -> material1.equals(material));
    }

    @Override
    public String getFormattedName() {
        return "";
    }
}
