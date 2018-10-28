package me.jartreg.drawingapplication;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        var window = new MainWindow();
        window.setVisible(true);
    }
}