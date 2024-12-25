package dev.viaduct.factories.supply_drops.properties;

import org.bukkit.Material;
import org.bukkit.Particle;

public record Properties(Material fallingBlockMaterial, Material landingBlockMaterial, String landingBlockText,
                         Particle fallingParticle, boolean disappearOnClaim) {

    public static Properties defaultProperties() {
        return new Properties(Material.OAK_LEAVES, Material.CHEST, "Supply Drop",
                Particle.REDSTONE, true);
    }

    public static class PropertyBuilder {

        private Material fallingBlockMaterial;
        private Material landingBlockMaterial;
        private String landingBlockText;
        private boolean disappearOnClaim;

        public PropertyBuilder fallingBlockMaterial(Material fallingBlockMaterial) {
            this.fallingBlockMaterial = fallingBlockMaterial;
            return this;
        }

        public PropertyBuilder landingBlockMaterial(Material landingBlockMaterial) {
            this.landingBlockMaterial = landingBlockMaterial;
            return this;
        }

        public PropertyBuilder landingBlockText(String landingBlockText) {
            this.landingBlockText = landingBlockText;
            return this;
        }

        public Properties build() {
            return new Properties(fallingBlockMaterial, landingBlockMaterial, landingBlockText, Particle.REDSTONE, disappearOnClaim);
        }

    }

}
