package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DrawingCanvas extends JComponent implements PropertyChangeListener {
    private final MainWindow mainWindow;
    private final BufferedImage image;

    private final Dimension canvasSize;
    private final Rectangle previewClip;

    public DrawingCanvas(MainWindow mainWindow, BufferedImage image) {
        this.mainWindow = mainWindow;
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        this.image = image;
        canvasSize = new Dimension(image.getWidth(), image.getHeight());
        previewClip = new Rectangle(canvasSize);

        attachTool(mainWindow.getSelectedTool());
    }

    public static DrawingCanvas createNew(MainWindow window, int width, int height) {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        var graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        return new DrawingCanvas(window, image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void markModified() {
        mainWindow.setModified(true);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        var g = (Graphics2D) graphics;
        g.drawImage(image, 0, 0, null);

        g.clip(previewClip);
        mainWindow.getSelectedTool().drawPreview(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return canvasSize;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            var oldValue = (Tool) evt.getOldValue();
            if (oldValue != null) {
                removeMouseListener(oldValue);
                removeMouseMotionListener(oldValue);

                oldValue.setCanvas(null);
                oldValue.getGraphics().dispose();
                oldValue.setGraphics(null);
            }

            var newValue = (Tool) evt.getNewValue();
            if (newValue != null) {
                attachTool(newValue);
            }
        }
    }

    private void attachTool(Tool tool) {
        addMouseListener(tool);
        addMouseMotionListener(tool);

        tool.setCanvas(this);
        tool.setGraphics(image.createGraphics());
    }
}
