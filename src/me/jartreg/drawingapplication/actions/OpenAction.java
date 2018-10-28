package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.FileUtilities;
import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.components.DrawingCanvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class OpenAction extends AbstractAction {
    private final MainWindow window;

    public OpenAction(MainWindow window) {
        super("Öffnen");
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var file = FileUtilities.showOpenDialog(window);
        if (file == null || !file.isFile())
            return;

        var currentWindow = window.getCanvas() == null ? window : new MainWindow();
        currentWindow.setFile(file);
        currentWindow.setEnabled(false);
        currentWindow.setVisible(true);

        var image = FileUtilities.open(file);
        if (image == null) {
            JOptionPane.showMessageDialog(
                    currentWindow,
                    "Die Datei konnte nicht geöffnet werden.",
                    "Malprogramm",
                    JOptionPane.ERROR_MESSAGE
            );

            if (currentWindow != window)
                currentWindow.dispose();
            return;
        }

        currentWindow.setCanvas(new DrawingCanvas(currentWindow, image));
        currentWindow.setEnabled(true);
    }
}
