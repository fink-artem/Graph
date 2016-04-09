package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class OptionsDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int DIALOG_WIDTH = 550;
    private final int DIALOG_HEIGHT = 500;
    private final int IMAGE_WIDTH = DIALOG_WIDTH;
    private final int IMAGE_HEIGHT = 350;
    private final int WIDTH_HEADER = 26;
    private final int TEXT_FIELD_SIZE = 5;
    private final Model model;
    private boolean status = false;
    private BufferedImage image;

    public OptionsDialog(JFrame frame, final Model model) {
        super(frame, true);
        this.model = model;
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        setLayout(null);
        JPanel mainPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> {
        });
        mainPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent ae) -> setVisible(false));
        mainPanel.add(cancelButton);
        add(mainPanel);
        mainPanel.setBounds(0, IMAGE_HEIGHT, DIALOG_WIDTH, DIALOG_HEIGHT - IMAGE_HEIGHT - WIDTH_HEADER);

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
                //todo
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawLine(IMAGE_WIDTH / 2, 0, IMAGE_WIDTH / 2, IMAGE_HEIGHT - 1);
        image.getGraphics().drawLine(0, IMAGE_HEIGHT / 2, IMAGE_WIDTH - 1, IMAGE_HEIGHT / 2);
        //Drawer.drawSplain(image, model.data, 0);
        g.drawImage(image, 0, WIDTH_HEADER, this);
    }

    public boolean getStatus() {
        return status;
    }
}
