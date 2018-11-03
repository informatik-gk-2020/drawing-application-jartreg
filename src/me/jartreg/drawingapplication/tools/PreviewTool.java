package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.tools.Tool;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Basisklasse f�r alle Werkzeuge, die eine Vorschau anzeigen.
 *
 * <p>
 * Diese Werkzeuge fangen beim Dr�cken der Maustaste an, als Vorschau zu zeigen, was auf das Bild gezeichnet w�rde.
 * Sobald die Maustaste losgelassen wird, wird die Vorschau dann auch tats�chlich auf die Leinwand gezeichnet
 * </p>
 *
 * <p>
 * Unterklassen m�ssen die Methode {@link #draw(Graphics2D, int, int, int, int)} �berschreiben, da sie zuerst
 * genutzt wird, um die Vorschau zu zeichnen und sp�ter auch auf das Bild zeichnet.
 * </p>
 */
public abstract class PreviewTool extends Tool {
    /**
     * Ob das Werkzeug gerade zeichnet (= Maustaste ist gedr�ckt)
     */
    private boolean drawing = false;

    /**
     * der x-Wert der Startkoordinate
     */
    private int x1;

    /**
     * der y-Wert der Startkoordinate
     */
    private int y1;

    /**
     * Der x-Wert der Endkoordinate
     */
    private int x2;

    /**
     * Der y-Wert der Endkoordinate
     */
    private int y2;

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (!selected) { // Wenn das Werkzeug abgew�hlt wurde, wird das zeichnen abgebrochen.
            drawing = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        // Linke Maustaste?
        if (e.getButton() == MouseEvent.BUTTON1) {

            // Das Zeichnen wird begonnen
            drawing = true;

            // Startkoordinate festlegen
            x1 = x2 = e.getX();
            y1 = y2 = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        // Linke Maustaste?
        if (e.getButton() == MouseEvent.BUTTON1) {

            // Das Zeichnen wird beendet
            drawing = false;

            // auf das Bild zeichnen
            draw(getGraphics(), x1, y1, x2, y2);

            getCanvas().repaint(); // Bild neu auf das Fenster zeichnen und Vorschau entfernen
            getCanvas().markModified(); // als bearbeitet markieren
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        // wird zurzeit gezeichnet?
        if (drawing) {
            // Endkoordinate festlegen
            x2 = e.getX();
            y2 = e.getY();

            // Vorschau aktualisieren
            getCanvas().repaint();
        }
    }

    @Override
    public void drawPreview(Graphics2D g) {
        // wird zurzeit gezeichnet?
        if (drawing) {
            // das {@link Graphics2D}-Objekt f�r die Vorschau muss die gleichen Eigenchaften haben
            // wie das f�r das Bild
            prepareGraphics(g);

            // Vorschau zeichnen
            draw(g, x1, y1, x2, y2);
        }
    }

    /**
     * Zeichnet entweder die Vorschau oder in das Bild
     *
     * @param g  das {@link Graphics2D}-Objekt
     * @param x1 der x-Wert der Startkoordinate
     * @param y1 der y-Wert der Startkoordinate
     * @param x2 der x-Wert der Endkoordinate
     * @param y2 der y-Wert der Endkoordinate
     */
    protected abstract void draw(Graphics2D g, int x1, int y1, int x2, int y2);
}
