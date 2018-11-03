package me.jartreg.drawingapplication.tools.preview.outline;

import java.awt.*;

/**
 * Das Werkzeug für Rechtecke
 */
public class RectangleTool extends OutlinePreviewTool {
    @Override
    public String getName() {
        return "Rechteck";
    }

    /**
     * Zeichnet ein Rechteck
     *
     * @param g  das {@link Graphics2D}-Objekt
     * @param x1 der x-Wert der Startkoordinate
     * @param y1 der y-Wert der Startkoordinate
     * @param x2 der x-Wert der Endkoordinate
     * @param y2 der y-Wert der Endkoordinate
     */
    @Override
    protected void draw(Graphics2D g, int x1, int y1, int x2, int y2) {
        // Koordinaten in oben links und unten rechts aufteilen
        int xMin = Math.min(x1, x2);
        int xMax = Math.max(x1, x2);
        int yMin = Math.min(y1, y2);
        int yMax = Math.max(y1, y2);

        // Rechteck zeichnen
        g.drawRect(xMin, yMin, xMax - xMin, yMax - yMin);
    }
}
