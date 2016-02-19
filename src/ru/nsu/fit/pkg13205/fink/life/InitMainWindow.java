package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private Options options = new Options();
    private InitView initView = new InitView(options);
    private OptionsDialog optionsDialog = new OptionsDialog(options, this);

    public InitMainWindow() {
        super();
        setTitle("Life");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/New", "New", KeyEvent.VK_X, "New.png", "onNew");
            addMenuItem("File/Open", "Open", KeyEvent.VK_X, "Open.png", "onOpen");
            addMenuItem("File/Save", "Save", KeyEvent.VK_X, "Save.png", "onSave");
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit");

            addSubMenu("Modify", KeyEvent.VK_H);
            addMenuItem("Modify/Options", "Options", KeyEvent.VK_X, "Options.png", "onOptions");
            addMenuItem("Modify/Replace", "Replace", KeyEvent.VK_X, "Replace.png", "onReplace");
            getMenuElement("Modify/Replace").getComponent().setEnabled(false);
            addMenuItem("Modify/XOR", "XOR", KeyEvent.VK_X, "XOR.png", "onXor");
            addMenuSeparator("Modify");
            addMenuItem("Modify/Impact", "Impact", KeyEvent.VK_X, "Impact.png", "onNew");

            addSubMenu("Action", KeyEvent.VK_H);
            addMenuItem("Action/Init", "Init", KeyEvent.VK_X, "Init.png", "onInit");
            addMenuItem("Action/Next", "Next", KeyEvent.VK_X, "Next.png", "onNew");
            addMenuItem("Action/Run", "Run", KeyEvent.VK_X, "Run.png", "onNew");

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
            ((JButton) getToolBar().getComponentAtIndex(5)).setSelected(true);
            addToolBarButton("Modify/XOR");
            addToolBarButton("Modify/Impact");
            addToolBarSeparator();
            addToolBarButton("Action/Init");
            addToolBarButton("Action/Next");
            addToolBarButton("Action/Run");
            addToolBarSeparator();
            addToolBarButton("Help/About");

            add(initView);
            //JScrollPane pane = new JScrollPane(initView);        
            //add(pane);

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

    public void onXor() {
        getMenuElement("Modify/Replace").getComponent().setEnabled(true);
        ((JButton) getToolBar().getComponentAtIndex(5)).setSelected(false);
        getMenuElement("Modify/XOR").getComponent().setEnabled(false);
        ((JButton) getToolBar().getComponentAtIndex(6)).setSelected(true);
        options.setPaintMode(Options.PaintMode.XOR);
        optionsDialog.updateMode();
    }

    public void onReplace() {
        getMenuElement("Modify/Replace").getComponent().setEnabled(false);
        ((JButton) getToolBar().getComponentAtIndex(5)).setSelected(true);
        getMenuElement("Modify/XOR").getComponent().setEnabled(true);
        ((JButton) getToolBar().getComponentAtIndex(6)).setSelected(false);
        options.setPaintMode(Options.PaintMode.REPLACE);
        optionsDialog.updateMode();
    }

    public void onOptions() {
        optionsDialog.setVisible(true);
    }

    public void onInit() {
        initView.initGrid();
    }

    public void onExit() {
        System.exit(0);
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Fink Artem, NSU, FIT, group 13025", "About Author", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }
}
