package me.jartreg.drawingapplication.tools.drawing;

import java.awt.*;

public class EraserTool extends DrawingTool {
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
        graphics.setComposite(AlphaComposite.Src);
    }
}
