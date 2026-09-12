
public enum Richtung {
    RECHTS(1, 0),
    LINKS(-1, 0),
    OBEN(0, -1),
    UNTEN(0, 1);

    public final int dx, dy;

    private Richtung(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Richtung gegenRichtung() {
        switch (this) {
            case RECHTS -> {
                return LINKS;
            }
            case LINKS -> {
                return RECHTS;
            }
            case OBEN -> {
                return UNTEN;
            }
            case UNTEN -> {
                return OBEN;
            }
            default ->
                throw new AssertionError();

        }
    }
}
