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

public class RobertDialog extends JDialog {

    private final int WIDTH = 430;
    private final int HEIGHT = 130;
    private final int TEXT_FIELD_SIZE = 5;
    private final int DEFAULT_ROBERT = 80;
    private final int MIN_ROBERT = 1;
    private final int MAX_ROBERT = 101;
    private boolean error = true;

    public RobertDialog(final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        setTitle("Robert operator");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel robertPanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        JPanel textPanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        textPanel.add(new JLabel("Level"));
        final JTextField robertTextField = new JTextField(String.valueOf(DEFAULT_ROBERT));
        textPanel.add(robertTextField);
        final JSlider robertSlider = new JSlider(JSlider.HORIZONTAL, MIN_ROBERT, MAX_ROBERT, DEFAULT_ROBERT);
        robertSlider.setMajorTickSpacing(10);
        robertSlider.setMinorTickSpacing(1);
        robertSlider.setPaintLabels(true);
        robertSlider.setPaintTicks(true);
        robertSlider.setSnapToTicks(true);
        robertSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                robertTextField.setText(String.valueOf(robertSlider.getValue()));
            }
        });
        robertTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = DEFAULT_ROBERT;
                try {
                    value = Integer.parseInt(robertTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    robertTextField.setText(String.valueOf(DEFAULT_ROBERT));
                    JOptionPane.showMessageDialog(RobertDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < MIN_ROBERT) {
                    error = true;
                    robertTextField.setText(String.valueOf(DEFAULT_ROBERT));
                    JOptionPane.showMessageDialog(RobertDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value > MAX_ROBERT) {
                    error = true;
                    robertTextField.setText(String.valueOf(DEFAULT_ROBERT));
                    JOptionPane.showMessageDialog(RobertDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                robertSlider.setValue(value);
            }
        });
        robertPanel.add(textPanel);
        robertPanel.add(robertSlider);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (error) {
                    error = false;
                    return;
                }
                initMainWindow.robertOperator(Integer.parseInt(robertTextField.getText()));
                setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        robertPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(robertPanel);
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
                robertTextField.setText(String.valueOf(DEFAULT_ROBERT));
                robertSlider.setValue(DEFAULT_ROBERT);
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

}
