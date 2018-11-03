package me.jartreg.drawingapplication.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Ein Menüeintrag, der ein {@link LookAndFeel Look and Feel} darstellt und es aktiviert, wenn darauf geklickt wird
 */
public class LookAndFeelMenuItem extends JRadioButtonMenuItem implements ActionListener, PropertyChangeListener {
    /**
     * Die Informationen über den dargestellten Look and Feel
     */
    private final UIManager.LookAndFeelInfo lookAndFeel;

    /**
     * Erstellt ein neues <code>LookAndFeelMenuItem</code>
     *
     * @param lookAndFeel der dargestellte Look and Feel
     */
    public LookAndFeelMenuItem(UIManager.LookAndFeelInfo lookAndFeel) {
        super(lookAndFeel.getName()); // Name setzen
        this.lookAndFeel = lookAndFeel;
        updateState(); // Zustand aktualisieren
        addActionListener(this); // auf Klick achten
        UIManager.addPropertyChangeListener(this); // darauf achten, wenn der aktuelle Look and Feel geändert wurde
    }

    /**
     * Wird aufgerufen, wenn auf den Menüeintrag geklickt wurde
     *
     * @param e das Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Versuchen den Look and Feel zu aktivieren
            UIManager.setLookAndFeel(lookAndFeel.getClassName());

            // Allen Fenstern mitteilen, dass der Look and Feel geändert wurde
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Wird aufgerufen, wenn eine Eigenschaft des {@link UIManager}s geändert wurde
     *
     * <p>
     * Hier wird die Methode dazu genutzt, um den Zustand des Menüeintrags zu ändern, wenn der dargestellte Look and Feel
     * ausgewählt wurde.
     * </p>
     *
     * @param evt das Ereignis
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Wenn der aktuelle Look and Feel geändert wurde
        if (evt.getPropertyName().equals("lookAndFeel")) {
            updateState(); // Zustand aktualisieren
        }
    }

    /**
     * Aktualisiert den Zustand des Menüeintrags.
     * Dabei wird der Menüeintrag als ausgewählt markiert, wenn der dargestellte Look and Feel der aktuell ausgewählte ist.
     */
    private void updateState() {
        setSelected(UIManager.getLookAndFeel().getClass().getName().equals(lookAndFeel.getClassName()));
    }
}
