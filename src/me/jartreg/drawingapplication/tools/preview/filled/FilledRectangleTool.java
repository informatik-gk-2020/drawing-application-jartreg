package me.jartreg.drawingapplication.tools.preview.filled;

import me.jartreg.drawingapplication.tools.preview.ColorPreviewTool;

import java.awt.*;

public class FilledRectangleTool extends ColorPreviewTool {
    @Override
    public String getName() {
        return "Gefülltes Rechteck";
    }

    @Override
    protected void draw(Graphics2D g, int x1, int y1, int x2, int y2) {
        var xMin = Math.min(x1, x2);
        var xMax = Math.max(x1, x2);
        var yMin = Math.min(y1, y2);
        var yMax = Math.max(y1, y2);

        g.fillRect(xMin, yMin, xMax - xMin, yMax - yMin);
    }
}
