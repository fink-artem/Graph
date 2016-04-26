package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

public final class OptionsDialog extends JDialog {

    private final int DIALOG_WIDTH = 550;
    private final int DIALOG_HEIGHT = 550;
    private final int SPLAIN_PANEL_WIDTH = DIALOG_WIDTH;
    private final int SPLAIN_PANEL_HEIGHT = 350;
    private final int SPINNER_WIDTH = 80;
    private final int SPINNER_HEIGHT = 20;

    public OptionsDialog(JFrame frame, final Data data, InitView initView) {
        super(frame, false);
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        setLayout(null);
        SplainPanel splainPanel = new SplainPanel(data, initView);
        splainPanel.setBounds(0, 0, SPLAIN_PANEL_WIDTH, SPLAIN_PANEL_HEIGHT);
        add(splainPanel);
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, SPLAIN_PANEL_HEIGHT, DIALOG_WIDTH, 100);
        JPanel optionsPanel = new JPanel(new GridLayout(4, 4));

        JPanel nPanel = new JPanel();
        nPanel.add(new JLabel("n "));
        JSpinner nSpinner = new JSpinner(new SpinnerNumberModel(data.getN(), 1, 50, 1));
        nSpinner.addChangeListener((ChangeEvent e) -> {
            data.setN((int) nSpinner.getValue());
            initView.repaint();
        });
        nSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        nPanel.add(nSpinner);
        optionsPanel.add(nPanel);

        JPanel mPanel = new JPanel();
        mPanel.add(new JLabel("m "));
        JSpinner mSpinner = new JSpinner(new SpinnerNumberModel(data.getM(), 1, 50, 1));
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
            if ((double) aSpinner.getValue() < (double) bSpinner.getValue()) {
                data.setA((double) aSpinner.getValue());
                initView.repaint();
            } else {
                aSpinner.setValue(data.getA());
            }
        });
        bSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) aSpinner.getValue() < (double) bSpinner.getValue()) {
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
            if ((double) cSpinner.getValue() < (double) dSpinner.getValue()) {
                data.setC((double) cSpinner.getValue());
                initView.repaint();
            } else {
                cSpinner.setValue(data.getC());
            }
        });
        dSpinner.addChangeListener((ChangeEvent e) -> {
            if ((double) cSpinner.getValue() < (double) dSpinner.getValue()) {
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
        JSpinner swSpinner = new JSpinner(new SpinnerNumberModel(data.getSw(), 0, 1000, 1));
        swSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSw((double) swSpinner.getValue());
            initView.repaint();
        });
        swSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        swPanel.add(swSpinner);
        optionsPanel.add(swPanel);

        JPanel shPanel = new JPanel();
        shPanel.add(new JLabel("sh"));
        JSpinner shSpinner = new JSpinner(new SpinnerNumberModel(data.getSh(), 0, 1000, 1));
        shSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSh((double) shSpinner.getValue());
            initView.repaint();
        });
        shSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        shPanel.add(shSpinner);
        optionsPanel.add(shPanel);

        JPanel xPanel = new JPanel();
        xPanel.add(new JLabel("x "));
        JSpinner xSpinner = new JSpinner(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCx(), -1000, 1000, 1));
        xSpinner.addChangeListener((ChangeEvent e) -> {
            data.getModel((int) numberSpinner.getValue()).setCx((double) xSpinner.getValue());
            initView.repaint();
        });
        xSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        xPanel.add(xSpinner);
        optionsPanel.add(xPanel);

        JPanel yPanel = new JPanel();
        yPanel.add(new JLabel("y "));
        JSpinner ySpinner = new JSpinner(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCy(), -1000, 1000, 1));
        ySpinner.addChangeListener((ChangeEvent e) -> {
            data.getModel((int) numberSpinner.getValue()).setCy((double) ySpinner.getValue());
            initView.repaint();
        });
        ySpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        yPanel.add(ySpinner);
        optionsPanel.add(yPanel);

        JPanel zPanel = new JPanel();
        zPanel.add(new JLabel("z "));
        JSpinner zSpinner = new JSpinner(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCz(), -1000, 1000, 1));
        zSpinner.addChangeListener((ChangeEvent e) -> {
            data.getModel((int) numberSpinner.getValue()).setCz((double) zSpinner.getValue());
            initView.repaint();
        });
        zSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        zPanel.add(zSpinner);
        optionsPanel.add(zPanel);

        JPanel colorPanel = new JPanel();
        JButton colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        colorButton.setBackground(data.getModel((int) numberSpinner.getValue()).getColor());
        colorButton.setContentAreaFilled(false);
        colorButton.setOpaque(true);
        colorButton.addActionListener((ActionEvent actionEvent) -> {
            Color initialBackground = colorButton.getBackground();
            Color background = JColorChooser.showDialog(null, "Change color", initialBackground);
            if (background != null) {
                colorButton.setBackground(background);
                data.getModel((int) numberSpinner.getValue()).setColor(background);
                initView.repaint();
            }
        });
        colorPanel.add(colorButton);
        optionsPanel.add(colorPanel);

        numberSpinner.addChangeListener((ChangeEvent e) -> {
            int number = (int) numberSpinner.getValue();
            splainPanel.setModelNumber(number);
            xSpinner.setModel(new SpinnerNumberModel(data.getModel(number).getCx(), -1000, 1000, 1));
            ySpinner.setModel(new SpinnerNumberModel(data.getModel(number).getCy(), -1000, 1000, 1));
            zSpinner.setModel(new SpinnerNumberModel(data.getModel(number).getCz(), -1000, 1000, 1));
            colorButton.setBackground(data.getModel(number).getColor());
            data.setRotatingModelNumber(number);
            splainPanel.repaint();
        });

        mainPanel.setBounds(0, SPLAIN_PANEL_HEIGHT, DIALOG_WIDTH, DIALOG_HEIGHT - SPLAIN_PANEL_HEIGHT);
        mainPanel.add(optionsPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 15));

        JButton addButton = new JButton(new ImageIcon(getClass().getResource("resources/Add.png")));
        addButton.addActionListener((ActionEvent ae) -> {
            double[][] rotateMatrix = new double[4][4];
            rotateMatrix[0][0] = 1;
            rotateMatrix[1][1] = 1;
            rotateMatrix[2][2] = 1;
            rotateMatrix[3][3] = 1;
            int numberModel = data.addNewModel(50, 50, 50, rotateMatrix, Color.yellow);
            List<Coordinate2D> pivotList = new ArrayList<>();
            pivotList.add(new Coordinate2D(10, 20));
            pivotList.add(new Coordinate2D(20, 0));
            pivotList.add(new Coordinate2D(30, 20));
            pivotList.add(new Coordinate2D(40, 0));
            data.setPivotsListInModel(numberModel, pivotList);
            splainPanel.setModelNumber(numberModel);
            splainPanel.repaint();
            initView.repaint();
            numberSpinner.setModel(new SpinnerNumberModel(numberModel, 0, numberModel, 1));
            xSpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCx(), -1000, 1000, 1));
            ySpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCy(), -1000, 1000, 1));
            zSpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCz(), -1000, 1000, 1));
            colorButton.setBackground(data.getModel((int) numberSpinner.getValue()).getColor());
        });
        buttonPanel.add(addButton);
        JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("resources/Delete.png")));
        deleteButton.addActionListener((ActionEvent ae) -> {
            if (data.getModelNumber() > 1) {
                data.deleteModel((int) numberSpinner.getValue());
                splainPanel.setModelNumber(0);
                splainPanel.repaint();
                initView.repaint();
                numberSpinner.setModel(new SpinnerNumberModel(0, 0, data.getModelNumber() - 1, 1));
                xSpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCx(), -1000, 1000, 1));
                ySpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCy(), -1000, 1000, 1));
                zSpinner.setModel(new SpinnerNumberModel(data.getModel((int) numberSpinner.getValue()).getCz(), -1000, 1000, 1));
                colorButton.setBackground(data.getModel((int) numberSpinner.getValue()).getColor());
            } else {
                JOptionPane.showMessageDialog(OptionsDialog.this, "Нельзя удалить все объекты", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(deleteButton);
        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent ae) -> {
            data.setRotatingModelNumber(-1);
            dispose();
        });
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel);
        add(mainPanel);
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                data.setRotatingModelNumber(-1);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

}
