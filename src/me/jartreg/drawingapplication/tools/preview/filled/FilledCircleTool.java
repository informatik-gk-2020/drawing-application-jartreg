package me.jartreg.drawingapplication.tools.preview.filled;

import me.jartreg.drawingapplication.tools.preview.ColorPreviewTool;

import java.awt.*;

/**
 * Das Werkzeug für gefüllte Kreise
 */
public class FilledCircleTool extends ColorPreviewTool {
    @Override
    public String getName() {
        return "Gefüllter Kreis";
    }

    /**
     * Zeichnet einen gefüllten Kreis
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
        var xMin = Math.min(x1, x2);
        var xMax = Math.max(x1, x2);
        var yMin = Math.min(y1, y2);
        var yMax = Math.max(y1, y2);

        // den Kreis zeichnen
        g.fillOval(xMin, yMin, xMax - xMin, yMax - yMin);
    }
}
