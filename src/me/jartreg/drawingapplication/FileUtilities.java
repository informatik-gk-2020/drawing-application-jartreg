package me.jartreg.drawingapplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

/**
 * Eine Hilfeklasse zum Speichern und Öffnen von Bildern
 */
public final class FileUtilities {
    private FileUtilities() {
    }

    /**
     * Speichert ein Bild in einer Datei
     *
     * @param image das Bild
     * @param file  die Datei
     * @return ob das Speichern erfolgreich war
     */
    public static boolean save(BufferedImage image, File file) {
        boolean[] result = new boolean[]{false}; // wird enthalten, ob erfolgreich gespeichert wurde

        // Wird benutzt, um das Programm zu pausieren, wärend das Bild gespeichert wird
        SecondaryLoop eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue().createSecondaryLoop();

        // Speichert das Bild in einem anderen Thread, damit die Anwendung nicht hängt
        ForkJoinPool.commonPool().execute(() -> {
            try {
                ImageIO.write(image, "PNG", file); // speichern
                result[0] = true; // war erfolgreich
            } catch (IOException e) {
                e.printStackTrace();
            }

            eventQueue.exit(); // Setzt das Programm fort (eventQueue.enter() wird weiterlaufen)
        });

        eventQueue.enter(); // Pausiert das Programm
        // Wenn dieser Punkt erreicht wurde, wurde das Bild bereits gespeichert
        return result[0];
    }

    /**
     * Öffnet ein Bild aus einer Datei
     *
     * @param file die Datei
     * @return das Bild
     */
    public static BufferedImage open(File file) {
        BufferedImage[] result = new BufferedImage[]{null}; // Wird das Bild enthalten

        // Wird benutzt, um das Programm zu pausieren, wärend das Bild gelesen wird
        SecondaryLoop eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue().createSecondaryLoop();

        // Öffnet das Bild in einem anderen Thread, damit die Anwendung nicht hängt
        ForkJoinPool.commonPool().execute(() -> {
            try {
                result[0] = ImageIO.read(file); // lesen
            } catch (IOException e) {
                e.printStackTrace();
            }

            eventQueue.exit();
        });

        eventQueue.enter(); // Pausiert das Programm
        // Wenn dieser Punkt erreicht wurde, wurde das Bild bereits gelesen
        return result[0];
    }

    /**
     * Zeigt einen "Speichern"-Dialog an
     *
     * @param parent das Fenster, über dem der Dialog angezeugt werden soll
     * @return die ausgewählte Datei oder <code>null</code>
     */
    public static File showSaveDialog(Frame parent) {
        JFileChooser fileChooser = new JFileChooser();

        // Nur .png-Dateien werden zugelassen
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG-Dateien", "png"));

        // Wenn der Nutzer bestätigt hat...
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Wenn der Dateiname noch keine Endung enthält, wird eine angefügt
            if (!file.getName().toLowerCase().endsWith(".png"))
                file = new File(file.getAbsolutePath() + ".png");

            return file;
        }

        return null; // der Nutzer hat nicht bestätigt
    }

    /**
     * Zeigt einen "Öffnen"-Dialog an
     *
     * @param parent das Fenster, über dem der Dialog angezeugt werden soll
     * @return die ausgewählte Datei oder <code>null</code>
     */
    public static File showOpenDialog(Frame parent) {
        JFileChooser fileChooser = new JFileChooser();

        // Nur .png-Dateien werden zugelassen
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG-Dateien", "png"));

        // Wenn der Nutzer bestätigt hat...
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null; // der Nutzer hat nicht bestätigt
    }
}
