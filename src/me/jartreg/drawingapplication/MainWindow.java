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

public class MainWindow extends JFrame {
    public static final String SELECTED_TOOL_PROPERTY = "selectedTool";
    public static final String CANVAS_PROPRETY = "canvas";
    public static final String FILE_PROPERTY = "file";

    private File file;
    private boolean modified = false;

    private DrawingCanvas canvas;
    private final JComponent canvasContainer;
    private final JComponent contentPanel;
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

    public MainWindow() {
        super("Malprogramm");
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        canvasContainer = new JPanel(new GridBagLayout());
        contentPanel = buildContentPanel(canvasContainer);
        buildLayout(contentPanel);
        pack();

        setSelectedTool(tools[0]);
    }

    private void buildLayout(JComponent contentPanel) {
        var rootPanel = new JPanel(new BorderLayout());
        getContentPane().add(rootPanel);

        var menuBar = new JMenuBar();
        buildMenu(menuBar);
        rootPanel.add(menuBar, BorderLayout.PAGE_START);
        rootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private void buildMenu(JMenuBar menuBar) {
        var fileMenu = new JMenu("Datei");
        fileMenu.add(new NewAction(this));
        fileMenu.add(new OpenAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new SaveAction(this));
        fileMenu.add(new SaveAsAction(this));
        fileMenu.addSeparator();
        fileMenu.add(new CloseAction(this));
        menuBar.add(fileMenu);

        var viewMenu = new JMenu("Ansicht");
        var lafMenu = new JMenu("Aussehen");
        viewMenu.add(lafMenu);

        for (UIManager.LookAndFeelInfo lookAndFeel : UIManager.getInstalledLookAndFeels()) {
            lafMenu.add(new LookAndFeelMenuItem(lookAndFeel));
        }

        menuBar.add(viewMenu);
    }

    private JComponent buildContentPanel(JComponent canvasContainer) {
        var contentPanel = new JPanel(new BorderLayout());

        contentPanel.add(buildMainToolBar(), BorderLayout.PAGE_START);
        contentPanel.add(buildPropertiesToolBar(), BorderLayout.PAGE_END);

        var canvasScroller = new JScrollPane(canvasContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPanel.add(canvasScroller, BorderLayout.CENTER);

        return contentPanel;
    }

    private JToolBar buildMainToolBar() {
        var toolBar = new JToolBar();
        toolBar.setName("Werkzeuge");
        for (Tool tool : tools) {
            toolBar.add(new ToolSelectionButton(this, tool));
        }

        return toolBar;
    }

    private JToolBar buildPropertiesToolBar() {
        var toolBar = new JToolBar();
        toolBar.setName("Eigenschaften");

        toolBar.add(new ColorSelectionButton(this));
        toolBar.addSeparator();

        JPanel sliderPanel = new JPanel(new BorderLayout(8, 0));
        sliderPanel.add(new ThicknessSlider(this));
        sliderPanel.add(new JLabel("Breite:"), BorderLayout.LINE_START);
        toolBar.add(sliderPanel);

        return toolBar;
    }

    private void updateTitle() {
        setTitle((file == null ? "Unbenannt" : file.getName()) + (isModified() ? "*" : "") + " - Malprogramm");
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if(!isEnabled()) {
                return;
            }

            if (canvas != null && isModified()) {
                var result = JOptionPane.showConfirmDialog(
                        this,
                        "Soll die Datei vor dem Schließen gespeichert werden?",
                        "Malprogramm",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    if (!save()) {
                        return;
                    }
                } else if (result != JOptionPane.NO_OPTION) {
                    return;
                }
            }
        }

        super.processWindowEvent(e);
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        if (this.selectedTool != selectedTool) {
            var oldValue = this.selectedTool;
            this.selectedTool = selectedTool;

            // Den Tools mitteilen, ob sie ausgewählt sind
            if(oldValue != null)
                oldValue.setSelected(false);
            selectedTool.setSelected(true);

            firePropertyChange(SELECTED_TOOL_PROPERTY, oldValue, selectedTool);
        }
    }

    public DrawingCanvas getCanvas() {
        return canvas;
    }

    public void setCanvas(DrawingCanvas canvas) {
        if (this.canvas != null) {
            throw new IllegalStateException("canvas != null");
        }

        this.canvas = canvas;
        canvasContainer.add(canvas);
        firePropertyChange(CANVAS_PROPRETY, null, canvas);
        updateTitle();
        pack();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        var oldValue = this.file;
        this.file = file;
        firePropertyChange(FILE_PROPERTY, oldValue, file);
        updateTitle();
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
        updateTitle();
    }

    public boolean saveAs() {
        selectFile();

        var file = getFile();
        if (file != null) {
            return save(file);
        }

        return false;
    }

    public boolean save() {
        if (getFile() == null) {
            selectFile();
        }

        var file = getFile();
        if (file != null) {
            return save(file);
        }

        return false;
    }

    private boolean save(File file) {
        setEnabled(false);
        var success = FileUtilities.save(getCanvas().getImage(), file);
        setEnabled(true);

        if(!success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Die Datei konnte nicht gespeichert werden.",
                    "Malprogramm",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            setModified(false);
        }

        return success;
    }

    private void selectFile() {
        setFile(FileUtilities.showSaveDialog(this));
    }
}
