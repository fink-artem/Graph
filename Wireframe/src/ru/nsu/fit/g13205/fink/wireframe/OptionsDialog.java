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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
        JSpinner nSpinner = new JSpinner(new SpinnerNumberModel(data.getN(), 1, 100, 1));
        nSpinner.addChangeListener((ChangeEvent e) -> {
            data.setN((int) nSpinner.getValue());
            initView.repaint();
        });
        nSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        nPanel.add(nSpinner);
        optionsPanel.add(nPanel);

        JPanel mPanel = new JPanel();
        mPanel.add(new JLabel("m "));
        JSpinner mSpinner = new JSpinner(new SpinnerNumberModel(data.getM(), 1, 100, 1));
        mSpinner.addChangeListener((ChangeEvent e) -> {
            data.setM((int) mSpinner.getValue());
            initView.repaint();
        });
        mSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        mPanel.add(mSpinner);
        optionsPanel.add(mPanel);

        JPanel kPanel = new JPanel();
        kPanel.add(new JLabel("k "));
        JSpinner kSpinner = new JSpinner(new SpinnerNumberModel(data.getK(), 1, 50, 1));
        kSpinner.addChangeListener((ChangeEvent e) -> {
            data.setK((int) kSpinner.getValue());
            initView.repaint();
        });
        kSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        kPanel.add(kSpinner);
        optionsPanel.add(kPanel);

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("№"));
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(0, 0, data.getModelNumber() - 1, 1));
        numberSpinner.addChangeListener((ChangeEvent e) -> {
            splainPanel.setModelNumber((int) numberSpinner.getValue());
            splainPanel.repaint();
        });
        numberSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        numberPanel.add(numberSpinner);
        optionsPanel.add(numberPanel);

        JPanel aPanel = new JPanel();
        aPanel.add(new JLabel("a "));
        JSpinner aSpinner = new JSpinner(new SpinnerNumberModel(data.getA(), 0, 1.0, 0.01));
        aSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        aPanel.add(aSpinner);
        optionsPanel.add(aPanel);

        JPanel bPanel = new JPanel();
        bPanel.add(new JLabel("b "));
        JSpinner bSpinner = new JSpinner(new SpinnerNumberModel(data.getB(), 0, 1.0, 0.01));
        bSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        aSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) aSpinner.getValue() <= (double) bSpinner.getValue()) {
                data.setA((double) aSpinner.getValue());
                initView.repaint();
            } else {
                aSpinner.setValue(data.getA());
            }
        });
        bSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) aSpinner.getValue() <= (double) bSpinner.getValue()) {
                data.setB((double) bSpinner.getValue());
                initView.repaint();
            } else {
                bSpinner.setValue(data.getB());
            }
        });
        bPanel.add(bSpinner);
        optionsPanel.add(bPanel);

        JPanel cPanel = new JPanel();
        cPanel.add(new JLabel("c  "));
        JSpinner cSpinner = new JSpinner(new SpinnerNumberModel(data.getC(), 0, 6.28, 0.01));
        cSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        cPanel.add(cSpinner);
        optionsPanel.add(cPanel);

        JPanel dPanel = new JPanel();
        dPanel.add(new JLabel("d "));
        JSpinner dSpinner = new JSpinner(new SpinnerNumberModel(data.getD(), 0, 6.28, 0.01));
        cSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) cSpinner.getValue() <= (double) dSpinner.getValue()) {
                data.setC((double) cSpinner.getValue());
                initView.repaint();
            } else {
                cSpinner.setValue(data.getC());
            }
        });
        dSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) cSpinner.getValue() <= (double) dSpinner.getValue()) {
                data.setD((double) dSpinner.getValue());
                initView.repaint();
            } else {
                dSpinner.setValue(data.getD());
            }
        });
        dSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        dPanel.add(dSpinner);
        optionsPanel.add(dPanel);

        JPanel znPanel = new JPanel();
        znPanel.add(new JLabel("zn"));
        JSpinner znSpinner = new JSpinner(new SpinnerNumberModel(data.getZn(), 0, 100, 1));
        znSpinner.addChangeListener((ChangeEvent e) -> {
            data.setZn((double) znSpinner.getValue());
            initView.repaint();
        });
        znSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        znPanel.add(znSpinner);
        optionsPanel.add(znPanel);

        JPanel zfPanel = new JPanel();
        zfPanel.add(new JLabel("zf"));
        JSpinner zfSpinner = new JSpinner(new SpinnerNumberModel(data.getZf(), 0, 100, 1));
        zfSpinner.addChangeListener((ChangeEvent e) -> {
            data.setZf((double) zfSpinner.getValue());
            initView.repaint();
        });
        zfSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        zfPanel.add(zfSpinner);
        optionsPanel.add(zfPanel);

        JPanel swPanel = new JPanel();
        swPanel.add(new JLabel("sw"));
        JSpinner swSpinner = new JSpinner(new SpinnerNumberModel(data.getSw(), 0, 100, 1));
        swSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSw((double) swSpinner.getValue());
            initView.repaint();
        });
        swSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        swPanel.add(swSpinner);
        optionsPanel.add(swPanel);

        JPanel shPanel = new JPanel();
        shPanel.add(new JLabel("sh"));
        JSpinner shSpinner = new JSpinner(new SpinnerNumberModel(data.getSh(), 0, 100, 1));
        shSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSh((double) shSpinner.getValue());
            initView.repaint();
        });
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
    }

}
