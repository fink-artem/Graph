package ru.nsu.fit.g13205.fink.filter;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TurnDialog extends JDialog {

    private final int WIDTH = 430;
    private final int HEIGHT = 130;
    private final int TEXT_FIELD_SIZE = 5;
    private final int DEFAULT_ANGLE = 0;
    private final int MIN_ANGLE = -180;
    private final int MAX_ANGLE = 180;
    private boolean error = false;

    public TurnDialog(final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        setTitle("Rotation angle");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel anglePanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        JPanel textPanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        textPanel.add(new JLabel("Angle"));
        final JTextField angleTextField = new JTextField(String.valueOf(DEFAULT_ANGLE));
        textPanel.add(angleTextField);
        final JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, MIN_ANGLE, MAX_ANGLE, DEFAULT_ANGLE);
        angleSlider.setMajorTickSpacing(60);
        angleSlider.setMinorTickSpacing(1);
        angleSlider.setPaintLabels(true);
        angleSlider.setPaintTicks(true);
        angleSlider.setSnapToTicks(true);
        angleSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                angleTextField.setText(String.valueOf(angleSlider.getValue()));
            }
        });
        angleTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = DEFAULT_ANGLE;
                try {
                    value = Integer.parseInt(angleTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    angleTextField.setText(String.valueOf(DEFAULT_ANGLE));
                    JOptionPane.showMessageDialog(TurnDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < MIN_ANGLE) {
                    error = true;
                    angleTextField.setText(String.valueOf(DEFAULT_ANGLE));
                    JOptionPane.showMessageDialog(TurnDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value > MAX_ANGLE) {
                    error = true;
                    angleTextField.setText(String.valueOf(DEFAULT_ANGLE));
                    JOptionPane.showMessageDialog(TurnDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                angleSlider.setValue(value);
            }
        });
        anglePanel.add(textPanel);
        anglePanel.add(angleSlider);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (error) {
                    error = false;
                    return;
                }
                setVisible(false);
                initMainWindow.turn(Integer.parseInt(angleTextField.getText()));
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        anglePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(anglePanel);
        mainPanel.add(okButton);
        mainPanel.add(new JLabel("           "));
        mainPanel.add(cancelButton);
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
                angleTextField.setText(String.valueOf(DEFAULT_ANGLE));
                angleSlider.setValue(DEFAULT_ANGLE);
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

}
