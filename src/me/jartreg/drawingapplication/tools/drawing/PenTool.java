package me.jartreg.drawingapplication.tools.drawing;

import me.jartreg.drawingapplication.tools.ColorTool;

import java.awt.*;

public class PenTool extends DrawingTool implements ColorTool {
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
        graphics.setColor(getColor());
    }
}
