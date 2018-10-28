package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.ThicknessTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ThicknessSlider extends JSlider implements PropertyChangeListener, ChangeListener {
    private final MainWindow mainWindow;
    private ThicknessTool tool = null;

    public ThicknessSlider(MainWindow mainWindow) {
        super(1, 50);
        this.mainWindow = mainWindow;
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);

        setToolTipText("Breite");
        addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            var newValue = evt.getNewValue();
            updateState(newValue instanceof ThicknessTool ? (ThicknessTool) newValue : null);
        }
    }

    private void updateState(ThicknessTool tool) {
        this.tool = tool;
        setEnabled(tool != null);
        setValue(tool != null ? (int) tool.getThickness() : 0);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tool != null) {
            tool.setThickness(getValue());
        }
    }
}
