package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class SaveAsAction extends FileAction {
    public SaveAsAction(MainWindow window) {
        super("Speichern unter", window);
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getWindow().saveAs();
    }
}
