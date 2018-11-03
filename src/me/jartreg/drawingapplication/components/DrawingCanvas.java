package me.jartreg.drawingapplication.components;

import me.jartreg.drawingapplication.MainWindow;
import me.jartreg.drawingapplication.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Die Leinwand, auf der gezeichnet wird
 */
public class DrawingCanvas extends JComponent implements PropertyChangeListener {
    /**
     * Das Hauptfenster, zu dem die Leinwand gehört
     */
    private final MainWindow mainWindow;

    /**
     * Das Bild, auf dem gezeichnet wird
     */
    private final BufferedImage image;

    /**
     * Die Größe der Leinwand
     */
    private final Dimension canvasSize;

    /**
     * Erstellt eine neue Leinwand mit einem bestimmten Bild als Inhalt
     *
     * @param mainWindow das zugehörige Fenster
     * @param image      das Bild, welches der Inhalt der Leinwand werden soll
     */
    public DrawingCanvas(MainWindow mainWindow, BufferedImage image) {
        this.mainWindow = mainWindow;
        this.image = image;

        // Die Größe der Leinwand ermitteln
        canvasSize = new Dimension(image.getWidth(), image.getHeight());

        // Darauf achten, wenn das Werkzeug geändert wurde
        mainWindow.addPropertyChangeListener(MainWindow.SELECTED_TOOL_PROPERTY, this);

        // Ein Feinauswahl-Cursor
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        // Das aktuell ausgewählte Tool nutzen
        attachTool(mainWindow.getSelectedTool());
    }

    /**
     * Erstellt eine neue Leinwand mit leerem Inhalt und einer bestimmten Größe
     *
     * @param window das zugehörige Fenster
     * @param width  die Breite der neuen Leinwand
     * @param height die Höhe der neuen Leinwand
     * @return die neue Leinwand
     */
    public static DrawingCanvas createNew(MainWindow window, int width, int height) {
        // Ein neues Bild erstellen
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Den Hintergrund des Bildes weiß füllen
        var graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        return new DrawingCanvas(window, image);
    }

    /**
     * Gibt das Bild zurück, auf dem gezeichnet wird
     *
     * @return das Bild, auf dem gezeichnet wird
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Markiert das Bild als bearbeitet
     *
     * <p>
     * Diese Methode wird von den {@link me.jartreg.drawingapplication.tools Werkzeugen} verwendet,
     * wenn sie etwas geändert haben.
     * </p>
     *
     * @see me.jartreg.drawingapplication.tools
     * @see Tool
     */
    public void markModified() {
        mainWindow.setModified(true);
    }

    /**
     * Zeichnet den Inhalt der Leinwand in das Fenster
     *
     * @param graphics das {@link Graphics2D}-Objekt, mit dem gezeichnet wird
     */
    @Override
    public void paintComponent(Graphics graphics) {
        var g = (Graphics2D) graphics;

        // Das Bild zeichnen
        g.drawImage(image, 0, 0, null);

        // Die Vorschau zeichnen
        mainWindow.getSelectedTool().drawPreview(g);
    }

    /**
     * Diese Methode wird von Java benutzt, um die bevorzugte Größe dieser Komponente zu ermitteln.
     * Da die Leinwand nur das Bild enthält, ist die bevorzugte Größe immer die Größe des Bildes.
     *
     * @return die bevorzugte Größe dieser Komponente
     */
    @Override
    public Dimension getPreferredSize() {
        return canvasSize;
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

            // Das vorherige Werkzeug soll, wenn vorhanden, von dieser Leinwand entfernt werden,
            // damit es nicht mehr zum Zeichnen benutzt werden kann
            var oldValue = (Tool) evt.getOldValue();
            if (oldValue != null) {
                removeMouseListener(oldValue);
                removeMouseMotionListener(oldValue);

                oldValue.setCanvas(null);
                oldValue.getGraphics().dispose();
                oldValue.setGraphics(null);
            }

            // Das neue Werkzeug soll, wenn vorhanden, zu dieser Leinwand hinzugefügt werden
            var newValue = (Tool) evt.getNewValue();
            if (newValue != null) {
                attachTool(newValue);
            }
        }
    }

    /**
     * Fügt ein Werkzeug zu dieser Leinwand hinzu.
     *
     * <p>
     * Dem Werkzeug wird ein {@link Graphics2D}-Objekt übergeben, womit es auf dem Bild zeichnen kann und wird und wird als
     * Mauslistener für Bewegungen und Klicks registriert, damit es auf Bewegungen der Maus achten kann.
     * </p>
     *
     * @param tool
     */
    private void attachTool(Tool tool) {
        // Listener registrieren
        addMouseListener(tool);
        addMouseMotionListener(tool);

        // Leinwand und {@link Graphics2D}-Objekt übergeben
        tool.setCanvas(this);
        tool.setGraphics(image.createGraphics());
    }
}
