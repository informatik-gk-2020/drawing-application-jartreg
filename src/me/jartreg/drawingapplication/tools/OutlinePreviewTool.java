package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.tools.ThicknessTool;
import me.jartreg.drawingapplication.tools.ColorPreviewTool;

import java.awt.*;

/**
 * Basisklasse für Vorschau-Werkzeuge, die ungefüllte Forman zeichnen und somit eine Breite als Eigenschaft haben
 *
 * <p>
 * Die ausgewählte Breite wird automatisch auf das {@link Graphics2D}-Objekt angewendet, sodass Unterklassen nichts machen müssen,
 * außer von dieser Klasse zu erben.
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

        // Da die Breite geändert wurde, muss die Einstellung auch auf das Graphics2D-Objekt übernommen werden
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setStroke(new BasicStroke(getThickness())); // Breite auf das Graphics2D-Objekt anwenden
    }
}
