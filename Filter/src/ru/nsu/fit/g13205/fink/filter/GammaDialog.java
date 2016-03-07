package ru.nsu.fit.g13205.fink.filter;

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

public final class GammaDialog extends JDialog {

    private final int WIDTH = 250;
    private final int HEIGHT = 100;
    private final int TEXT_FIELD_SIZE = 5;
    private final double DEFAULT_GAMMA = 1.0;
    private final double MIN_GAMMA = 0.005;
    private final double MAX_GAMMA = 10.0;

    public GammaDialog(final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        setTitle("Gamma");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel gammaPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        gammaPanel.add(new JLabel("Gamma(" + MIN_GAMMA + " - " + MAX_GAMMA + ")"));
        final JTextField gammaTextField = new JTextField(String.valueOf(DEFAULT_GAMMA), TEXT_FIELD_SIZE);
        gammaPanel.add(gammaTextField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double gamma = Double.parseDouble(gammaTextField.getText());
                    if (gamma >= MIN_GAMMA && gamma <= MAX_GAMMA) {
                        initMainWindow.gammaCorrection(gamma);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(GammaDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(GammaDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gammaPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        gammaPanel.add(cancelButton);
        mainPanel.add(gammaPanel);
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
                gammaTextField.setText(String.valueOf(DEFAULT_GAMMA));
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

}
