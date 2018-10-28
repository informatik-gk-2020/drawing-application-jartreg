package me.jartreg.drawingapplication.tools.preview;

import me.jartreg.drawingapplication.tools.Tool;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class PreviewTool extends Tool {
    private boolean drawing = false;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

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
            x1 = x2 = e.getX();
            y1 = y2 = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        if (e.getButton() == MouseEvent.BUTTON1) {
            drawing = false;
            draw(getGraphics(), x1, y1, x2, y2);
            getCanvas().repaint();
            getCanvas().markModified();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

        if (drawing) {
            x2 = e.getX();
            y2 = e.getY();
            getCanvas().repaint();
        }
    }

    @Override
    public void drawPreview(Graphics2D g) {
        if (drawing) {
            prepareGraphics(g);
            draw(g, x1, y1, x2, y2);
        }
    }

    protected abstract void draw(Graphics2D g, int x1, int y1, int x2, int y2);
}
