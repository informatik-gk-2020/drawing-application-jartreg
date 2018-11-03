package me.jartreg.drawingapplication.tools.preview.outline;

import java.awt.*;

/**
 * Das Werkzeug für Linien
 */
public class LineTool extends OutlinePreviewTool {
    @Override
    public String getName() {
        return "Linie";
    }

    /**
     * Zeichnet eine Linie
     *
     * @param g  das {@link Graphics2D}-Objekt
     * @param x1 der x-Wert der Startkoordinate
     * @param y1 der y-Wert der Startkoordinate
     * @param x2 der x-Wert der Endkoordinate
     * @param y2 der y-Wert der Endkoordinate
     */
    protected void draw(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }
}
