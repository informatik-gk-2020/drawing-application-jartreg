package me.jartreg.drawingapplication.tools;

import java.awt.*;

/**
 * Das Stift-Tool
 *
 * <p>
 * Da der Stift eine Farbe hat, implementiert er {@link ColorTool}
 * </p>
 *
 * @see me.jartreg.drawingapplication.tools.ColorTool
 */
public class PenTool extends DrawingTool implements ColorTool {
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
        updateGraphics();
    }

    @Override
    public String getName() {
        return "Stift";
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);

        // Farbe auf das Graphics2D-Objekt anwenden
        graphics.setColor(getColor());
    }
}
