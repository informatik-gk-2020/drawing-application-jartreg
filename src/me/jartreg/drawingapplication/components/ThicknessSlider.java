package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.ThicknessTool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Der Breitenschieberegler, welcher benutzt wird, um die Breite des ausgewählten Werzeugs anzupassen
 */
public class ThicknessSlider extends JSlider implements PropertyChangeListener, ChangeListener {
    /**
     * Das aktuelle Tool oder <code>null</code>, wenn es keine Breiteneigenschaft hat
     */
    private ThicknessTool tool = null;

    /**
     * Erstellt einen neuen Breitenschieberegler, der die Breite des aktuellen Werkzeugs in <code>mainWindow</code> ändern kann.
     *
     * @param mainWindow das Hauptfenster, welches das Werkzeug enthält und benachrichtigt, wenn ein anderes Tool gewählt wurde
     */
    public ThicknessSlider(MainWindow mainWindow) {
        super(1, 50); // Werte von 1 bis 50 zulassen

        // darauf achten, wenn das Werkzeug gewechselt wird
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);

        setToolTipText("Breite");
        addChangeListener(this); // darauf achten, wenn der Regler verschoben wird
    }

    /**
     * Wird aufgerufen, wenn eine Eigenschaft geändert wurde
     *
     * <p>
     * Hier wird die Methode dazu genutzt um auf Änderungen des aktuellen Werkzeugs des Hauptfensters zu achten.
     * </p>
     *
     * @param evt das Ereignis
     * @see MainWindow#SELECTED_TOOL_PROPERTY
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Wenn das Werkzeug geändert wurde
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            var newValue = evt.getNewValue();

            // Zustand aktualisieren
            updateState(newValue instanceof ThicknessTool ? (ThicknessTool) newValue : null);
        }
    }

    /**
     * Aktualisiert den Zustand des Schiebereglers
     *
     * @param tool das Werkzeug, an den der Zustand angepasst werden soll oder <code>null</code>,
     *             wenn der Schieberegler deaktiviert sein soll
     */
    private void updateState(ThicknessTool tool) {
        this.tool = tool;
        setEnabled(tool != null); // aktivieren/deaktivieren
        setValue(tool != null ? (int) tool.getThickness() : 0); // Wert vom Werkzeug übernehmen
    }

    /**
     * Wird aufgerufen, wenn der Schieberegler verschoben wurde
     *
     * @param e das Ereignis
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (tool != null) { // wenn eine Auswahl möglich ist
            tool.setThickness(getValue()); // wird die neue Breite auf das Werkzeug übertragen
        }
    }
}
