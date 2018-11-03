package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Ein Knopf, der ein Werkzeug darstellt und das Werkzeug auswählt, wenn darauf geklickt wird
 */
public class ToolSelectionButton extends JToggleButton implements PropertyChangeListener, ActionListener {
    /**
     * Das Hauptfenster
     */
    private final MainWindow mainWindow;

    /**
     * Das dargestellte Werkzeug
     */
    private final Tool tool;

    /**
     * Erstellt einen neuen Werzeugauswahlknopf
     *
     * @param mainWindow das Hauptfenster
     * @param tool       das Werkzeug
     */
    public ToolSelectionButton(MainWindow mainWindow, Tool tool) {
        this.mainWindow = mainWindow;
        this.tool = tool;
        setText(tool.getName()); // Die Aufschrift des Knopfes ist der Name des Werkzeugs

        // Darauf achten, wenn ein anderes Werkzeug ausgewählt wird
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);

        // darauf achten, wenn auf den Knopf geklickt wird
        addActionListener(this);
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
        // Wenn ein anderes Werkzeug ausgewählt wurde
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            Object newValue = evt.getNewValue();
            setSelected(tool == newValue); // diesen Knopf als ausgewählt markieren, wenn es das eigene Werkzeug ist
        }
    }

    /**
     * Wird aufgerufen, wenn auf den Knopf geklickt wurde
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.setSelectedTool(tool); // das eigene Werkzeug auswählen
    }
}
