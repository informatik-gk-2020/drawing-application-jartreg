/**
 * Enth�lt das Malprogramm
 *
 * <p>
 * Die Klasse {@link me.jartreg.drawingapplication.Main} ist hierbei der Einstiegspunkt und erstellt ein neues Fenster
 * der Klasse {@link me.jartreg.drawingapplication.MainWindow}. Dieses enth�lt anfangs noch keine Leinwand,
 * da erst under Datei/Neu ein neues Bild erstellt oder mit Datei/�ffnen eins ge�ffnet werden muss.
 * </p>
 * <p>
 * Beim Erstellen eines neuen Bildes wird in {@link me.jartreg.drawingapplication.actions.NewAction NewAction} mit
 * {@link me.jartreg.drawingapplication.components.DrawingCanvas#createNew(me.jartreg.drawingapplication.MainWindow, int, int) DrawingCanvas.createNew(...)}
 * ein neues {@link me.jartreg.drawingapplication.components.DrawingCanvas DrawingCanvas} erstellt und mit
 * {@link me.jartreg.drawingapplication.MainWindow#setCanvas(me.jartreg.drawingapplication.components.DrawingCanvas) MainWindow.setDrawingCanvas(...)}
 * zum Fenster hinzugef�gt.
 * </p>
 * <p>
 * Beim �ffnen eines Bildes wird in {@link me.jartreg.drawingapplication.actions.OpenAction OpenAction} ein Bild gelesen,
 * damit ein neues {@link me.jartreg.drawingapplication.components.DrawingCanvas DrawingCanvas} erstellt
 * und zum Fenster hinzufgef�gt.
 * </p>
 *
 * <h3>Pakete</h3>
 * <dl>
 *     <dt>{@link me.jartreg.drawingapplication.actions}</dt>
 *     <dd>
 *         Dieses Paket enth�lt Klassen, die die Men�eintr�ge repr�sentieren.
 *     </dd>
 *
 *     <dt>{@link me.jartreg.drawingapplication.components}</dt>
 *     <dd>
 *         Dieses Paket enth�lt eigene Komponenten wie beispielsweise den
 *         {@link me.jartreg.drawingapplication.components.ColorSelectionButton Farbwahlknopf},
 *         das {@link me.jartreg.drawingapplication.components.DrawingCanvas DraingCanvas} oder
 *         {@link me.jartreg.drawingapplication.components.NumberTextField ein Textfeld, welches nur Nummern akzeptiert}.
 *     </dd>
 *
 *     <dt>{@link me.jartreg.drawingapplication.tools}</dt>
 *     <dd>
 *         Dieses Paket enth�lt alle Werkzeuge, die zum Malen benutzt werden k�nnen.
 *     </dd>
 * </dl>
 *
 * @see me.jartreg.drawingapplication.MainWindow
 */
package me.jartreg.drawingapplication;