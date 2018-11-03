package me.jartreg.drawingapplication;

import javax.swing.*;

/**
 * Die Einstiegsklasse der Anwendung
 */
public class Main {
    /**
     * Die Startmethode
     *
     * @param args die Kommandozeilenparameter
     */
    public static void main(String[] args) {
        // Das Aussehen ändern, damit die Anwendung wie der Rest des Systems aussieht
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Ein neues leeres Fenster erstellen und anzeigen
        var window = new MainWindow();
        window.setVisible(true);
    }
}