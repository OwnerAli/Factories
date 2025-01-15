package dev.viaduct.factories.items.beam_items.data;

import dev.viaduct.factories.items.beam_items.beam_types.BeamTransformer;
import dev.viaduct.factories.items.beam_items.configuration.BeamConfiguration;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BeamConfigPersistentData implements PersistentDataType<byte[], BeamConfiguration> {

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<BeamConfiguration> getComplexType() {
        return BeamConfiguration.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull BeamConfiguration complex, @NotNull PersistentDataAdapterContext context) {
        // Convert Material name to bytes
        byte[] materialBytes = complex.getBeamMaterial().name().getBytes(StandardCharsets.UTF_8);

        // Convert BeamType name to bytes
        byte[] beamTypeBytes = complex.getBeamType().name().getBytes(StandardCharsets.UTF_8);

        // Calculate total buffer size needed
        int bufferSize = 4 + materialBytes.length + // material name length + bytes
                4 + beamTypeBytes.length +  // beam type name length + bytes
                8 +  // double for speed
                4 +  // int for maxDistance
                4;   // int for shotsPerSecond

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

        // Write material
        buffer.putInt(materialBytes.length);
        buffer.put(materialBytes);

        // Write beam type
        buffer.putInt(beamTypeBytes.length);
        buffer.put(beamTypeBytes);

        // Write numeric values
        buffer.putDouble(complex.getSpeed());
        buffer.putInt(complex.getMaxDistance());
        buffer.putInt(complex.getShotsPerSecond());

        return buffer.array();
    }

    @Override
    public @NotNull BeamConfiguration fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);

        // Read material
        int materialLength = buffer.getInt();
        byte[] materialBytes = new byte[materialLength];
        buffer.get(materialBytes);
        Material material = Material.valueOf(new String(materialBytes, StandardCharsets.UTF_8));

        // Read beam type
        int beamTypeLength = buffer.getInt();
        byte[] beamTypeBytes = new byte[beamTypeLength];
        buffer.get(beamTypeBytes);
        BeamTransformer.BeamType beamType = BeamTransformer.BeamType.valueOf(new String(beamTypeBytes, StandardCharsets.UTF_8));

        // Read numeric values
        double speed = buffer.getDouble();
        int maxDistance = buffer.getInt();
        int shotsPerSecond = buffer.getInt();

        return new BeamConfiguration(beamType, material, speed, maxDistance, shotsPerSecond);
    }

}