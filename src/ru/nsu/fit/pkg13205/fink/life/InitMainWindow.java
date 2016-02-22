package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
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
            addMenuItem("Modify/Impact", "Impact", KeyEvent.VK_X, "Impact.png", "onImpact");

            addSubMenu("Action", KeyEvent.VK_H);
            addMenuItem("Action/Init", "Init", KeyEvent.VK_X, "Init.png", "onInit");
            addMenuItem("Action/Next", "Next", KeyEvent.VK_X, "Next.png", "onNext");
            addMenuItem("Action/Run", "Run", KeyEvent.VK_X, "Run.png", "onRun");

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About", "About", KeyEvent.VK_A, "About.png", "onAbout");

            addToolBarButton("File/New");
            addToolBarButton("File/Open");
            addToolBarButton("File/Save");
            addToolBarSeparator();
            addToolBarButton("Modify/Options");
            addToolBarButton("Modify/Replace");
            ((JButton) toolBar.getComponentAtIndex(5)).setSelected(true);
            addToolBarButton("Modify/XOR");
            addToolBarButton("Modify/Impact");
            addToolBarSeparator();
            addToolBarButton("Action/Init");
            addToolBarButton("Action/Next");
            addToolBarButton("Action/Run");
            addToolBarSeparator();
            addToolBarButton("Help/About");

            JScrollPane scrollPane = new JScrollPane(initView);
            add(scrollPane);

        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNew() {
        newDocumentDialog.setVisible(true);
    }

    public void onOpen() {
        File file = getOpenFileName("", "");
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
                        out.println(j + " " + i);
                    }
                }
            }
        } catch (FileNotFoundException e) {
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
