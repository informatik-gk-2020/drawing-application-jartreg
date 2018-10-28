package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ToolSelectionButton extends JToggleButton implements PropertyChangeListener, ActionListener {
    private final MainWindow mainWindow;
    private final Tool tool;

    public ToolSelectionButton(MainWindow mainWindow, Tool tool) {
        this.mainWindow = mainWindow;
        this.tool = tool;
        setText(tool.getName());

        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);
        addActionListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            var newValue = evt.getNewValue();
            setSelected(tool == newValue);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.setSelectedTool(tool);
    }
}
