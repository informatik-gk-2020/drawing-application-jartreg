package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class SaveAction extends FileAction {
    public SaveAction(MainWindow window) {
        super("Speichern", window);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getWindow().save();
    }
}
