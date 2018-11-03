package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.FileUtilities;
import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.components.DrawingCanvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Die "�ffnen"-Aktion
 *
 * <p>
 * Diese Aktion liest ein Bild aus einer Datei und �ffnet es in dem aktuellen Fenster oder einem neuen,
 * wenn das aktuelle eine Leinwand enth�lt
 * </p>
 *
 * <p>
 * Tastenkombination: <kbd>Strg</kbd> + <kbd>O</kbd>
 * </p>
 */
public class OpenAction extends AbstractAction {
    /**
     * das Hauptfenster
     */
    private final MainWindow window;

    /**
     * Erstellt eine neue "�ffnen"-Aktion
     *
     * @param window das Hauptfenster
     */
    public OpenAction(MainWindow window) {
        super("�ffnen");
        this.window = window;

        // Tastenkombination: Strg + O
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Wird aufgerufen, wenn die Aktion ausgef�hrt wird
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Nach Datei fragen
        File file = FileUtilities.showOpenDialog(window);
        if (file == null || !file.isFile()) // nichts ausgew�hlt? -> abbrechen
            return;

        // Aktuelles Fenster oder eine neues, wenn das aktuelle bereits eine Leinwand enth�lt
        MainWindow currentWindow = window.getCanvas() == null ? window : new MainWindow();
        currentWindow.setFile(file); // Datei �bergeben
        currentWindow.setEnabled(false); // Eingaben verweigern, w�hrend die Datei gelesen wird
        currentWindow.setVisible(true); // Fenster anzeigen, damit der Nutzer nicht auf das lesen der Datei warten muss

        // Datei �ffnen
        BufferedImage image = FileUtilities.open(file); // dieser Aufruf ist erst fertig, wenn die Datei gelesen wurde

        if (image == null) { // Fehler?
            // Meldung anzeigen
            JOptionPane.showMessageDialog(
                    currentWindow,
                    "Die Datei konnte nicht ge�ffnet werden.",
                    "Malprogramm",
                    JOptionPane.ERROR_MESSAGE
            );

            // Wenn ein neues Fenster erstellt wurde, wird es wieder geschlossen.
            if (currentWindow != window)
                currentWindow.dispose();
            return; // abbrechen
        }

        // Leinwand �bergeben
        currentWindow.setCanvas(new DrawingCanvas(currentWindow, image));
        currentWindow.setEnabled(true); // Lesen ist abgeschlossen -> Eingaben werden wieder akzeptiert
    }
}
