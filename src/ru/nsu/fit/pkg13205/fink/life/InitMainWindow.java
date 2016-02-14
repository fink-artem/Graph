package ru.nsu.fit.pkg13205.fink.life;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    public InitMainWindow() {
        super(600, 400, "Life");
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/New", "New", KeyEvent.VK_X, "New.png", "onNew");
            addMenuItem("File/Open", "Open", KeyEvent.VK_X, "Open.png", "onOpen");
            addMenuItem("File/Save", "Save", KeyEvent.VK_X, "Save.png", "onSave");
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit");

            addSubMenu("Modify", KeyEvent.VK_H);
            addMenuItem("Modify/Options", "Options", KeyEvent.VK_X, "Options.png", "onNew");
            addMenuItem("Modify/Replace", "Replace", KeyEvent.VK_X, "Replace.png", "onNew");
            addMenuItem("Modify/XOR", "XOR", KeyEvent.VK_X, "XOR.png", "onNew");
            addMenuSeparator("Modify");
            addMenuItem("Modify/Impact", "Impact", KeyEvent.VK_X, "Impact.png", "onNew");
            addMenuSeparator("Modify");
            addMenuItem("Modify/Clear", "Clear", KeyEvent.VK_X, "Clear.png", "onNew");

            addSubMenu("Action", KeyEvent.VK_H);
            addMenuItem("Action/Init", "Init", KeyEvent.VK_X, "New.png", "onNew");
            addMenuItem("Action/Next", "Next", KeyEvent.VK_X, "New.png", "onNew");
            addMenuItem("Action/Run", "Run", KeyEvent.VK_X, "New.png", "onNew");

            addSubMenu("View", KeyEvent.VK_H);
            addMenuItem("View/Toolbar", "Init", KeyEvent.VK_X, "New.png", "onNew");
            addMenuItem("View/Status Bar", "Next", KeyEvent.VK_X, "New.png", "onNew");

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout");

            addToolBarButton("File/New");
            addToolBarButton("File/Open");
            addToolBarButton("File/Save");
            addToolBarSeparator();
            addToolBarButton("Modify/Options");
            addToolBarButton("Modify/Replace");
            addToolBarButton("Modify/XOR");
            addToolBarButton("Modify/Impact");
            addToolBarButton("Modify/Clear");
            addToolBarSeparator();
            addToolBarButton("Action/Init");
            addToolBarButton("Action/Next");
            addToolBarButton("Action/Run");
            addToolBarSeparator();
            addToolBarButton("Help/About");

            add(new InitView());
        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNew() {

    }

    public void onOpen() {

    }

    public void onSave() {

    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Init, version 1.0\nCopyright � 2010 Vasya Pupkin, FF, group 1234", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }
}
