package dev.viaduct.factories.resources;

import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Resource {

    private final String name;
    private final double incrementAmount;
    private final List<MaterialAmountPair> materialAmountPairsList;

    protected Resource(String name, double incrementAmount) {
        this.name = name;
        this.incrementAmount = incrementAmount;
        this.materialAmountPairsList = new ArrayList<>();
    }

    protected Resource(String name, double incrementAmount, MaterialAmountPair... materialAmountPairs) {
        this.name = name;
        this.incrementAmount = incrementAmount;
        this.materialAmountPairsList = Arrays.stream(materialAmountPairs).toList();
    }

    public boolean isValidMaterial(Material material) {
        return materialAmountPairsList.stream().map(MaterialAmountPair::material)
                .anyMatch(material1 -> material1.equals(material));
    }

    public abstract String getFormattedName();

}
