package me.jartreg.drawingapplication.actions;

import me.jartreg.drawingapplication.MainWindow;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Stellt eine Menüaktion dar, die nur aktiviert ist, wenn eine Leinwand vorhanden ist.
 *
 * <p>
 * Beispiele sind alle Aktionen, die etwas mit dem Speichern der Leinwand zu tun haben, da sie eine Leinand benötigen.
 * </p>
 */
public abstract class FileAction extends AbstractAction implements PropertyChangeListener {
    /**
     * das Hauptfenster
     */
    private final MainWindow window;

    /**
     * Erstellt eine neue Dateiaktion
     *
     * @param name   der Name der Aktion
     * @param window das Hauptfenster
     */
    public FileAction(String name, MainWindow window) {
        super(name); // Namen weitergeben
        this.window = window;

        // darauf achten, wenn eine Leinwand zum Fenster hinzugefügt wird
        window.addPropertyChangeListener(this);
        setEnabled(window.getCanvas() != null); // aktivieren, wenn bereits eine vorhanden ist
    }

    /**
     * Gibt das Hauptfenster zurück
     *
     * @return das Hauptfenster
     */
    public MainWindow getWindow() {
        return window;
    }

    /**
     * Wird aufgerufen, wenn eine Eigenschaft geändert wurde
     *
     * <p>
     * Hier wird die Methode dazu genutzt um darauf zu achten, wenn eine Leinwand zum Fenster hinzugefügt wird.
     * </p>
     *
     * @param evt das Ereignis
     * @see MainWindow#CANVAS_PROPRETY
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Wenn die Leinwand geändert wurde
        if (evt.getPropertyName() == MainWindow.CANVAS_PROPRETY) {
            setEnabled(evt.getNewValue() != null); // aktivieren, wenn eine Vorhanden ist
        }
    }
}
