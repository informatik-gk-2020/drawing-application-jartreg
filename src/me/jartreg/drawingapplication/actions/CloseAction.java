package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;

public class CloseAction extends AbstractAction {
    private final MainWindow window;

    public CloseAction(MainWindow window) {
        super("Schließen");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
