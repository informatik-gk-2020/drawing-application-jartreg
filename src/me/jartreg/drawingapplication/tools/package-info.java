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
 * @see me.jartreg.drawingapplication.tools.Tool
 */
package me.jartreg.drawingapplication.tools;