package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.components.DrawingCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Tool implements MouseListener, MouseMotionListener {
    private Graphics2D graphics = null;
    private DrawingCanvas canvas = null;
    private boolean selected = false;

    protected void updateGraphics() {
        var g = getGraphics();
        if (g != null) {
            prepareGraphics(g);
        }
    }

    public abstract String getName();

    protected void prepareGraphics(Graphics2D graphics) {

    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;

        if (graphics != null) {
            prepareGraphics(graphics);
        }
    }

    public DrawingCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(DrawingCanvas canvas) {
        this.canvas = canvas;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void drawPreview(Graphics2D g) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
