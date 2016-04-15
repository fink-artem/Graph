package ru.nsu.fit.g13205.fink.wireframe;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class OptionsDialog extends JDialog {

    public static final boolean SUCCESS = true;
    public static final boolean FAILED = false;
    private final int DIALOG_WIDTH = 550;
    private final int DIALOG_HEIGHT = 500;
    private final int SPLAIN_PANEL_WIDTH = DIALOG_WIDTH;
    private final int SPLAIN_PANEL_HEIGHT = 350;
    private final int TEXT_FIELD_SIZE = 5;
    private final Data data;
    private boolean status = false;

    public OptionsDialog(JFrame frame, final Data data, InitView initView) {
        super(frame, true);
        this.data = data;
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        setLayout(null);
        SplainPanel splainPanel = new SplainPanel(data,initView);
        JPanel optionsPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> {
        });
        optionsPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent ae) -> setVisible(false));
        optionsPanel.add(cancelButton);
        add(splainPanel);
        add(optionsPanel);
        optionsPanel.setBounds(0, SPLAIN_PANEL_HEIGHT, DIALOG_WIDTH, DIALOG_HEIGHT - SPLAIN_PANEL_HEIGHT);
        splainPanel.setBounds(0, 0, SPLAIN_PANEL_WIDTH, SPLAIN_PANEL_HEIGHT);

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

    public boolean getStatus() {
        return status;
    }
}
