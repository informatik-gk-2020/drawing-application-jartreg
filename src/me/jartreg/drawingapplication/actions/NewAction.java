package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.components.DrawingCanvas;
import me.jartreg.drawingapplication.components.NumberTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class NewAction extends AbstractAction {
    private final MainWindow window;

    public NewAction(MainWindow window) {
        super("Neu");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var window = this.window.getCanvas() == null ? this.window : new MainWindow();
        var size = showCreateNewDialog();

        if (size == null)
            return;

        var canvas = DrawingCanvas.createNew(window, size.width, size.height);
        window.setCanvas(canvas);
        window.setVisible(true);
    }

    private Dimension showCreateNewDialog() {
        var widthText = new NumberTextField("600");
        var heightText = new NumberTextField("400");

        var result = JOptionPane.showConfirmDialog(
                window,
                new Object[]{
                        "Breite des Bildes (in Pixeln):",
                        widthText,
                        "Höhe des Bildes (in Pixeln):",
                        heightText
                },
                "Neues Bild",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION)
            return null;

        int width;
        int height;
        try {
            width = parseInt(widthText.getText());
            height = parseInt(heightText.getText());
        } catch (NumberFormatException e) {
            return null;
        }

        if (height * width == 0) {
            JOptionPane.showMessageDialog(
                    window,
                    "Die größe des Bildes kann nicht 0 sein.",
                    "Neues Bild",
                    JOptionPane.ERROR_MESSAGE
            );

            return null;
        }

        return new Dimension(width, height);
    }

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    window,
                    "\"" + text + "\" ist keine gültige Zahl.",
                    "Neues Bild",
                    JOptionPane.ERROR_MESSAGE
            );

            throw e;
        }
    }

}
