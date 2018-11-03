package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.tools.ThicknessTool;
import me.jartreg.drawingapplication.tools.ColorPreviewTool;

import java.awt.*;

/**
 * Basisklasse f�r Vorschau-Werkzeuge, die ungef�llte Forman zeichnen und somit eine Breite als Eigenschaft haben
 *
 * <p>
 * Die ausgew�hlte Breite wird automatisch auf das {@link Graphics2D}-Objekt angewendet, sodass Unterklassen nichts machen m�ssen,
 * au�er von dieser Klasse zu erben.
 * </p>
 */
public abstract class OutlinePreviewTool extends ColorPreviewTool implements ThicknessTool {
    /**
     * Die aktuelle Breite
     */
    private float thickness = 1;

    @Override
    public float getThickness() {
        return thickness;
    }

    @Override
    public void setThickness(float thickness) {
        this.thickness = thickness;

        // Da die Breite ge�ndert wurde, muss die Einstellung auch auf das Graphics2D-Objekt �bernommen werden
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setStroke(new BasicStroke(getThickness())); // Breite auf das Graphics2D-Objekt anwenden
    }
}
