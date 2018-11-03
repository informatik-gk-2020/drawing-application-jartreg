package me.jartreg.drawingapplication.components;

import javax.swing.*;

/**
 * Ein Textfeld, welches nur Nummern als Eingabe akzeptiert
 */
public class NumberTextField extends JTextField {
    /**
     * Erstellt ein neues Nummerntextfeld
     *
     * @param text der Text, der zu Beginn im Textfeld stehen soll
     */
    public NumberTextField(String text) {
        super(text);
    }

    /**
     * Wird aufgerufen, wenn der Inhalt geändert wird (d.h. neuer Text wurde eingegeben oder eine Auswahl ersetzt)
     *
     * <p>
     * Hier wird die Eingabe gefiltert, um nur Nummern zuzulassen.
     * </p>
     *
     * @param content der Text, mit dem die aktuelle Auswahl ersetzt werden soll
     */
    @Override
    public void replaceSelection(String content) {
        // Wenn der Inhalt gelöscht wird, muss nichts gefiltert werden.
        if (content == null || content.isEmpty()) {
            super.replaceSelection(content);
            return;
        }

        content = content.trim(); // Leerzeichen entfernen
        for (char c : content.toCharArray()) { // Jedes Zeichen überprüfen...
            if (!Character.isDigit(c)) // ...ob es eine Nummer ist...
                return; // ...und abbrechen wenn nicht
        }

        // an die Basisklasse weitergeben
        super.replaceSelection(content);
    }
}
