package dev.viaduct.factories.supply_drops.display;

import org.bukkit.Material;

public record DisplayProperties(Material fallingBlockMaterial, Material landingBlockMaterial, String landingBlockText) {

    public static DisplayProperties defaultProperties() {
        return new DisplayProperties(Material.OAK_LEAVES, Material.CHEST, "Supply Drop");
    }

    public static class DisplayPropertiesBuilder {

        private Material fallingBlockMaterial;
        private Material landingBlockMaterial;
        private String landingBlockText;

        public DisplayPropertiesBuilder fallingBlockMaterial(Material fallingBlockMaterial) {
            this.fallingBlockMaterial = fallingBlockMaterial;
            return this;
        }

        public DisplayPropertiesBuilder landingBlockMaterial(Material landingBlockMaterial) {
            this.landingBlockMaterial = landingBlockMaterial;
            return this;
        }

        public DisplayPropertiesBuilder landingBlockText(String landingBlockText) {
            this.landingBlockText = landingBlockText;
            return this;
        }

        public DisplayProperties build() {
            return new DisplayProperties(fallingBlockMaterial, landingBlockMaterial, landingBlockText);
        }

    }

}
