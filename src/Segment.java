
import java.util.HashSet;
import java.util.Set;

public class Segment {

    private final Set<Integer> bildschirmPixel = new HashSet<>();

    public Segment(int offsetX, int offsetY) {
        erstelleSegment(offsetX, offsetY, Farbe.ROT);
    }

    private void erstelleSegment(int offsetX, int offsetY, int farbe) {
        if (offsetX >= 0 && offsetX < Spiel.BREITE - Wurm.DICKE && offsetY >= 0 && offsetY <= Spiel.HOEHE - Wurm.DICKE) {
            // Ein einfaches Quadrat pixelweise zeichnen
            for (int y = 0; y < Wurm.DICKE; y++) {
                for (int x = 0; x < Wurm.DICKE; x++) {
                    bildschirmPixel.add(offsetX + x + (offsetY + y) * Spiel.BREITE);
                    //setzePixel(offsetX + x, offsetY + y, farbe);
                }
            }
        }
    }

    // Hilfsmethode, um einen Pixel sicher zu setzen
    private void setzePixel(int x, int y, int farbe) {
        if (x >= 0 && x < Spiel.BREITE && y >= 0 && y <= Spiel.HOEHE) {
            bildschirmPixel.add(x + y * Spiel.BREITE);
            //pixel[x + y * Spiel.BREITE] = farbe;
        }
    }

    public Set<Integer> getPixel() {
        return this.bildschirmPixel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bildschirmPixel == null) ? 0 : bildschirmPixel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Segment other = (Segment) obj;
        if (bildschirmPixel == null) {
            if (other.bildschirmPixel != null) {
                return false;
            }
        } else if (!bildschirmPixel.equals(other.bildschirmPixel)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String segment = "Segment: ";
        for (Integer index : bildschirmPixel) {
            segment = segment + index + ", ";
        }
        return segment;
    }
}
