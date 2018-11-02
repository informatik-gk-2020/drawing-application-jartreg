package me.jartreg.drawingapplication.tools;

import java.awt.*;

/**
 * Stellt die Eigenschaft Farbe dar.
 */
public interface ColorTool {
    /**
     * Gibt die Farbe des Werkzeugs zurück
     * @return die Farbe des Werkzeugs
     */
    Color getColor();

    /**
     * Setzt die Farbe des Werkzeugs
     * @param color die neue Farbe des Werkzeugs
     */
    void setColor(Color color);
}
