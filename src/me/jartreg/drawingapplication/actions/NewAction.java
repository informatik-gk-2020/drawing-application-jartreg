package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.components.DrawingCanvas;
import me.jartreg.drawingapplication.components.NumberTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * Die "Neu"-Aktion
 *
 * <p>
 * Diese Aktion ertsellt eine neue Leinwand im aktuellen Fenster oder in einem neuen, wenn das aktuelle Fenster bereits
 * eine Leinwand enthält.
 * </p>
 */
public class NewAction extends AbstractAction {
    /**
     * das Hauptfenster
     */
    private final MainWindow window;

    /**
     * Erstellt eine neue "Neu"-Aktion
     *
     * @param window das Hauptfenster
     */
    public NewAction(MainWindow window) {
        super("Neu");
        this.window = window;

        // Tastenkombination: Strg + N
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgeführt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Dieses Fenster oder ein neues, wenn das aktuelle bereits eine Leinwand enthält
        var window = this.window.getCanvas() == null ? this.window : new MainWindow();

        // nach der Größe fragen
        var size = showCreateNewDialog();
        if (size == null) {
            window.dispose();
            // wenn der Benutzer den Dialog nicht bestätigt hat oder die Werte nicht konvertiert werden konnten,
            // wird abgebrochen
            return;
        }

        // Neue Leinvand erstellen und zum Fenster hinzufügen
        var canvas = DrawingCanvas.createNew(window, size.width, size.height);
        window.setCanvas(canvas);
        window.setVisible(true); // Fenster anzeigen
    }

    /**
     * Fragt den Benutzer nach der gewünschten Größe des neuen Bildes
     *
     * @return die gewünschte Größe
     */
    private Dimension showCreateNewDialog() {
        // Textfelder zum Eingeben der Größe
        var widthText = new NumberTextField("600");
        var heightText = new NumberTextField("400");

        // Dialog anzeigen
        var result = JOptionPane.showConfirmDialog(
                window,
                new Object[]{ // Inhalt des Dialogs untereinander
                        "Breite des Bildes (in Pixeln):",
                        widthText,
                        "Höhe des Bildes (in Pixeln):",
                        heightText
                },
                "Neues Bild", // Titel des Dialogs
                JOptionPane.OK_CANCEL_OPTION, // Knöpfe: OK und Abbrechen
                JOptionPane.QUESTION_MESSAGE // als Frage
        );

        // nicht bestätigt? -> abbrechen
        if (result != JOptionPane.OK_OPTION)
            return null;

        // der Text wird in Nummern konvertiert
        int width;
        int height;
        try {
            width = parseInt(widthText.getText());
            height = parseInt(heightText.getText());
        } catch (NumberFormatException e) { // wenn etwas nicht konvertiert werden konnte, wird abrebrochen
            return null;
        }

        // Wenn einer der Werte 0 ist, kann kein neues Bild erstellt werden
        if (height * width == 0) {
            // Fehler anzeigen
            JOptionPane.showMessageDialog(
                    window,
                    "Die größe des Bildes kann nicht 0 sein.",
                    "Neues Bild",
                    JOptionPane.ERROR_MESSAGE
            );

            return null; // abbrechen
        }

        // Größe zurückgeben
        return new Dimension(width, height);
    }

    /**
     * Konvertiert einen Text in eine Zahl und zeigt eine Fehlermeldung, wenn dies nicht möglich ist
     *
     * @param text der Text
     * @return die konvertierte Zahl
     * @throws NumberFormatException wenn der Text nicht in eine Zahl konvertiert werden konnte
     */
    private int parseInt(String text) {
        try {
            // konvertieren
            return Integer.parseInt(text);
        } catch (NumberFormatException e) { // Fehler bei der Konvertierung

            // Fehlermeldung anzeigen
            JOptionPane.showMessageDialog(
                    window,
                    "\"" + text + "\" ist keine gültige Zahl.",
                    "Neues Bild",
                    JOptionPane.ERROR_MESSAGE
            );

            throw e; // Fehler weitergeben
        }
    }

}
