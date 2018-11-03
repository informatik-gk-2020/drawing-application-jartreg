package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.ColorTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Der Farbwahlknopf des Malprogramms
 *
 * <p>
 * Der Farbwahlknopf enth�lt als Icon die aktuell ausgew�hlte Farbe und ist deaktiviert,
 * wenn das aktuelle Tool keine Farbwahl zul�sst.
 * </p>
 *
 * <p>
 * Beim Klick auf den Farbwahlknopf wird ein Farbwahldialog ge�ffnet und nach Best�tigung die Farbe des aktuellen Tool ge�ndert.
 * </p>
 */
public class ColorSelectionButton extends JButton implements PropertyChangeListener, ActionListener {
    /**
     * Das Hauptfenster, welches das Werkzeug enth�lt und benachrichtigt, wenn ein anderes Tool gew�hlt wurde
     */
    private final MainWindow mainWindow;

    /**
     * Das aktuelle Tool als {@link ColorTool} oder <code>null</code>, wenn das aktuelle Tool keine Farbwahl zul�sst.
     */
    private ColorTool tool = null;

    /**
     * Erstellt einen neuen Farbwahlknopf, welcher die Farbe des aktuellen Tools in <code>mainWindow</code> �ndern kann.
     *
     * @param mainWindow das Hauptfenster, welches das Werkzeug enth�lt und benachrichtigt, wenn ein anderes Tool gew�hlt wurde
     */
    public ColorSelectionButton(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setIcon(new ColorIcon()); // Das Farbicon
        setText("Farbe w�hlen");
        setToolTipText("Farbe w�hlen");

        // Auf �nderungen des Tool achten
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);

        // Wird ausgel�st, wenn auf den Knopf geklickt wurde
        addActionListener(this);
    }

    /**
     * Wird aufgerufen, wenn eine Eigenschaft ge�ndert wurde
     *
     * <p>
     * Hier wird die Methode dazu genutzt um auf �nderungen des aktuellen Werkzeugs des Hauptfensters zu achten.
     * </p>
     *
     * @param evt das Ereignis
     * @see MainWindow#SELECTED_TOOL_PROPERTY
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Wenn das Werkzeug ge�ndert wurde
        if (evt.getPropertyName() == MainWindow.SELECTED_TOOL_PROPERTY) {
            Object newValue = evt.getNewValue();

            // Zustand aktualisieren
            updateState(newValue instanceof ColorTool ? (ColorTool) newValue : null);
        }
    }

    /**
     * Aktualisiert den Zustand des Knopfes.
     *
     * <p>
     * Es wird folgendes gemacht:
     * </p>
     * <ul>
     * <li>Es wird �berpr�ft, ob der Knopf aktiviert sein soll.</li>
     * <li>Der Knopf wird neu gezeichnet, damit das Icon die aktuelle Farbe �bernehmen kann.</li>
     * </ul>
     *
     * @param tool das Werkzeug, an den der Zustand angepasst werden soll oder <code>null</code>,
     *             wenn der Knopf deaktiviert sein soll
     */
    private void updateState(ColorTool tool) {
        this.tool = tool;
        setEnabled(tool != null); // aktivieren/deaktivieren
        repaint(); // Icon neu zeichnen
    }

    /**
     * Wird aufgerufen, wenn auf den Knopf geklickt wurde
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (tool != null) { // Wenn eine Auswahl getroffen werden kann

            // Dialog zeigen
            Color newColor = JColorChooser.showDialog(SwingUtilities.getWindowAncestor(this), "Farbe w�hlen", tool.getColor());

            // Wenn eine neue Farbe gew�hlt wurde
            if (newColor != null) {
                tool.setColor(newColor); // Farbe an das Werkzeug weitergeben
                repaint(); // Icon neu zeichnen
            }
        }
    }

    /**
     * Das Icon, welches in einem Rechteck die aktuell ausgew�hlte Farbe oder Grau anzeigt
     */
    private class ColorIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            // Zeichne ein Rechteck mit der aktuellen Farbe oder ein graues,
            // wenn keine Auswahl getroffen werden kann
            g.setColor(tool != null ? tool.getColor() : Color.GRAY);
            g.fillRect(x, y, 16, 16);
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }
    }
}
