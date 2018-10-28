package me.jartreg.drawingapplication.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LookAndFeelMenuItem extends JRadioButtonMenuItem implements ActionListener, PropertyChangeListener {
    private final UIManager.LookAndFeelInfo lookAndFeel;

    public LookAndFeelMenuItem(UIManager.LookAndFeelInfo lookAndFeel) {
        super(lookAndFeel.getName());
        this.lookAndFeel = lookAndFeel;
        updateState();
        addActionListener(this);
        UIManager.addPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(lookAndFeel.getClassName());
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("lookAndFeel")) {
            updateState();
        }
    }

    private void updateState() {
        setSelected(UIManager.getLookAndFeel().getClass().getName().equals(lookAndFeel.getClassName()));
    }
}
