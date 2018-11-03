package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * Die "Speichern"-Aktion
 */
public class SaveAction extends FileAction {
    /**
     * Erstellt eine neue "Speichern"-Aktion
     *
     * @param window das Hauptfenster
     */
    public SaveAction(MainWindow window) {
        super("Speichern", window);

        // Tastenkombination: Strg + S
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgeführt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        getWindow().save(); // speichern
    }
}
