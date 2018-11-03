package me.jartreg.drawingapplication.tools;

import java.awt.*;

/**
 * Basisklasse f�r Werkzeuge, die eine Vorschau zeichnen und eine Farbe haben
 *
 * <p>
 * Die ausgew�hlte Farbe wird automatisch auf das {@link Graphics2D}-Objekt angewendet, sodass Unterklassen nichts machen m�ssen,
 * au�er von dieser Klasse zu erben.
 * </p>
 *
 * @see me.jartreg.drawingapplication.tools.ColorTool
 */
public abstract class ColorPreviewTool extends PreviewTool implements ColorTool {
    /**
     * Die aktuelle Farbe
     */
    private Color color = Color.BLACK;

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;

        // Da die Farbe ge�ndert wurde, muss die Einstellung auch auf das Graphics2D-Objekt �bernommen werden
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setColor(getColor()); // Farbe auf das Graphics2D-Objekt anwenden
    }
}
