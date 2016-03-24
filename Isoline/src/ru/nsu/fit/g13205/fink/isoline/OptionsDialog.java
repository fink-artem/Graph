package ru.nsu.fit.g13205.fink.isoline;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class OptionsDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int WIDTH = 250;
    private final int HEIGHT = 280;
    private final int TEXT_FIELD_SIZE = 5;
    private final Options options;
    private final int MIN_VALUE = 1;
    private final int MAX_VALUE = 100;
    private boolean status = false;

    public OptionsDialog(final Options options) {
        super(new JFrame(), true);
        this.options = options;
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel optionsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        optionsPanel.add(new JLabel("K(" + MIN_VALUE + " - " + MAX_VALUE + ")"));
        final JTextField kTextField = new JTextField(String.valueOf(options.getK()), TEXT_FIELD_SIZE);
        optionsPanel.add(kTextField);
        optionsPanel.add(new JLabel("M(" + MIN_VALUE + " - " + MAX_VALUE + ")"));
        final JTextField mTextField = new JTextField(String.valueOf(options.getM()), TEXT_FIELD_SIZE);
        optionsPanel.add(mTextField);
        optionsPanel.add(new JLabel("A"));
        final JTextField aTextField = new JTextField(String.valueOf(options.getA()), TEXT_FIELD_SIZE);
        optionsPanel.add(aTextField);
        optionsPanel.add(new JLabel("B"));
        final JTextField bTextField = new JTextField(String.valueOf(options.getB()), TEXT_FIELD_SIZE);
        optionsPanel.add(bTextField);
        optionsPanel.add(new JLabel("C"));
        final JTextField cTextField = new JTextField(String.valueOf(options.getC()), TEXT_FIELD_SIZE);
        optionsPanel.add(cTextField);
        optionsPanel.add(new JLabel("D"));
        final JTextField dTextField = new JTextField(String.valueOf(options.getD()), TEXT_FIELD_SIZE);
        optionsPanel.add(dTextField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int k = Integer.parseInt(kTextField.getText());
                    int m = Integer.parseInt(mTextField.getText());
                    double a = Double.parseDouble(aTextField.getText());
                    double b = Double.parseDouble(bTextField.getText());
                    double c = Double.parseDouble(cTextField.getText());
                    double d = Double.parseDouble(dTextField.getText());
                    if (k >= MIN_VALUE && k <= MAX_VALUE && m >= MIN_VALUE && m <= MAX_VALUE) {
                        if (a <= c) {
                            if (b <= d) {
                                options.setK(k);
                                options.setM(m);
                                options.setA(a);
                                options.setB(b);
                                options.setC(c);
                                options.setD(d);
                                status = SUCCESS;
                                setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(OptionsDialog.this, "B must be less D", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(OptionsDialog.this, "A must be less C", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        optionsPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        optionsPanel.add(cancelButton);
        mainPanel.add(optionsPanel);
        add(mainPanel);

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
                kTextField.setText(String.valueOf(options.getK()));
                mTextField.setText(String.valueOf(options.getM()));
                aTextField.setText(String.valueOf(options.getA()));
                bTextField.setText(String.valueOf(options.getB()));
                cTextField.setText(String.valueOf(options.getC()));
                dTextField.setText(String.valueOf(options.getD()));
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    public boolean getStatus() {
        return status;
    }
}
