package me.jartreg.drawingapplication.tools;

/**
 * Stellt die Eigenschaft Breite dar.
 */
public interface ThicknessTool {
    /**
     * Gibt die Breite des Tool zurück
     *
     * @return die Breite des Tools
     */
    float getThickness();

    /**
     * Legt die Breite des Tools fest
     *
     * @param value die neue Breite des Tools
     */
    void setThickness(float value);
}
