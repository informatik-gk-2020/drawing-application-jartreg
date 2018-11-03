/**
 * Dieses Paket enthält alle Werkzeuge, die zum Malen benutzt werden können.
 *
 * <p>
 * Die Klasse {@link me.jartreg.drawingapplication.tools.ColorTool ColorTool} ist die Basisklasse
 * für alle Werkzeuge.
 * </p>
 *
 * <h3>Eigenschaften der Werkzeuge</h3>
 * <p>
 * Die Interfaces {@link me.jartreg.drawingapplication.tools.ColorTool ColorTool} und
 * {@link me.jartreg.drawingapplication.tools.ThicknessTool ThicknessTool} sind Interfaces, die Eigenschaften der Werkzeuge
 * wie die Farbe und die Breite darstellen. Implementiert eine Werkzeugklasse eines der Interfaces, so kann die Eigenschaft
 * mit den entsprechenden Steuerelementen angepasst werden. Implementiert eine Klasse eines der Interfaces nicht, so werden
 * die entsprechenden Steuerelemente deaktiviert, wenn das Werkzeug ausgewählt ist.
 * </p>
 *
 * <h2>Werkzeuge</h2>
 *
 * <h3>Einfache Werkzeuge</h3>
 * <p>
 *     Diese Werkzeuge zeichnen direkt auf das Bild und erben von {@link me.jartreg.drawingapplication.tools.drawing.DrawingTool DrawingTool}.
 * </p>
 * <ul>
 *     <li>{@link me.jartreg.drawingapplication.tools.drawing.PenTool PenTool}</li>
 *     <li>{@link me.jartreg.drawingapplication.tools.drawing.EraserTool EraserTool}</li>
 * </ul>
 *
 * <h3>Werkzeuge mit Vorschau</h3>
 * <p>
 *     Diese Werkzeuge zeichnen geometrische Formen und zeigen deswegen eine Vorschau, bevor sie auf das Bild zeichnen.
 *     Dazu erben sie von {@link me.jartreg.drawingapplication.tools.preview.PreviewTool PreviewTool}.
 * </p>
 * <ul>
 *     <li>{@link me.jartreg.drawingapplication.tools.preview.outline.LineTool LineTool}</li>
 *     <li>{@link me.jartreg.drawingapplication.tools.preview.outline.RectangleTool RectangleTool}</li>
 *     <li>{@link me.jartreg.drawingapplication.tools.preview.outline.CircleTool CircleTool}</li>
 *     <li>{@link me.jartreg.drawingapplication.tools.preview.filled.FilledRectangleTool FilledRectangleTool}</li>
 *     <li>{@link me.jartreg.drawingapplication.tools.preview.filled.FilledCircleTool FilledCircleTool}</li>
 * </ul>
 *
 * @see me.jartreg.drawingapplication.tools.Tool
 * @see me.jartreg.drawingapplication.tools.drawing.DrawingTool
 * @see me.jartreg.drawingapplication.tools.preview.PreviewTool
 */
package me.jartreg.drawingapplication.tools;