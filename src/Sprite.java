
import java.util.HashSet;
import java.util.Set;

public abstract class Sprite {

    private final Set<Integer> bildschirmPixel = new HashSet<>();
    protected final int offsetX, offsetY;


    public Sprite(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    protected void erstelleSprite() {
        
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
        Sprite other = (Sprite) obj;
        if (bildschirmPixel == null) {
            if (other.bildschirmPixel != null) {
                return false;
            }
        } else if (!bildschirmPixel.equals(other.bildschirmPixel)) {
            return false;
        }
        return true;
    }
}
