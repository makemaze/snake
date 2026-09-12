
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Dot {

    public static List<Point> erzeuge(int radius) {
        List<Point> pixelIndizes = new ArrayList<>();

        int radiusSquared = radius * radius;

        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                // Wenn der Punkt im oder auf dem Kreis liegt
                if ((x * x + y * y) <= radiusSquared) {
                    pixelIndizes.add(new Point(x, y));
                }
            }
        }
        return pixelIndizes;
    }

    public static List<Point> getAusgefuellterKreisIndizes(int cx, int cy, int radius, int width, int height) {
        List<Point> pixelIndizes = new ArrayList<>();

        // Grenzen berechnen, um nicht außerhalb des Rasters zu suchen
        int xStart = Math.max(0, cx - radius);
        int xEnd = Math.min(width - 1, cx + radius);
        int yStart = Math.max(0, cy - radius);
        int yEnd = Math.min(height - 1, cy + radius);

        int radiusSquared = radius * radius;

        for (int y = yStart; y <= yEnd; y++) {
            for (int x = xStart; x <= xEnd; x++) {
                int dx = x - cx;
                int dy = y - cy;

                // Wenn der Punkt im oder auf dem Kreis liegt
                if ((dx * dx + dy * dy) <= radiusSquared) {
                    pixelIndizes.add(new Point(x, y));
                }
            }
        }
        return pixelIndizes;
    }
}
