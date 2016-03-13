package ru.nsu.fit.g13205.fink.filter;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class PixelizeDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int WIDTH = 250;
    private final int HEIGHT = 100;
    private final int TEXT_FIELD_SIZE = 5;
    private final int validValue[] = {2, 5, 7, 10};
    private final int DEFAULT_PIXEL_SIZE = validValue[0];
    private boolean status = false;
    private int value = 0;

    public PixelizeDialog() {
        super(new JFrame(), true);
        setTitle("Pixelize");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel pixelizePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        pixelizePanel.add(new JLabel("Width " + Arrays.toString(validValue)));
        final JTextField pixelizeTextField = new JTextField(String.valueOf(DEFAULT_PIXEL_SIZE), TEXT_FIELD_SIZE);
        pixelizePanel.add(pixelizeTextField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int pixelWidth = Integer.parseInt(pixelizeTextField.getText());
                    for (int i = 0; i < validValue.length; i++) {
                        if (pixelWidth == validValue[i]) {
                            value = pixelWidth;
                            status = SUCCESS;
                            setVisible(false);
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(PixelizeDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(PixelizeDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pixelizePanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        pixelizePanel.add(cancelButton);
        mainPanel.add(pixelizePanel);
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
                pixelizeTextField.setText(String.valueOf(DEFAULT_PIXEL_SIZE));
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

    public int getValue() {
        return value;
    }
}
