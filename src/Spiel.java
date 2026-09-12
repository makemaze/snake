
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Spiel extends JPanel implements Runnable, KeyListener {

    static final int BREITE = 1200;
    static final int HOEHE = 800;
    private static final int SKALIERUNG = 1; // Vergrößert das Fenster, ohne die Pixelauflösung zu ändern

    private Thread spielThread;
    private boolean laeuft = false;

    private final BufferedImage bild;
    private final WritableRaster raster;
    private final int[] pixel;

    private final Wurm wurm;

    public Spiel() {
        this.setPreferredSize(new Dimension(BREITE * SKALIERUNG, HOEHE * SKALIERUNG));

        // Bildpuffer erstellen, auf den wir direkt pixelweise zugreifen können
        bild = new BufferedImage(BREITE, HOEHE, BufferedImage.TYPE_INT_RGB);
        raster = bild.getRaster();
        //raster.setPixel(WIDTH, WIDTH,Farbe.BLAU);;
        //raster.getPixel(WIDTH, WIDTH, (int[])null);

        pixel = ((DataBufferInt) raster.getDataBuffer()).getData();

        wurm = new Wurm();
        maleSegment(wurm.getSegmente().getFirst(), Farbe.WURMFARBE);
        //maleKreis(bild);
        maleDot(raster);
    }

    private void maleSegment(Segment segment, int farbe) {
        for (Integer index : segment.getPixel()) {
            this.pixel[index] = farbe;
        }
        repaint();
    }

    private void maleDot(WritableRaster raster) {

        int[] roteFarbe = new int[]{255, 0, 0};
        // Ein Array, das die kombinierten 32-Bit ARGB-Werte hält
        //int[] pixelArray = new int[1];
        //pixelArray[0] = Color.BLUE.getRGB();
        //  raster.setPixel(WIDTH, WIDTH, pixelArray);
        List<Point> list = Dot.erzeuge(20);
        for (Point p : list) {
            raster.setPixel(p.x + 100, p.y + 100, roteFarbe);
        }
        repaint();
    }

    private void maleKreis(BufferedImage image) {
        // Graphics2D-Kontext vom Image holen
        Graphics2D g2d = image.createGraphics();

        // Optional: Schöne, glatte Kanten aktivieren (Antialiasing)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 3. Kreis zeichnen (wird direkt in das Raster geschrieben)
        g2d.setColor(Color.GREEN);
        int cx = 200, cy = 200, radius = 5;

        // drawOval/fillOval erwartet: x, y (obere linke Ecke des Rechtecks), Breite, Höhe
        g2d.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

        // 4. Ressourcen freigeben
        g2d.dispose();
    }

    public static void main(String[] args) throws Exception {
        JFrame fenster = new JFrame("Mein Pixel Spiel");
        Spiel spiel = new Spiel();
        fenster.addKeyListener(spiel);

        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.add(spiel);
        fenster.pack();
        fenster.setLocationRelativeTo(null); // Zentrieren
        fenster.setResizable(false);
        fenster.setVisible(true);

        spiel.starten();
    }

    public synchronized void starten() {
        laeuft = true;
        spielThread = new Thread(this);
        spielThread.start();
    }

    @Override
    public void run() {
        while (laeuft == true) {
            Segment kopfSegment = wurm.bewegeUndErstelleWurmkopfSegment();
            if (kopfSegment == null) {
                laeuft = false;
            } else {

                this.maleSegment(kopfSegment, Farbe.WURMFARBE);
                int aktuelleWurmLaenge = wurm.getSegmente().size();

                if (aktuelleWurmLaenge > wurm.getErlaubteWurmLaenge()) {
                    this.maleSegment(wurm.getSegmente().getFirst(), Farbe.HINTERGRUND);
                    wurm.loescheLetztesSegment();
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game over!");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Das kleine Pixelbild skaliert auf die Fenstergröße zeichnen
        g.drawImage(bild, 0, 0, BREITE * SKALIERUNG, HOEHE * SKALIERUNG, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP -> {
                this.wurm.setzeRichtung(Richtung.OBEN);
            }
            case KeyEvent.VK_DOWN -> {
                this.wurm.setzeRichtung(Richtung.UNTEN);
            }
            case KeyEvent.VK_LEFT -> {
                this.wurm.setzeRichtung(Richtung.LINKS);
            }
            case KeyEvent.VK_RIGHT -> {
                this.wurm.setzeRichtung(Richtung.RECHTS);
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
