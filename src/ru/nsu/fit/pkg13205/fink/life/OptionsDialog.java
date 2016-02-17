package ru.nsu.fit.pkg13205.fink.life;

import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class OptionsDialog extends JDialog {

    private final int WIDTH = 400;
    private final int HEIGHT = 300;

    public OptionsDialog() {
        super(new JFrame(), true);
        setTitle("Options");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
    }

}
