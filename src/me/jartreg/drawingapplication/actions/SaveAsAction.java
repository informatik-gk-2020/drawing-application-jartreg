package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * Die "Speichern unter"-Aktion
 *
 * <p>
 * Tastenkombination: <kbd>Strg</kbd> + <kbd>Umschalt</kbd> + <kbd>S</kbd>
 * </p>
 */
public class SaveAsAction extends FileAction {
    /**
     * Erstellt eine neue "Speichern unter"-Aktion
     *
     * @param window das Hauptfenster
     */
    public SaveAsAction(MainWindow window) {
        super("Speichern unter", window);

        // Tastenkombination: Strg + Umschalt + S
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgeführt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        getWindow().save(true); // Dialog anzeigen und speichern
    }
}
