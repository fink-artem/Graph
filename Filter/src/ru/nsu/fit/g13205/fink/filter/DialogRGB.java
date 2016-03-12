package ru.nsu.fit.g13205.fink.filter;

import java.awt.Color;
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

public class DialogRGB extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int WIDTH = 250;
    private final int HEIGHT = 190;
    private final int TEXT_FIELD_SIZE = 5;
    private final int DEFAULT_VALUE = 2;
    private final int MIN_VALUE = 2;
    private final int MAX_VALUE = 256;
    private boolean status = false;
    private Color value = null;

    public DialogRGB() {
        super(new JFrame(), true);
        setTitle("Dithering");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel PanelRGB = new JPanel(new GridLayout(4, 2, 10, 10));
        PanelRGB.add(new JLabel("Red(" + MIN_VALUE + " - " + MAX_VALUE + ")"));
        final JTextField redTextField = new JTextField(String.valueOf(DEFAULT_VALUE), TEXT_FIELD_SIZE);
        PanelRGB.add(redTextField);
        PanelRGB.add(new JLabel("Green(" + MIN_VALUE + " - " + MAX_VALUE + ")"));
        final JTextField greenTextField = new JTextField(String.valueOf(DEFAULT_VALUE), TEXT_FIELD_SIZE);
        PanelRGB.add(greenTextField);
        PanelRGB.add(new JLabel("Blue(" + MIN_VALUE + " - " + MAX_VALUE + ")"));
        final JTextField blueTextField = new JTextField(String.valueOf(DEFAULT_VALUE), TEXT_FIELD_SIZE);
        PanelRGB.add(blueTextField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int red = Integer.parseInt(redTextField.getText());
                    int green = Integer.parseInt(greenTextField.getText());
                    int blue = Integer.parseInt(blueTextField.getText());
                    if (red >= MIN_VALUE && red <= MAX_VALUE && green >= MIN_VALUE && green <= MAX_VALUE && blue >= MIN_VALUE && blue <= MAX_VALUE) {
                        value = new Color(red - 1, green - 1, blue - 1);
                        status = SUCCESS;
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(DialogRGB.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(DialogRGB.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PanelRGB.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        PanelRGB.add(cancelButton);
        mainPanel.add(PanelRGB);
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
                redTextField.setText(String.valueOf(DEFAULT_VALUE));
                greenTextField.setText(String.valueOf(DEFAULT_VALUE));
                blueTextField.setText(String.valueOf(DEFAULT_VALUE));
                status = FAILED;
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    public boolean getStatus() {
        return status;
    }

    public Color getValue() {
        return value;
    }

}
