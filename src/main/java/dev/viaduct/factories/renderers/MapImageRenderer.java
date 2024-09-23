package dev.viaduct.factories.renderers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapImageRenderer extends MapRenderer {

    private BufferedImage image;
    private boolean done;

    public MapImageRenderer() {
        this.image = null;
        this.done = false;
    }

    public boolean load(String path) {
        BufferedImage image;

        try {
            image = ImageIO.read(getClass().getResource(path));
            image = MapPalette.resizeImage(image); // uses less memory
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.image = image;
        return true;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        // Runs every tick
        if (done) return;
        if (image == null) return;

        canvas.drawImage(0, 0, image);
        map.setTrackingPosition(false);
        done = true;
    }

    public MapView getMapView(World world) {
        MapView mapView = Bukkit.createMap(world);

        // Clear all renderers
        mapView.getRenderers().forEach(mapView::removeRenderer);
        mapView.addRenderer(this);

        return mapView;
    }

}
