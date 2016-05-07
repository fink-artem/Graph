package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private Data data = new Data();
    private JLabel statusBar = new JLabel("Ready");
    private InitView initView;
    private OptionsDialog optionsDialog;
    private JScrollPane scrollPane;

    public InitMainWindow() {
        super();
        setTitle("Wireframe");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open an existing document", KeyEvent.VK_X, "Open.png", "onOpen", statusBar);
            addMenuItem("File/Save", "Save the active document", KeyEvent.VK_X, "Save.png", "onSave", statusBar);
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit", statusBar);
            addSubMenu("Edit", KeyEvent.VK_F);
            addMenuItem("Edit/Init", "Init", KeyEvent.VK_X, "Init.png", "onInit", statusBar);
            addMenuItem("Edit/Options", "Options", KeyEvent.VK_X, "Options.png", "onOptions", statusBar);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/Open", "Open an existing document", statusBar);
            addToolBarButton("File/Save", "Save the active document", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Init", "Init", statusBar);
            addToolBarButton("Edit/Options", "Options", statusBar);
            addToolBarSeparator();
            addToolBarButton("Help/About", "Information about author", statusBar);

            add(statusBar, BorderLayout.SOUTH);
            try {
                data = Parser.parse(new File(FileUtils.getDataDirectory().getAbsolutePath() + "\\data.txt"));
            } catch (Exception ex) {
            }
            initView = new InitView(data);
            scrollPane = new JScrollPane(initView);
            add(scrollPane);
        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onOpen() {
        File file = getOpenFileName("scene", "Scene file");
        try {
            data = Parser.parse(file);
            initView.updateData(data);
        } catch (FileNotFoundException | NullPointerException e) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onSave() {
        
    }

    public void onOptions() {
        if (optionsDialog == null || !optionsDialog.isVisible()) {
            optionsDialog = new OptionsDialog(this, data, initView);
        }
        optionsDialog.setVisible(true);
    }

    public void onInit() {
        data.clearRotate();
        initView.repaint();
    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Fink Artem, NSU, FIT, group 13205", "About Author", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }

}
