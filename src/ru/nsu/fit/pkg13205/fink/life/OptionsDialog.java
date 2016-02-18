package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Toolkit;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsDialog extends JDialog {

    private final int WIDTH = 400;
    private final int HEIGHT = 300;
    private final int NUMBER_RADIO_BUTTONS = 2;

    public OptionsDialog() {
        super(new JFrame(), true);
        setTitle("Options");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel jPanel = new JPanel();

        JRadioButton[] radioButtons = new JRadioButton[NUMBER_RADIO_BUTTONS];
        radioButtons[0] = new JRadioButton("Replace");
        radioButtons[1] = new JRadioButton("XOR");
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < NUMBER_RADIO_BUTTONS; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);
        for (int i = 0; i < NUMBER_RADIO_BUTTONS; i++) {
            jPanel.add(radioButtons[i]);
        }
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        jPanel.add(okButton);
        jPanel.add(cancelButton);
        add(jPanel);
    }

}
