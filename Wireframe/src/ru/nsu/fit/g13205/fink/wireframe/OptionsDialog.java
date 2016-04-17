package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public final class OptionsDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int DIALOG_WIDTH = 550;
    private final int DIALOG_HEIGHT = 500;
    private final int SPLAIN_PANEL_WIDTH = DIALOG_WIDTH;
    private final int SPLAIN_PANEL_HEIGHT = 350;
    private final int SPINNER_WIDTH = 80;
    private final int SPINNER_HEIGHT = 20;
    private final Data data;

    public OptionsDialog(JFrame frame, final Data data, InitView initView) {
        super(frame, true);
        this.data = data;
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        setLayout(null);
        SplainPanel splainPanel = new SplainPanel(data, initView);
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, SPLAIN_PANEL_HEIGHT, DIALOG_WIDTH, 100);
        JPanel optionsPanel = new JPanel(new GridLayout(3, 4));

        JPanel nPanel = new JPanel();
        nPanel.add(new JLabel("n "));
        JSpinner nSpinner = new JSpinner();
        nSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        nPanel.add(nSpinner);
        optionsPanel.add(nPanel);

        JPanel mPanel = new JPanel();
        mPanel.add(new JLabel("m "));
        JSpinner mSpinner = new JSpinner();
        mSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        mPanel.add(mSpinner);
        optionsPanel.add(mPanel);

        JPanel kPanel = new JPanel();
        kPanel.add(new JLabel("k "));
        JSpinner kSpinner = new JSpinner();
        kSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        kPanel.add(kSpinner);
        optionsPanel.add(kPanel);

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("№"));
        JSpinner numberSpinner = new JSpinner();
        numberSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        numberPanel.add(numberSpinner);
        optionsPanel.add(numberPanel);

        JPanel aPanel = new JPanel();
        aPanel.add(new JLabel("a "));
        JSpinner aSpinner = new JSpinner();
        aSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        aPanel.add(aSpinner);
        optionsPanel.add(aPanel);

        JPanel bPanel = new JPanel();
        bPanel.add(new JLabel("b "));
        JSpinner bSpinner = new JSpinner();
        bSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        bPanel.add(bSpinner);
        optionsPanel.add(bPanel);

        JPanel cPanel = new JPanel();
        cPanel.add(new JLabel("c  "));
        JSpinner cSpinner = new JSpinner();
        cSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        cPanel.add(cSpinner);
        optionsPanel.add(cPanel);

        JPanel dPanel = new JPanel();
        dPanel.add(new JLabel("d "));
        JSpinner dSpinner = new JSpinner();
        dSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        dPanel.add(dSpinner);
        optionsPanel.add(dPanel);

        JPanel znPanel = new JPanel();
        znPanel.add(new JLabel("zn"));
        JSpinner znSpinner = new JSpinner();
        znSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        znPanel.add(znSpinner);
        optionsPanel.add(znPanel);

        JPanel zfPanel = new JPanel();
        zfPanel.add(new JLabel("zf"));
        JSpinner zfSpinner = new JSpinner();
        zfSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        zfPanel.add(zfSpinner);
        optionsPanel.add(zfPanel);

        JPanel swPanel = new JPanel();
        swPanel.add(new JLabel("sw"));
        JSpinner swSpinner = new JSpinner();
        swSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        swPanel.add(swSpinner);
        optionsPanel.add(swPanel);

        JPanel shPanel = new JPanel();
        shPanel.add(new JLabel("sh"));
        JSpinner shSpinner = new JSpinner();
        shSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        shPanel.add(shSpinner);
        optionsPanel.add(shPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent ae) -> dispose());
        add(splainPanel);
        mainPanel.add(optionsPanel);
        mainPanel.add(okButton);
        add(mainPanel);
        optionsPanel.setBounds(0, SPLAIN_PANEL_HEIGHT, DIALOG_WIDTH, DIALOG_HEIGHT - SPLAIN_PANEL_HEIGHT);
        splainPanel.setBounds(0, 0, SPLAIN_PANEL_WIDTH, SPLAIN_PANEL_HEIGHT);

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
            }

            @Override
            public void windowClosed(WindowEvent we) {
            }

            @Override
            public void windowIconified(WindowEvent we) {
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
            }

            @Override
            public void windowActivated(WindowEvent we) {
                //todo
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

}
