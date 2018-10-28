package me.jartreg.drawingapplication.tools.preview;

import me.jartreg.drawingapplication.tools.ColorTool;

import java.awt.*;

public abstract class ColorPreviewTool extends PreviewTool implements ColorTool {
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
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setColor(getColor());
    }
}
