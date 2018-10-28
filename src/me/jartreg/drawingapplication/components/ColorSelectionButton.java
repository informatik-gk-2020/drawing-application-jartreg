package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.ColorTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ColorSelectionButton extends JButton implements PropertyChangeListener, ActionListener {
    private final MainWindow mainWindow;
    private ColorTool tool = null;

    public ColorSelectionButton(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setIcon(new ColorIcon());
        setText("Farbe wählen");
        setToolTipText("Farbe wählen");

        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);
        addActionListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            var newValue = evt.getNewValue();
            updateState(newValue instanceof ColorTool ? (ColorTool) newValue : null);
        }
    }

    private void updateState(ColorTool tool) {
        this.tool = tool;
        setEnabled(tool != null);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tool != null) {
            var newColor = JColorChooser.showDialog(SwingUtilities.getWindowAncestor(this), "Farbe wählen", tool.getColor());
            if (newColor != null) {
                tool.setColor(newColor);
                repaint();
            }
        }
    }

    private class ColorIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(tool != null ? tool.getColor() : Color.GRAY);
            g.fillRect(x, y, 16, 16);
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }
    }
}
