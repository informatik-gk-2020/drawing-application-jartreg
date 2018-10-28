package me.jartreg.drawingapplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public final class FileUtilities {
    private FileUtilities() {
    }

    public static boolean save(BufferedImage image, File file) {
        var result = new boolean[]{false};
        var eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue().createSecondaryLoop();

        ForkJoinPool.commonPool().execute(() -> {
            try {
                ImageIO.write(image, "PNG", file);
                result[0] = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            eventQueue.exit();
        });

        eventQueue.enter();
        return result[0];
    }

    public static BufferedImage open(File file) {
        var result = new BufferedImage[]{null};
        var eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue().createSecondaryLoop();

        ForkJoinPool.commonPool().execute(() -> {
            try {
                result[0] = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            eventQueue.exit();
        });

        eventQueue.enter();
        return result[0];
    }

    public static File showSaveDialog(Frame parent) {
        var fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG-Dateien", "png"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".png"))
                file = new File(file.getAbsolutePath() + ".png");
            return file;
        }

        return null;
    }

    public static File showOpenDialog(Frame parent) {
        var fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG-Dateien", "png"));

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }
}
