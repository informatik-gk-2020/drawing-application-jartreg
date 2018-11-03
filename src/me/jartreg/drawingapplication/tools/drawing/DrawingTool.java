package me.jartreg.drawingapplication.tools.drawing;

import me.jartreg.drawingapplication.tools.ThicknessTool;
import me.jartreg.drawingapplication.tools.Tool;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Basisklasse für direkt zeichnende Werkzeuge
 *
 * <p>
 * Da diese Werkzeuge immer auch eine Breite haben, implementiert diese Klasse {@link ThicknessTool}.
 * Die ausgewählte Breite wird automatisch auf das {@link Graphics2D}-Objekt angewendet.
 * </p>
 *
 * @see me.jartreg.drawingapplication.tools.ThicknessTool
 */
public abstract class DrawingTool extends Tool implements ThicknessTool {
    /**
     * Ob das Werkzeug gerade zeichnet (= Maustaste ist gedrückt)
     */
    private boolean drawing = false;

    /**
     * Der x-Wert der letzten Position der Maus
     */
    private int x;

    /**
     * Der y -Wert der letzten Position der Maus
     */
    private int y;

    /**
     * Die Breite des Werkzeugs
     */
    private float thickness;

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);

        // Breite auf das Graphics2D-Objekt anwenden
        // Hierbei wird darauf geachtet, dass die Enden der Linie rund sind
        graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (!selected) { // Wenn das Werkzeug abgewählt wurde, wird das zeichnen abgebrochen.
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

            // Position festlegen
            x = e.getX();
            y = e.getY();

            // Das Bild wird jetzt bearbeitet, also kann es als bearbeitet markiert werden
            getCanvas().markModified();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        // Linke Maustaste?
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Das Zeichnen wird beendet
            drawing = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        // wird zurzeit gezeichnet?
        if (drawing) {
            // letze Position zwischenspeichern
            var x1 = x;
            var y1 = y;

            // aktuelle Position speichern
            x = e.getX();
            y = e.getY();

            draw(x1, y1, x, y); // Linie zeichnen
            getCanvas().repaint(); // Bild neu auf das Fenster zeichnen
        }
    }

    /**
     * Zeichnet eine Linie zwischen der letzten und der aktuellen Mausposition
     *
     * <p>
     * Diese Methode kann überschrieben werden, wenn etwas anderes als eine Linie gezeichnet werden soll.
     * </p>
     *
     * @param x1 der x-Wert der letzten Position der Maus
     * @param y1 der y-Wert der letzten Position der Maus
     * @param x2 der x-Wert der aktuellen Position der Maus
     * @param y2 der y-Wert der aktuellen Position der Maus
     */
    protected void draw(int x1, int y1, int x2, int y2) {
        getGraphics().drawLine(x1, y1, x2, y2);
    }
}
