package me.jartreg.drawingapplication.tools;

import me.jartreg.drawingapplication.components.DrawingCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Die Basisklasse f�r alle Werkzeuge
 *
 * <p>
 * Der Name eines Werkzeugs kann mit der Methode {@link #getName()} abgerufen werden.
 * </p>
 *
 * <p>
 * Diese Klasse implementiert die Interfaces {@link MouseListener} und {@link MouseMotionListener}, damit die Werkzeuge
 * alle Arten von Mausbewegungen abfangen k�nnen. Um ein Ereignis zu behandeln, muss die entsprechende Methode lediglich
 * in einer Unterklasse �berschrieben werden.
 * </p>
 *
 * <p>
 * Sobald ein Werkzeug ausgew�hlt wird, wird dem Werkzeug mit der Methode {@link #setGraphics(Graphics2D)} ein
 * {@link Graphics2D}-Objekt �bertragen, welches es mit {@link #getGraphics()} zum Zeichnen auf dem Bild nutzen kann. Zudem kann
 * ein Werkzeug, wenn das {@link Graphics2D}-Objekt vorher bearbeitet werden soll, die Methode {@link #prepareGraphics(Graphics2D)}
 * �berschreiben.
 * </p>
 *
 * <p>
 * Sollte ein Werkzeug eine Vorschau zeichnen wollen, die nicht in dem Bild gespeichert werden soll, so kann es die
 * Methode {@link #drawPreview(Graphics2D)} �berschreiben und {@link #getCanvas()}<code>.</code>{@link DrawingCanvas#repaint() repaint()} aufrufen,
 * wenn es eine neue Vorschau zeichnen m�chte. Als Hilfe kann von der Klasse {@link me.jartreg.drawingapplication.tools.preview.PreviewTool PreviewTool}
 * geerbt werden.
 * </p>
 *
 * <p>
 * Einfache Werkzeuge, die direkt auf das Bild zeichnen, k�nnen von der Klasse {@link me.jartreg.drawingapplication.tools.drawing.DrawingTool DrawingTool} erben.
 * </p>
 *
 * <p>
 * <strong>Wichtig:</strong> wenn ein Werkzeug den Inhalt des Bildes ge�ndert hat, muss {@link #getCanvas()}<code>.</code>{@link DrawingCanvas#repaint() repaint()}
 * aufgrufen werden, damit der neue Inhalt des Bildes in das Fenster gezeichnet wird.
 * Zudem sollte {@link #getCanvas()}<code>.</code>{@link DrawingCanvas#markModified() markModified()} aufgerufen werden,
 * um das Bild als ge�ndert zu markieren.
 * </p>
 *
 * @see me.jartreg.drawingapplication.tools.preview.PreviewTool
 * @see me.jartreg.drawingapplication.tools.drawing.DrawingTool
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
     * Ob das Tool ausgew�hlt ist
     */
    private boolean selected = false;

    /**
     * Erneuert die Einstellungen des {@link Graphics2D}-Objekts. Dazu wird die Methode {@link #prepareGraphics(Graphics2D)}
     * erneut aufgerufen.
     *
     * <p>
     * Diese Methode wird benutzt, wenn sich einenEinstellung ge�ndert hat, die auch auf das {@link Graphics2D}-Objekt
     * �bertragen werden muss.
     * </p>
     */
    protected void updateGraphics() {
        Graphics2D g = getGraphics();
        if (g != null) { // wenn vorhanden
            prepareGraphics(g); // Einstellungen �bertragen
        }
    }

    /**
     * Gibt den Namen des Werkzeugs zur�ck.
     *
     * @return der Name des Werkzeugs
     */
    public abstract String getName();

    /**
     * Kann �berschrieben werden, um Einstellungen am {@link Graphics2D}-Objekt zu �ndern. Die Methode wird dann aufgerufen,
     * wenn das {@link Graphics2D}-Objekt neu eingestellt werden soll.
     *
     * <p>
     * <strong>Achtung:</strong> beim �berschreiben muss <em>immer</em> zuerst <code>super.prepareGraphics(graphics)</code>
     * aufgerufen werden, damit alle Klassen in der Hierarchie ihre Einstellungen auf das {@link Graphics2D}-Objekt �bertragen k�nnen.
     * </p>
     *
     * @param graphics das {@link Graphics2D}-Objekt
     */
    protected void prepareGraphics(Graphics2D graphics) {

    }

    /**
     * Gibt das {@link Graphics2D}-Objekt zur�ck, mit dem auf das Bild gezeichnet werden kann.
     *
     * @return das {@link Graphics2D}-Objekt
     */
    public Graphics2D getGraphics() {
        return graphics;
    }

    /**
     * Legt das {@link Graphics2D}-Objekt fest. Au�erdem wird {@link #prepareGraphics(Graphics2D)} aufgerufen, um Einstellungen
     * auf das neue {@link Graphics2D}-Objekt zu �bertragen.
     *
     * @param graphics das neue {@link Graphics2D}-Objekt
     */
    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;

        if (graphics != null) {
            prepareGraphics(graphics);
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
     * @param canvas die neue Leinwand
     */
    public void setCanvas(DrawingCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Gibt zur�ck, ob das Werkzeug ausgew�hlt ist.
     *
     * @return ob das Werkzeug ausgew�hlt ist
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Legt fest, ob das Werkzeug ausgew�hlt ist.
     *
     * <p>Diese Methode darf nur von {@link me.jartreg.drawingapplication.MainWindow} aufgerufen werden</p>
     *
     * @param selected ob das Werkzeug ausgew�hl ist
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Zeichnet die Vorschau in das Fenster
     *
     * <p>
     * Diese Methode wird aufgerufen, um es Werkzeugen zu erm�glichen, eine Vorschau zu zeigen, ohne direkt auf das Bild zu zeichnen.
     * Damit eine neue Vorschau gezeichnet wird, muss {@link #getCanvas()}<code>.</code>{@link DrawingCanvas#repaint() repaint()}
     * aufgerufen werden.
     * </p>
     *
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
