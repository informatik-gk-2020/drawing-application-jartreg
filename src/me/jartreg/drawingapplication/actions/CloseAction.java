package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;

/**
 * Die "Schließen"-Aktion, welche das Fenster schließt
 */
public class CloseAction extends AbstractAction {
    /**
     * das Hauptfenster
     */
    private final MainWindow window;

    /**
     * Erstellt eine neue "Schließen"-Aktion
     *
     * @param window
     */
    public CloseAction(MainWindow window) {
        super("Schließen");
        this.window = window;

        // Tastenkombination: Strg + W
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgeführt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Das Fensterevent WINDOW_CLOSING ist das gleiche, welches ausgelöst wird,
        // wenn auf den Schließen-Knopf des Fensterrahmens geklickt wird
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
