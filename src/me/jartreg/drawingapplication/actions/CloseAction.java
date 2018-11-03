package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;

/**
 * Die "Schlie�en"-Aktion, welche das Fenster schlie�t
 *
 * <p>
 * Tastenkombination: <kbd>Strg</kbd> + <kbd>W</kbd>
 * </p>
 */
public class CloseAction extends AbstractAction {
    /**
     * das Hauptfenster
     */
    private final MainWindow window;

    /**
     * Erstellt eine neue "Schlie�en"-Aktion
     *
     * @param window das Hauptfenster
     */
    public CloseAction(MainWindow window) {
        super("Schlie�en");
        this.window = window;

        // Tastenkombination: Strg + W
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgef�hrt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Das Fensterevent WINDOW_CLOSING ist das gleiche, welches ausgel�st wird,
        // wenn auf den Schlie�en-Knopf des Fensterrahmens geklickt wird
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
