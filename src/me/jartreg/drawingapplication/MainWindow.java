package me.jartreg.drawingapplication;

import me.jartreg.drawingapplication.actions.*;
import me.jartreg.drawingapplication.components.*;
import me.jartreg.drawingapplication.tools.Tool;
import me.jartreg.drawingapplication.tools.EraserTool;
import me.jartreg.drawingapplication.tools.PenTool;
import me.jartreg.drawingapplication.tools.FilledCircleTool;
import me.jartreg.drawingapplication.tools.FilledRectangleTool;
import me.jartreg.drawingapplication.tools.CircleTool;
import me.jartreg.drawingapplication.tools.LineTool;
import me.jartreg.drawingapplication.tools.RectangleTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Das Hauptfenster der Anwendung
 */
public class MainWindow extends JFrame {
    /**
     * Name der Eigenschaft f�r das aktuelle Werkzeug. Wird f�r Einenschafts�nderungsereignisse verwendet.
     */
    public static final String SELECTED_TOOL_PROPERTY = "selectedTool";

    /**
     * Name der Eigenschaft f�r die Leinwand. Wird f�r Einenschafts�nderungsereignisse verwendet.
     */
    public static final String CANVAS_PROPRETY = "canvas";

    /**
     * Die Datei, die gerade bearbeitet wird
     * <code>null</code>, wenn es ein neues Bild ist, welches noch nicht gespeichert wurde
     */
    private File file;

    /**
     * Ob das Bild seit dem letzten Speichern ge�ndert wurde
     */
    private boolean modified = false;

    /**
     * Die Leinwandkomponente
     */
    private DrawingCanvas canvas;

    /**
     * Die Komponente, die die Leinwand enth�lt
     * wird ben�tigt, damit die Leinwand immer zentriert ist und ausgetauscht werden kann
     */
    private final JComponent canvasContainer;

    /**
     * Die Komponente, die den gesamten Inhalt des Fensters enth�lt
     */
    private final JComponent contentPanel;

    /**
     * Alle zur Verf�gung stehenden Werkzeuge
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
        setMinimumSize(new Dimension(600, 400)); // Die Mindestgr��e
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Beim dr�cken auf den Schlie�en-Knopf soll das Fenster geschlossen werden

        // Den Inhalt des Fensters erstellen
        canvasContainer = new JPanel(new GridBagLayout());
        contentPanel = buildContentPanel(canvasContainer); // den Inhalt erstellen
        buildLayout(contentPanel); // zum Fenster hinzuf�gen
        pack(); // die Gr��e des Fensters an den Inhalt anpassen

        setSelectedTool(tools[0]); // das erste Werkzeug im Array ausw�hlen
    }

    /**
     * F�gt den Fensterinhalt und das Men� zum Fenster hinzu und legt ihr Layout fest
     *
     * @param contentPanel die Komponente, die den Inhalt enth�lt
     */
    private void buildLayout(JComponent contentPanel) {
        // eine neue Komponente, die das Men� und den Inhalt enthalten wird
        JPanel rootPanel = new JPanel(new BorderLayout());
        getContentPane().add(rootPanel); // zum Fenster hinzuf�gen

        // Das Men�
        JMenuBar menuBar = new JMenuBar();
        buildMenu(menuBar); // die Men�eintr�ge hinzuf�gen

        // Men�leiste und Inhalt hinzuf�gen
        rootPanel.add(menuBar, BorderLayout.PAGE_START);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * F�gt die Men�eintr�ge zum Men� hinzu
     *
     * @param menuBar das Men�, zu dem die Men�eintr�ge hinzugef�gt werden sollen
     */
    private void buildMenu(JMenuBar menuBar) {
        // Das Men� "Datei"
        JMenu fileMenu = new JMenu("Datei");
        fileMenu.add(new NewAction(this));
        fileMenu.add(new OpenAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new SaveAction(this));
        fileMenu.add(new SaveAsAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new CloseAction(this));
        menuBar.add(fileMenu); // "Datei" zum Men� hinzuf�gen

        // Das Men� "Ansicht"
        JMenu viewMenu = new JMenu("Ansicht");
        JMenu lafMenu = new JMenu("Aussehen"); // Das Untermen� "Ansicht"/"Aussehen"
        viewMenu.add(lafMenu); // "Aussehen" zu "Ansicht hinzuf�gen"

        // F�r jedes Aussehen einen neuen Men�eintrag hinzuf�gen
        for (UIManager.LookAndFeelInfo lookAndFeel : UIManager.getInstalledLookAndFeels()) {
            lafMenu.add(new LookAndFeelMenuItem(lookAndFeel));
        }

        menuBar.add(viewMenu); // "Ansicht" zum Men� hinzuf�gen
    }

    /**
     * Erstellt den Inhalt des Fensters
     *
     * @param canvasContainer die Komponente, die die leinwand enthalten wird
     * @return die komponente, die den Inhalt des Fensters enth�lt
     */
    private JComponent buildContentPanel(JComponent canvasContainer) {
        JPanel contentPanel = new JPanel(new BorderLayout()); // die Komponente erstellen

        // Hauptwerkzeugleiste erstellen und hinzuf�gen (oben)
        contentPanel.add(buildMainToolBar(), BorderLayout.PAGE_START);

        // Eigenschaftswerkzeugleiste erstellen und hinzuf�gen (unten)
        contentPanel.add(buildPropertiesToolBar(), BorderLayout.PAGE_END);

        // Eine Scrollkomponente, damit die Leinwand gescrollt werden kann, wenn das Fenster kleiner ist
        JScrollPane canvasScroller = new JScrollPane(
                canvasContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        contentPanel.add(canvasScroller, BorderLayout.CENTER); // in der Mitte hinzuf�gen

        return contentPanel;
    }

    /**
     * Erstellt die Hauptwerkzeugleiste
     *
     * @return die Hauptwerkzeugleiste
     */
    private JToolBar buildMainToolBar() {
        // Werkzeugleiste erstellen und benennen
        JToolBar toolBar = new JToolBar();
        toolBar.setName("Werkzeuge");

        // F�r jedes Werkzeug einen Eintrag hinzuf�gen
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
        JToolBar toolBar = new JToolBar();
        toolBar.setName("Eigenschaften");

        // Knopf zum Ausw�hlen der Farbe
        toolBar.add(new ColorSelectionButton(this));
        toolBar.addSeparator(); // Trennlinie

        // Der Slider f�r die Breite
        // Der SLider soll links neben sich einen Text "Breite:" haben, wof�r eine weitere Komponente notwendig ist
        // Ein Panel mit einem horizontalen Abstand von 8 Pixeln zwischen den Komponenten
        JPanel sliderPanel = new JPanel(new BorderLayout(8, 0));
        sliderPanel.add(new ThicknessSlider(this)); // Slider hinzuf�gen
        sliderPanel.add(new JLabel("Breite:"), BorderLayout.LINE_START); // Text links hinzuf�gen
        toolBar.add(sliderPanel); // zur Werkzeugleiste hinzuf�gen

        return toolBar;
    }

    /**
     * Erneuert den Titel des Fensters
     * <p>
     * Die Methode wird aufgerufen, wenn sich der Name der Datei ge�ndert hat, das Bild ungespeicherte �nderungen enth�lt
     * oder gespeichert wurde
     * </p>
     */
    private void updateTitle() {
        setTitle(
                (file == null ? "Unbenannt" : file.getName()) // Name der Datei
                        + (isModified() ? "*" : "") // "*" wenn das Bild nicht gespeicherte �nderungen enth�lt
                        + " - Malprogramm" // Der Name der Anwendung
        );
    }

    /**
     * Bearbeitet ereignisse, die an das Fenster gesendet werden
     * <p>
     * Die Methode wird �berschrieben, um den Benutzer beim schlie�en des Fensters zu fragen, ob er das Bild speicher will
     * </p>
     *
     * @param e das Ereignis
     */
    @Override
    protected void processWindowEvent(WindowEvent e) {
        // WINDOW_CLOSING wird ausgel�st, wenn suf den SChlie�en-Knopf geklickt wurde
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (!isEnabled()) { // Wenn das Fenster gerade keine Eingabe zul�sst, wird es ignoriert
                return;
            }

            // Wenn im Fenster ein Bild vorhanden ist und es nicht gespeicherte �nderungen enth�lt
            if (canvas != null && isModified()) {
                // Benutzer fragen
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "Soll die Datei vor dem Schlie�en gespeichert werden?",
                        "Malprogramm",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) { // Der Benutzer will speichern
                    if (!save(false)) { // speichern
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
     * Gibt das aktuell ausgew�hlte Werkzeug zur�ck
     *
     * @return das aktuell ausgew�hlte Werkzeug
     */
    public Tool getSelectedTool() {
        return selectedTool;
    }

    /**
     * Setzt das aktuell ausgew�hlte Werkzeug
     *
     * @param selectedTool das Werkzeug, welches das neue ausgew�hlte Werkzeug sein soll
     */
    public void setSelectedTool(Tool selectedTool) {
        if (this.selectedTool != selectedTool) { // nichts machen, wenn es das gleiche ist
            Tool oldValue = this.selectedTool;
            this.selectedTool = selectedTool;

            // Den Tools mitteilen, ob sie ausgew�hlt sind
            if (oldValue != null)
                oldValue.setSelected(false);
            selectedTool.setSelected(true);

            // Benachrichtigen, dass sich das Werkzeug ge�ndert hat
            firePropertyChange(SELECTED_TOOL_PROPERTY, oldValue, selectedTool);
        }
    }

    /**
     * Gibt die Leinwand zur�ck
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

        // Benachrichtigen, dass sich die Leinwand ge�ndert hat
        firePropertyChange(CANVAS_PROPRETY, null, canvas);

        updateTitle(); // Titel aktualisieren
        pack(); // Gr��e des Fenster an seinen neuen Inhalt anpassen
    }

    /**
     * Gibt die Datei zur�ck
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
        this.file = file;

        updateTitle(); // Titel aktualisieren
    }

    /**
     * Gibt zur�ck, ob das Bild ungespeicherte �nderungen enth�lt
     *
     * @return <code>true</code>, wenn das Bild ungespeicherte �nderungen enth�lt
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Legt fest, ob das Bild ungespeicherte �nderungen enth�lt
     *
     * @param modified <code>true</code>, wenn das Bild ungespeicherte �nderungen enth�lt
     */
    public void setModified(boolean modified) {
        this.modified = modified;
        updateTitle(); // Titel aktualisieren
    }

    /**
     * Speichert das Bild und l�sst den Benutzer, sollte noch keine Datei ausgew�hlt worden sein, eine Datei ausw�hlen.
     *
     * @param forceDialog erzwingt das Anzeigen eines Speichern-Dialogs
     * @return ob das Speichern erfolgreich war
     */
    public boolean save(boolean forceDialog) {
        File file = getFile();

        // Datei ausw�hlen lassen wenn noch keine ausgew�hlt ist oder forceDialog true ist
        if (forceDialog || file == null) {
            file = FileUtilities.showSaveDialog(this);
        }

        if (file != null) { // wenn ausgew�hlt
            setFile(file); // aktualisieren, falls eine andere ausgew�hlt wurde
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
        setEnabled(false); // kein Schlie�en w�rend des Speicherns zulassen
        boolean success = FileUtilities.save(getCanvas().getImage(), file); // Speichern
        setEnabled(true); // Schlie�en wieder zulassen

        if (!success) { // Wenn nicht erfolgreich: Fehler anzeigen
            JOptionPane.showMessageDialog(
                    this,
                    "Die Datei konnte nicht gespeichert werden.",
                    "Malprogramm",
                    JOptionPane.ERROR_MESSAGE
            );
        } else { // Wenn erfolgreich: das Bild enth�lt keine ungespeicherten �nderungen mehr
            setModified(false);
        }

        return success;
    }
}
