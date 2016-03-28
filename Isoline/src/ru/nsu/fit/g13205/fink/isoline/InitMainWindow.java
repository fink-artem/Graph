package ru.nsu.fit.g13205.fink.isoline;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private Options options = new Options();
    private JLabel statusBar = new JLabel("Ready");
    private InitView initView = new InitView(options, statusBar);
    private OptionsDialog optionsDialog;
    private boolean gridMode = false;
    private boolean isolineMode = false;
    private boolean interpolationMode = false;

    public InitMainWindow() {
        super();
        setTitle("Isoline");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/New", "Create a new document", KeyEvent.VK_X, "New.png", "onNew", statusBar);
            addMenuItem("File/Open", "Open an existing document", KeyEvent.VK_X, "Open.png", "onOpen", statusBar);
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit", statusBar);
            addSubMenu("Edit", KeyEvent.VK_F);
            addMenuItem("Edit/Options", "Options", KeyEvent.VK_X, "Options.png", "onOptions", statusBar);
            getMenuElement("Edit/Options").getComponent().setEnabled(false);
            addMenuItem("Edit/Grid", "Draw grid", KeyEvent.VK_X, "Grid.png", "onGrid", statusBar);
            addMenuItem("Edit/Isoline", "Draw isoline", KeyEvent.VK_X, "Isoline.png", "onIsoline", statusBar);
            addMenuItem("Edit/Interpolation", "Interpolation", KeyEvent.VK_X, "Interpolation.png", "onInterpolation", statusBar);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/New", "Create a new document", statusBar);
            addToolBarButton("File/Open", "Open an existing document", statusBar);
            addToolBarSeparator();
            addToolBarButton("Edit/Options", "Options", statusBar);
            ((JButton) toolBar.getComponentAtIndex(3)).setEnabled(false);
            addToolBarButton("Edit/Grid", "Draw grid", statusBar);
            addToolBarButton("Edit/Isoline", "Draw isoline", statusBar);
            addToolBarButton("Edit/Interpolation", "Interpolation", statusBar);
            addToolBarSeparator();
            addToolBarButton("Help/About", "Information about author", statusBar);

            add(statusBar, BorderLayout.SOUTH);
            add(initView);
        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNew() {
        initView.clear();
        ((JButton) toolBar.getComponentAtIndex(3)).setEnabled(false);
        getMenuElement("Edit/Options").getComponent().setEnabled(false);
    }

    public void onOpen() {
        File file = getOpenFileName("txt", "Text file");
        try (Scanner reader = new Scanner(new FileInputStream(file))) {
            int k = reader.nextInt();
            int m = reader.nextInt();
            int n = reader.nextInt() + 1;
            int colors[] = new int[n];
            for (int i = 0; i < n; i++) {
                colors[i] = (new Color(reader.nextInt(), reader.nextInt(), reader.nextInt())).getRGB();
            }
            int isolineColor = (new Color(reader.nextInt(), reader.nextInt(), reader.nextInt())).getRGB();
            options.setK(k);
            options.setM(m);
            initView.update(n, colors, isolineColor);
            ((JButton) toolBar.getComponentAtIndex(3)).setEnabled(true);
            getMenuElement("Edit/Options").getComponent().setEnabled(true);
        } catch (FileNotFoundException | NullPointerException e) {
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid file format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onOptions() {
        if (optionsDialog == null) {
            optionsDialog = new OptionsDialog(options);
        }
        optionsDialog.setVisible(true);
        if (optionsDialog.getStatus() == OptionsDialog.SUCCESS) {
            initView.repaint();
        }
    }

    public void onGrid() {
        gridMode = !gridMode;
        ((JButton) toolBar.getComponentAtIndex(4)).setSelected(gridMode);
        initView.setGridMode(gridMode);
    }

    public void onIsoline() {
        isolineMode = !isolineMode;
        ((JButton) toolBar.getComponentAtIndex(5)).setSelected(isolineMode);
        initView.setIsolineMode(isolineMode);
    }

    public void onInterpolation() {
        interpolationMode = !interpolationMode;
        ((JButton) toolBar.getComponentAtIndex(6)).setSelected(interpolationMode);
        initView.setInterpolationMode(interpolationMode);
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
