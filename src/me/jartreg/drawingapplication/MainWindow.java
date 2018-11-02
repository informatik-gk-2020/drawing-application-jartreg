package me.jartreg.drawingapplication;

import me.jartreg.drawingapplication.actions.*;
import me.jartreg.drawingapplication.components.*;
import me.jartreg.drawingapplication.tools.Tool;
import me.jartreg.drawingapplication.tools.drawing.EraserTool;
import me.jartreg.drawingapplication.tools.drawing.PenTool;
import me.jartreg.drawingapplication.tools.preview.filled.FilledCircleTool;
import me.jartreg.drawingapplication.tools.preview.filled.FilledRectangleTool;
import me.jartreg.drawingapplication.tools.preview.outline.CircleTool;
import me.jartreg.drawingapplication.tools.preview.outline.LineTool;
import me.jartreg.drawingapplication.tools.preview.outline.RectangleTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Das Hauptfenster der Anwendung
 */
public class MainWindow extends JFrame {
    public static final String SELECTED_TOOL_PROPERTY = "selectedTool";
    public static final String CANVAS_PROPRETY = "canvas";
    public static final String FILE_PROPERTY = "file";

    /**
     * Die Datei, die gerade bearbeitet wird
     * <code>null</code>, wenn es ein neues Bild ist, welches noch nicht gespeichert wurde
     */
    private File file;

    /**
     * Ob das Bild seit dem letzten Speichern geändert wurde
     */
    private boolean modified = false;

    /**
     * Die Leinwandkomponente
     */
    private DrawingCanvas canvas;

    /**
     * Die Komponente, die die Leinwand enthält
     * wird benötigt, damit die Leinwand immer zentriert ist und ausgetauscht werden kann
     */
    private final JComponent canvasContainer;

    /**
     * Die Komponente, die den gesamten Inhalt des Fensters enthält
     */
    private final JComponent contentPanel;

    /**
     * Alle zur Verfügung stehenden Werkzeuge
     */
    private final Tool[] tools = {
            new PenTool(),
            new EraserTool(),
            new LineTool(),
            new RectangleTool(),
            new FilledRectangleTool(),
            new CircleTool(),
            new FilledCircleTool()
    };

    private Tool selectedTool;

    /**
     * Erstellt ein neues Hauptfenster
     */
    public MainWindow() {
        super("Malprogramm"); // Den Titel des Fensters zu Beginn festlegen
        setMinimumSize(new Dimension(600, 400)); // Die Mindestgröße
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Beim drücken auf den Schließen-Knopf soll das Fenster geschlossen werden

        // Den Inhalt des Fensters erstellen
        canvasContainer = new JPanel(new GridBagLayout());
        contentPanel = buildContentPanel(canvasContainer); // den Inhalt erstellen
        buildLayout(contentPanel); // zum Fenster hinzufügen
        pack(); // die Größe des Fensters an den Inhalt anpassen

        setSelectedTool(tools[0]); // das erste Werkzeug im Array auswählen
    }

    /**
     * Fügt den Fensterinhalt und das Menü zum Fenster hinzu und legt ihr Layout fest
     *
     * @param contentPanel die Komponente, die den Inhalt enthält
     */
    private void buildLayout(JComponent contentPanel) {
        // eine neue Komponente, die das Menü und den Inhalt enthalten wird
        var rootPanel = new JPanel(new BorderLayout());
        getContentPane().add(rootPanel); // zum Fenster hinzufügen

        // Das Menü
        var menuBar = new JMenuBar();
        buildMenu(menuBar); // die Menüeinträge hinzufügen

        // Menüleiste und Inhalt hinzufügen
        rootPanel.add(menuBar, BorderLayout.PAGE_START);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Fügt die Menüeinträge zum Menü hinzu
     *
     * @param menuBar das Menü, zu dem die Menüeinträge hinzugefügt werden sollen
     */
    private void buildMenu(JMenuBar menuBar) {
        // Das Menü "Datei"
        var fileMenu = new JMenu("Datei");
        fileMenu.add(new NewAction(this));
        fileMenu.add(new OpenAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new SaveAction(this));
        fileMenu.add(new SaveAsAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new CloseAction(this));
        menuBar.add(fileMenu); // "Datei" zum Menü hinzufügen

        // Das Menü "Ansicht"
        var viewMenu = new JMenu("Ansicht");
        var lafMenu = new JMenu("Aussehen"); // Das Untermenü "Ansicht"/"Aussehen"
        viewMenu.add(lafMenu); // "Aussehen" zu "Ansicht hinzufügen"

        // Für jedes Aussehen einen neuen Menüeintrag hinzufügen
        for (UIManager.LookAndFeelInfo lookAndFeel : UIManager.getInstalledLookAndFeels()) {
            lafMenu.add(new LookAndFeelMenuItem(lookAndFeel));
        }

        menuBar.add(viewMenu); // "Ansicht" zum Menü hinzufügen
    }

    /**
     * Erstellt den Inhalt des Fensters
     *
     * @param canvasContainer die Komponente, die die leinwand enthalten wird
     * @return die komponente, die den Inhalt des Fensters enthält
     */
    private JComponent buildContentPanel(JComponent canvasContainer) {
        var contentPanel = new JPanel(new BorderLayout()); // die Komponente erstellen

        // Hauptwerkzeugleiste erstellen und hinzufügen (oben)
        contentPanel.add(buildMainToolBar(), BorderLayout.PAGE_START);

        // Eigenschaftswerkzeugleiste erstellen und hinzufügen (unten)
        contentPanel.add(buildPropertiesToolBar(), BorderLayout.PAGE_END);

        // Eine Scrollkomponente, damit die Leinwand gescrollt werden kann, wenn das Fenster kleiner ist
        var canvasScroller = new JScrollPane(
                canvasContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        contentPanel.add(canvasScroller, BorderLayout.CENTER); // in der Mitte hinzufügen

        return contentPanel;
    }

    /**
     * Erstellt die Hauptwerkzeugleiste
     *
     * @return die Hauptwerkzeugleiste
     */
    private JToolBar buildMainToolBar() {
        // Werkzeugleiste erstellen und benennen
        var toolBar = new JToolBar();
        toolBar.setName("Werkzeuge");

        // Für jedes Werkzeug einen Eintrag hinzufügen
        for (Tool tool : tools) {
            toolBar.add(new ToolSelectionButton(this, tool));
        }

        return toolBar;
    }

    /**
     * Erstellt die Eigenschaftswerkzeugleiste
     *
     * @return die Eigenschaftswerkzeugleiste
     */
    private JToolBar buildPropertiesToolBar() {
        // Werkzeugleiste erstellen und benennen
        var toolBar = new JToolBar();
        toolBar.setName("Eigenschaften");

        // Knopf zum Auswählen der Farbe
        toolBar.add(new ColorSelectionButton(this));
        toolBar.addSeparator(); // Trennlinie

        // Der Slider für die Breite
        // Der SLider soll links neben sich einen Text "Breite:" haben, wofür eine weitere Komponente notwendig ist
        // Ein Panel mit einem horizontalen Abstand von 8 Pixeln zwischen den Komponenten
        JPanel sliderPanel = new JPanel(new BorderLayout(8, 0));
        sliderPanel.add(new ThicknessSlider(this)); // Slider hinzufügen
        sliderPanel.add(new JLabel("Breite:"), BorderLayout.LINE_START); // Text links hinzufügen
        toolBar.add(sliderPanel); // zur Werkzeugleiste hinzufügen

        return toolBar;
    }

    /**
     * Erneuert den Titel des Fensters
     * <p>
     * Die Methode wird aufgerufen, wenn sich der Name der Datei geändert hat, das Bild ungespeicherte Änderungen enthält
     * oder gespeichert wurde
     * </p>
     */
    private void updateTitle() {
        setTitle(
                (file == null ? "Unbenannt" : file.getName()) // Name der Datei
                        + (isModified() ? "*" : "") // "*" wenn das Bild nicht gespeicherte Änderungen enthält
                        + " - Malprogramm" // Der Name der Anwendung
        );
    }

    /**
     * Bearbeitet ereignisse, die an das Fenster gesendet werden
     * <p>
     * Die Methode wird überschrieben, um den Benutzer beim schließen des Fensters zu fragen, ob er das Bild speicher will
     * </p>
     * @param e das Ereignis
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        // WINDOW_CLOSING wird ausgelöst, wenn suf den SChließen-Knopf geklickt wurde
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (!isEnabled()) { // Wenn das Fenster gerade keine Eingabe zulässt, wird es ignoriert
                return;
            }

            // Wenn im Fenster ein Bild vorhanden ist und es nicht gespeicherte Änderungen enthält
            if (canvas != null && isModified()) {
                // Benutzer fragen
                var result = JOptionPane.showConfirmDialog(
                        this,
                        "Soll die Datei vor dem Schließen gespeichert werden?",
                        "Malprogramm",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) { // Der Benutzer will speichern
                    if (!save()) { // speichern
                        return; // wenn das Bild nicht gespeichert werden konnte, wird nichts gemacht
                    }
                } else if (result != JOptionPane.NO_OPTION) {
                    // Wenn der Benutzer nicht auf "Nein" geklickt hat, hat er entweder auf "Abbrechen" geklickt oder
                    // das Fragefenster geschlossen. In diesem Fall wird einfach nichts gemacht
                    return;
                }
            }
        }

        // Das Ereignis weiterleiten
        // Im Falle wvon WINDOW_CLOSING wird das Fenster geschlossen
        super.processWindowEvent(e);
    }

    /**
     * Gibt das aktuell ausgewählte Werkzeug zurück
     *
     * @return das aktuell ausgewählte Werkzeug
     */
    public Tool getSelectedTool() {
        return selectedTool;
    }

    /**
     * Setzt das aktuell ausgewählte Werkzeug
     *
     * @param selectedTool das Werkzeug, welches das neue ausgewählte Werkzeug sein soll
     */
    public void setSelectedTool(Tool selectedTool) {
        if (this.selectedTool != selectedTool) { // nichts machen, wenn es das gleiche ist
            var oldValue = this.selectedTool;
            this.selectedTool = selectedTool;

            // Benachrichtigen, dass sich das Werkzeug geändert hat
            firePropertyChange(SELECTED_TOOL_PROPERTY, oldValue, selectedTool);
        }
    }

    /**
     * Gibt die Leinwand zurück
     *
     * @return die Leinwand
     */
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    /**
     * Setzt die Leinwand
     *
     * @param canvas die Leinwand
     */
    public void setCanvas(DrawingCanvas canvas) {
        if (this.canvas != null) { // die Leinwand darf nur einmal festgelegt werden
            // sollte es ein zweites Mal passieren, ist dies ein Fehler
            throw new IllegalStateException("es ist bereits eine Leinwand festgelegt");
        }

        this.canvas = canvas;
        canvasContainer.add(canvas); // Leinwand im Fenster anzeigen

        // Benachrichtigen, dass sich die Leinwand geändert hat
        firePropertyChange(CANVAS_PROPRETY, null, canvas);

        updateTitle(); // Titel aktualisieren
        pack(); // Größe des Fenster an seinen neuen Inhalt anpassen
    }

    /**
     * Gibt die Datei zurück
     *
     * @return die Datei oder <code>null</code>, wenn das Bild neu ist und noch nicht gespeichert wurde
     */
    public File getFile() {
        return file;
    }

    /**
     * Legt die Datei fest, in die das Bild gespeichert werden soll
     *
     * @param file die Datei, in die das Bild gespeichert werden soll
     */
    public void setFile(File file) {
        var oldValue = this.file;
        this.file = file;

        // Benachrichtigen, dass sich die Datei geändert hat
        firePropertyChange(FILE_PROPERTY, oldValue, file);

        updateTitle(); // Titel aktualiesieren
    }

    /**
     * Gibt zurück, ob das Bild ungespeicherte Änderungen enthält
     *
     * @return <code>true</code>, wenn das Bild ungespeicherte Änderungen enthält
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Legt fest, ob das Bild ungespeicherte Änderungen enthält
     *
     * @param modified <code>true</code>, wenn das Bild ungespeicherte Änderungen enthält
     */
    public void setModified(boolean modified) {
        this.modified = modified;
        updateTitle(); // Titel aktualisieren
    }

    /**
     * Zeigt den "Speichern unter"-Dialog an und speichert das Bild in dieser Datei
     *
     * @return ob das Speichern erfolgreich war
     */
    public boolean saveAs() {
        selectFile(); // Datei auswählen lassen

        var file = getFile();
        if (file != null) { // wenn ausgewählt
            return save(file); // speichern
        }

        return false;
    }

    /**
     * Speichert das Bild und lässt den Benutzer, sollte noch keine Datei ausgewählt worden sein, eine Datei auswählen
     *
     * @return ob das Speichern erfolgreich war
     */
    public boolean save() {
        if (getFile() == null) {
            selectFile();
        }

        var file = getFile();
        if (file != null) { // wenn ausgewählt
            return save(file); // speichern
        }

        return false;
    }

    /**
     * Speichert das Bild unter der angegeben Datei
     *
     * @param file die Datei, in der das Bild gespeichert werden soll
     * @return ob das Speichern erfolgreich war
     */
    private boolean save(File file) {
        setEnabled(false); // kein Schließen wärend des Speicherns zulassen
        var success = FileUtilities.save(getCanvas().getImage(), file); // Speichern
        setEnabled(true); // Schließen wieder zulassen

        if (!success) { // Wenn nicht erfolgreich: Fehler anzeigen
            JOptionPane.showMessageDialog(
                    this,
                    "Die Datei konnte nicht gespeichert werden.",
                    "Malprogramm",
                    JOptionPane.ERROR_MESSAGE
            );
        } else { // Wenn erfolgreich: das Bild enthält keine ungespeicherten Änderungen mehr
            setModified(false);
        }

        return success;
    }

    /**
     * Lässt deb Benutzer eine Datei auswählen, in der das Bild gespeichert werden soll
     */
    private void selectFile() {
        setFile(FileUtilities.showSaveDialog(this));
    }
}
