package me.jartreg.drawingapplication.components;

import javax.swing.*;

public class NumberTextField extends JTextField {
    public NumberTextField(String text) {
        super(text);
    }

    @Override
    public void replaceSelection(String content) {
        if (content == null || content.isEmpty()) {
            super.replaceSelection(content);
            return;
        }

        content = content.trim();
        for (var c : content.toCharArray()) {
            if (!Character.isDigit(c))
                return;
        }

        super.replaceSelection(content);
    }
}
