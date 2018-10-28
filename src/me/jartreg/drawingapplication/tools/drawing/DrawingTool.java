package me.jartreg.drawingapplication.tools.drawing;

import me.jartreg.drawingapplication.tools.ThicknessTool;
import me.jartreg.drawingapplication.tools.Tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class DrawingTool extends Tool implements ThicknessTool {
    private boolean drawing = false;
    private int x;
    private int y;
    private float thickness;

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
        updateGraphics();
    }

    @Override
    protected void prepareGraphics(Graphics2D graphics) {
        super.prepareGraphics(graphics);
        graphics.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (!selected) {
            drawing = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            drawing = true;
            x = e.getX();
            y = e.getY();
            getCanvas().markModified();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            drawing = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (drawing) {
            var x1 = x;
            var y1 = y;
            x = e.getX();
            y = e.getY();

            draw(x1, y1, x, y);
            getCanvas().repaint();
        }
    }

    protected void draw(int x1, int y1, int x2, int y2) {
        getGraphics().drawLine(x1, y1, x2, y2);
    }
}
