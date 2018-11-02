package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.components.DrawingCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Die Basisklasse für alle Werkzeuge
 *
 * <p>
 *     Der Name eines Werkzeugs kann mit der Methode {@link #getName()} abgerufen werden.
 * </p>
 *
 * <p>
 *     Diese Klasse implementiert die Interfaces {@link MouseListener} und {@link MouseMotionListener}, damit die Werkzeuge
 *     alle Arten von Mausbewegungen abfangen können. Um ein Ereignis zu behandeln, muss die entsprechende Methode lediglich
 *     in einer Unterklasse überschrieben werden.
 * </p>
 *
 * <p>
 *     Sobald ein Werkzeug ausgewählt wird, wird dem Werkzeug mit der Methode {@link #setGraphics(Graphics2D)} ein
 *     {@link Graphics2D}-Objekt übertragen, welches es mit {@link #getGraphics()} zum Zeichnen auf dem Bild nutzen kann. Zudem kann
 *     ein Werkzeug, wenn das {@link Graphics2D}-Objekt vorher bearbeitet werden soll, die Methode {@link #prepareGraphics(Graphics2D)}
 *     überschreiben.
 * </p>
 *
 * <p>
 *     Sollte ein Werkzeug eine Vorschau zeichnen wollen, die nicht in dem Bild gespeichert werden soll, so kann es die
 *     Methode {@link #drawPreview(Graphics2D)} überschreiben und {@link #getCanvas()}<code>.</code>{@link DrawingCanvas#repaint()} aufrufen,
 *     wenn es eine neue Vorschau zeichnen möchte.
 * </p>
 */
public abstract class Tool implements MouseListener, MouseMotionListener {
    /**
     * Das {@link Graphics2D}-Objekt zum Zeichnen auf dem Bild
     */
    private Graphics2D graphics = null;

    /**
     * Die Leinwand
     */
    private DrawingCanvas canvas = null;

    /**
     * Ob das Tool ausgewählt ist
     */
    private boolean selected = false;

    /**
     * Erneuert die Einstellungen des {@link Graphics2D}-Objekts. Dazu wird die Methode {@link #prepareGraphics(Graphics2D)}
     * erneut aufgerufen.
     *
     * <p>
     *     Diese Methode wird benutzt, wenn sich einenEinstellung geändert hat, die auch auf das {@link Graphics2D}-Objekt
     *     übertragen werden muss.
     * </p>
     */
    protected void updateGraphics() {
        var g = getGraphics();
        if (g != null) { // wenn vorhanden
            prepareGraphics(g); // Einstellungen übertragen
        }
    }

    /**
     * Gibt den Namen des Werkzeigs zurück.
     * @return der Name des Werkzeugs
     */
    public abstract String getName();

    /**
     * Kann überschrieben werden, um Einstellungen am {@link Graphics2D}-Objekt zu ändern. Die Methode wird dann aufgerufen,
     * wenn das {@link Graphics2D}-Objekt neu eingestellt werden soll.
     *
     * <p>
     *     <strong>Achtung:</strong> beim Überschreiben muss <em>immer</em> zuerst <code>super.prepareGraphics(graphics)</code>
     *     aufgerufen werden, damit alle Klassen in der Hierarchie ihre Einstellungen auf das {@link Graphics2D}-Objekt übertragen können.
     * </p>
     *
     * @param graphics das {@link Graphics2D}-Objekt
     */
    protected void prepareGraphics(Graphics2D graphics) {

    }

    /**
     * Gibt das {@link Graphics2D}-Objekt zurück, mit dem auf das Bild gezeichnet werden kann.
     * @return das {@link Graphics2D}-Objekt
     */
    public Graphics2D getGraphics() {
        return graphics;
    }

    /**
     * Legt das {@link Graphics2D}-Objekt fest. Außerdem wird {@link #prepareGraphics(Graphics2D)} aufgerufen, um Einstellungen
     * auf das neue {@link Graphics2D}-Objekt zu übertragen.
     * @param graphics das neue {@link Graphics2D}-Objekt
     */
    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;

        if (graphics != null) {
            prepareGraphics(graphics);
        }
    }

    /**
     * Gibt die Leinwand zurück
     * @return die Leinwand
     */
    public DrawingCanvas getCanvas() {
        return canvas;
    }

    /**
     * Setzt die Leinwand
     * @param canvas die neue Leinwand
     */
    public void setCanvas(DrawingCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Gibt zurück, ob das Werkzeug ausgewählt ist.
     * @return ob das Werkzeug ausgewählt ist
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Legt fest, ob das Werkzeug ausgewählt ist.
     *
     * <p>Diese Methode darf nur von {@link me.jartreg.drawingapplication.MainWindow} aufgerufen werden</p>
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Kann überschrieben werden, umm eine Vorschau zu zeichnen
     * @param g das {@link Graphics2D}-Objekt
     */
    public void drawPreview(Graphics2D g) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
