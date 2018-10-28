package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class FileAction extends AbstractAction implements PropertyChangeListener {
    private final MainWindow window;

    public FileAction(String name, MainWindow window) {
        super(name);
        this.window = window;
        window.addPropertyChangeListener(this);
        setEnabled(window.getCanvas() != null);
    }

    public MainWindow getWindow() {
        return window;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == MainWindow.CANVAS_PROPRETY) {
            setEnabled(evt.getNewValue() != null);
        }
    }
}
