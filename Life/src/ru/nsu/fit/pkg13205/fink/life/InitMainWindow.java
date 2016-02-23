package ru.nsu.fit.pkg13205.fink.life;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import ru.nsu.cg.MainFrame;

public class InitMainWindow extends MainFrame {

    private final int MIN_WIDTH = 800;
    private final int MIN_HEIGHT = 600;
    private Options options = new Options();
    private InitView initView = new InitView(options);
    private OptionsDialog optionsDialog = new OptionsDialog(options, this);
    private NewDocumentDialog newDocumentDialog = new NewDocumentDialog(options, this);
    private Timer timer;
    private JLabel statusBar = new JLabel("Ready");

    public InitMainWindow() {
        super();
        setTitle("Life");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - MIN_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MIN_HEIGHT / 2, MIN_WIDTH, MIN_HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/New", "New", KeyEvent.VK_X, "New.png", "onNew", statusBar);
            addMenuItem("File/Open", "Open", KeyEvent.VK_X, "Open.png", "onOpen", statusBar);
            addMenuItem("File/Save", "Save", KeyEvent.VK_X, "Save.png", "onSave", statusBar);
            addMenuSeparator("File");
            addMenuItem("File/Exit", "Exit", KeyEvent.VK_X, "Exit.png", "onExit", statusBar);

            addSubMenu("Modify", KeyEvent.VK_H);
            addMenuItem("Modify/Options", "Options", KeyEvent.VK_X, "Options.png", "onOptions", statusBar);
            addMenuItem("Modify/Replace", "Replace", KeyEvent.VK_X, "Replace.png", "onReplace", statusBar);
            getMenuElement("Modify/Replace").getComponent().setEnabled(false);
            addMenuItem("Modify/XOR", "XOR", KeyEvent.VK_X, "XOR.png", "onXor", statusBar);
            addMenuSeparator("Modify");
            addMenuItem("Modify/Impact", "Impact", KeyEvent.VK_X, "Impact.png", "onImpact", statusBar);

            addSubMenu("Action", KeyEvent.VK_H);
            addMenuItem("Action/Init", "Init", KeyEvent.VK_X, "Init.png", "onInit", statusBar);
            addMenuItem("Action/Next", "Next", KeyEvent.VK_X, "Next.png", "onNext", statusBar);
            addMenuItem("Action/Run", "Run", KeyEvent.VK_X, "Run.png", "onRun", statusBar);

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout", statusBar);

            addToolBarButton("File/New", statusBar);
            addToolBarButton("File/Open", statusBar);
            addToolBarButton("File/Save", statusBar);
            addToolBarSeparator();
            addToolBarButton("Modify/Options", statusBar);
            addToolBarButton("Modify/Replace", statusBar);
            ((JButton) toolBar.getComponentAtIndex(5)).setSelected(true);
            addToolBarButton("Modify/XOR", statusBar);
            addToolBarButton("Modify/Impact", statusBar);
            addToolBarSeparator();
            addToolBarButton("Action/Init", statusBar);
            addToolBarButton("Action/Next", statusBar);
            addToolBarButton("Action/Run", statusBar);
            addToolBarSeparator();
            addToolBarButton("Help/About", statusBar);

            add(statusBar, BorderLayout.SOUTH);
            JScrollPane scrollPane = new JScrollPane(initView);
            add(scrollPane);

            addWindowListener(new CloseWindowListener(this));

        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNew() {
        newDocumentDialog.setVisible(true);
    }

    public void onOpen() {
        File file = getOpenFileName("", "");
        try (Scanner reader = new Scanner(file)) {
            String s = reader.nextLine();
            int search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
            search = s.indexOf(" ");
            options.setRowNumber(Integer.parseInt(s.substring(0, search)));
            s = s.substring(search + 1).trim();
            options.setColumnNumber(Integer.parseInt(s));
            s = reader.nextLine();
            search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
            options.setGridWidth(Integer.parseInt(s));
            s = reader.nextLine();
            search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
            options.setCellSize(Integer.parseInt(s));
            s = reader.nextLine();
            search = s.indexOf("//");
            if (search != - 1) {
                s = s.substring(0, search).trim();
            }
            int length = Integer.parseInt(s);
            int x, y;
            initView.createNewModel();
            Model model = initView.getModel();
            for (int i = 0; i < length; i++) {
                s = reader.nextLine();
                search = s.indexOf("//");
                if (search != - 1) {
                    s = s.substring(0, search).trim();
                }
                search = s.indexOf(" ");
                x = Integer.parseInt(s.substring(0, search));
                s = s.substring(search + 1).trim();
                y = Integer.parseInt(s);
                model.setCellStatus(y, x, Model.Cell.LIVE);
            }
            initView.initGrid(false);
        } catch (FileNotFoundException | NullPointerException e) {
        }
    }

    public void onSave() {
        File file = getSaveFileName("", "");
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(options.getRowNumber() + " " + options.getColumnNumber());
            out.println(options.getGridWidth());
            out.println(options.getCellSize());
            Model model = initView.getModel();
            out.println(model.getLivingCellsNumber());
            int n = model.getN();
            int m = model.getM();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (model.getCellStatus(j, i) == Model.Cell.LIVE) {
                        out.println(i + " " + j);
                    }
                }
            }
        } catch (FileNotFoundException | NullPointerException e) {
        }
    }

    public void onRun() {
        if (initView.isRunMode()) {
            ((JButton) toolBar.getComponentAtIndex(11)).setSelected(false);
            ((JButton) toolBar.getComponentAtIndex(10)).setEnabled(true);
            ((JButton) toolBar.getComponentAtIndex(9)).setEnabled(true);
            ((JButton) toolBar.getComponentAtIndex(4)).setEnabled(true);
            getMenuElement("Action/Init").getComponent().setEnabled(true);
            getMenuElement("Action/Next").getComponent().setEnabled(true);
            getMenuElement("Modify/Options").getComponent().setEnabled(true);
            initView.setRunMode(false);
            timer.cancel();
        } else {
            timer = new Timer();
            ((JButton) toolBar.getComponentAtIndex(11)).setSelected(true);
            ((JButton) toolBar.getComponentAtIndex(10)).setEnabled(false);
            ((JButton) toolBar.getComponentAtIndex(9)).setEnabled(false);
            ((JButton) toolBar.getComponentAtIndex(4)).setEnabled(false);
            getMenuElement("Action/Init").getComponent().setEnabled(false);
            getMenuElement("Action/Next").getComponent().setEnabled(false);
            getMenuElement("Modify/Options").getComponent().setEnabled(false);
            initView.setRunMode(true);
            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    initView.next();
                }
            };
            timer.schedule(timerTask, 0, options.getTimer());
        }
    }

    public void onImpact() {
        if (initView.isImpactMode()) {
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(false);
            initView.ImpactOff();
        } else {
            ((JButton) toolBar.getComponentAtIndex(7)).setSelected(true);
            initView.ImpactOn();
        }
    }

    public void onNext() {
        initView.next();
    }

    public void onXor() {
        getMenuElement("Modify/Replace").getComponent().setEnabled(true);
        ((JButton) toolBar.getComponentAtIndex(5)).setSelected(false);
        getMenuElement("Modify/XOR").getComponent().setEnabled(false);
        ((JButton) toolBar.getComponentAtIndex(6)).setSelected(true);
        options.setPaintMode(Options.PaintMode.XOR);
        optionsDialog.updateMode();
    }

    public void onReplace() {
        getMenuElement("Modify/Replace").getComponent().setEnabled(false);
        ((JButton) toolBar.getComponentAtIndex(5)).setSelected(true);
        getMenuElement("Modify/XOR").getComponent().setEnabled(true);
        ((JButton) toolBar.getComponentAtIndex(6)).setSelected(false);
        options.setPaintMode(Options.PaintMode.REPLACE);
        optionsDialog.updateMode();
    }

    public void onOptions() {
        optionsDialog.setVisible(true);
    }

    public void onInit() {
        initView.clear();
    }

    public void redraw() {
        initView.initGrid(false);
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

    void createNewDocument() {
        initView.initGrid(true);
    }
}
