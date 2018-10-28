package me.jartreg.drawingapplication.tools.preview.outline;

import java.awt.*;

public class LineTool extends OutlinePreviewTool {
    @Override
    public String getName() {
        return "Linie";
    }

    protected void draw(Graphics2D g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }
}
