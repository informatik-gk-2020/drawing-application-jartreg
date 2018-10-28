package me.jartreg.drawingapplication.tools.preview.outline;

import me.jartreg.drawingapplication.tools.ThicknessTool;
import me.jartreg.drawingapplication.tools.preview.ColorPreviewTool;

import java.awt.*;

public abstract class OutlinePreviewTool extends ColorPreviewTool implements ThicknessTool {
    private float thickness = 1;

    @Override
    public float getThickness() {
        return thickness;
    }

    @Override
    public void setThickness(float thickness) {
        this.thickness = thickness;
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setStroke(new BasicStroke(getThickness()));
    }
}
