package me.jartreg.drawingapplication.tools;

import java.awt.*;

/**
 * Das Radierer-Werkzeug
 */
public class EraserTool extends DrawingTool {
    /**
     * Erstellt ein neues Radierer-Werkzeug
     */
    public EraserTool() {
        setThickness(10);
    }

    @Override
    public String getName() {
        return "Radierer";
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);

        // Stellt das Graphics2D-Objekt so ein, dass es Pixel entfernt (= radiert)
        graphics.setComposite(AlphaComposite.Src);
    }
}
