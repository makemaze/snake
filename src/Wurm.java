
import java.util.ArrayList;
import java.util.List;

public class Wurm {

    static int DICKE = 3;

    public Richtung richtung;
    private int posX, posY;

    private List<Segment> segmente = new ArrayList<Segment>();

    private int wurmLaenge = 150;

    public Wurm() {
        this.richtung = Richtung.RECHTS;
        this.posX = 50;
        this.posY = Spiel.HOEHE / 2;

        segmente.add(erstelleWurmkopfSegment(posX, posY));
    }

    private Segment erstelleWurmkopfSegment(int x, int y) {
        Segment kopfSegment = new Segment(x, y);
        return kopfSegment;
    }

    public Segment bewegeUndErstelleWurmkopfSegment() {
       
        int neuePosX = this.posX + richtung.dx * DICKE;
        int neuePosY = this.posY + richtung.dy * DICKE;
        
        if (neuePosX > Spiel.BREITE) {
            neuePosX = 1;
        }
        if (neuePosX < 0) {
            neuePosX = Spiel.BREITE;
        }
        if (neuePosY > Spiel.HOEHE) {
            neuePosY = 1;
        }
        if (neuePosY < 0) {
            neuePosY = Spiel.HOEHE;
        }
        Segment kopfSegment = new Segment(neuePosX, neuePosY);
        if (!kreuztDerWurmSichSelbst(kopfSegment)) {
            this.posX = neuePosX;
            this.posY = neuePosY;
            segmente.add(kopfSegment);
            return kopfSegment;
        }
        return null;
    }

    public boolean kreuztDerWurmSichSelbst(Segment neuesKopfSegment) {
        return segmente.contains(neuesKopfSegment);
    }

    public void loescheLetztesSegment() {
        segmente.removeFirst();
    }

    public int getErlaubteWurmLaenge() {
        return this.wurmLaenge;
    }

    public void bewegeWurm() {

    }

    public void verlaengereWurm(int laenge) {
        wurmLaenge = wurmLaenge + laenge;
    }

    public List<Segment> getSegmente() {
        return this.segmente;
    }

    public void setzeRichtung(Richtung richtung) {
        if (richtung.gegenRichtung() != this.richtung) {
            this.richtung = richtung;
        }
        verlaengereWurm(10);
    }

}
