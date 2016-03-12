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

public class SliderAndTextDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int WIDTH = 430;
    private final int HEIGHT = 130;
    private final int TEXT_FIELD_SIZE = 5;
    private boolean error = false;
    private boolean status = false;
    private int value = 0;

    public SliderAndTextDialog(final int minValue, final int maxValue, final int defaultValue, int sliderStep, String title, String label) {
        super(new JFrame(), true);
        setTitle(title);
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel sobelPanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        JPanel textPanel = new JPanel(new GridLayout(1, 2, TEXT_FIELD_SIZE, TEXT_FIELD_SIZE));
        textPanel.add(new JLabel(label));
        final JTextField sobelTextField = new JTextField(String.valueOf(defaultValue));
        textPanel.add(sobelTextField);
        final JSlider sobelSlider = new JSlider(JSlider.HORIZONTAL, minValue, maxValue, defaultValue);
        sobelSlider.setMajorTickSpacing(sliderStep);
        sobelSlider.setMinorTickSpacing(1);
        sobelSlider.setPaintLabels(true);
        sobelSlider.setPaintTicks(true);
        sobelSlider.setSnapToTicks(true);
        sobelSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                sobelTextField.setText(String.valueOf(sobelSlider.getValue()));
            }
        });
        sobelTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = defaultValue;
                try {
                    value = Integer.parseInt(sobelTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    sobelTextField.setText(String.valueOf(defaultValue));
                    JOptionPane.showMessageDialog(SliderAndTextDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < minValue) {
                    error = true;
                    sobelTextField.setText(String.valueOf(defaultValue));
                    JOptionPane.showMessageDialog(SliderAndTextDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value > maxValue) {
                    error = true;
                    sobelTextField.setText(String.valueOf(defaultValue));
                    JOptionPane.showMessageDialog(SliderAndTextDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                sobelSlider.setValue(value);
            }
        });
        sobelPanel.add(textPanel);
        sobelPanel.add(sobelSlider);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (error) {
                    error = false;
                    return;
                }
                status = SUCCESS;
                value = Integer.parseInt(sobelTextField.getText());
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
        sobelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(sobelPanel);
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
                sobelTextField.setText(String.valueOf(defaultValue));
                status = FAILED;
                sobelSlider.setValue(defaultValue);
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    public boolean getStatus() {
        return status;
    }

    public int getValue() {
        return value;
    }

}
