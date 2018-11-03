package me.jartreg.drawingapplication.tools;

import java.awt.*;

/**
 * Basisklasse für Werkzeuge, die eine Vorschau zeichnen und eine Farbe haben
 *
 * <p>
 * Die ausgewählte Farbe wird automatisch auf das {@link Graphics2D}-Objekt angewendet, sodass Unterklassen nichts machen müssen,
 * außer von dieser Klasse zu erben.
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

        // Da die Farbe geändert wurde, muss die Einstellung auch auf das Graphics2D-Objekt übernommen werden
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setColor(getColor()); // Farbe auf das Graphics2D-Objekt anwenden
    }
}
