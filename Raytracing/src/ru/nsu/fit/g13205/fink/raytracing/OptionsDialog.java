package ru.nsu.fit.g13205.fink.raytracing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

public final class OptionsDialog extends JDialog {

    private final int DIALOG_WIDTH = 380;
    private final int DIALOG_HEIGHT = 180;
    private final int SPINNER_WIDTH = 80;
    private final int SPINNER_HEIGHT = 20;

    public OptionsDialog(JFrame frame, final Data data, InitView initView) {
        super(frame, true);
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - DIALOG_WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        JPanel mainPanel = new JPanel();
        JPanel optionsPanel = new JPanel(new GridLayout(4, 4));

        JPanel dPanel = new JPanel();
        dPanel.add(new JLabel("Depth "));
        JSpinner dSpinner = new JSpinner(new SpinnerNumberModel(data.getDepth(), 1, 10, 1));
        dSpinner.addChangeListener((ChangeEvent e) -> {
            data.setDepth((int) dSpinner.getValue());
            initView.draw();
        });
        dSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        dPanel.add(dSpinner);
        optionsPanel.add(dPanel);

        JPanel znPanel = new JPanel();
        znPanel.add(new JLabel("zn"));
        JSpinner znSpinner = new JSpinner(new SpinnerNumberModel(data.getZn(), 0, 100, 1));
        znSpinner.addChangeListener((ChangeEvent e) -> {
            data.setZn((double) znSpinner.getValue());
            initView.draw();
        });
        znSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        znPanel.add(znSpinner);
        optionsPanel.add(znPanel);

        JPanel gPanel = new JPanel();
        gPanel.add(new JLabel("Gamma"));
        JSpinner gSpinner = new JSpinner(new SpinnerNumberModel(data.getGamma(), 0, 10, 0.1));
        gSpinner.addChangeListener((ChangeEvent e) -> {
            data.setGamma((double) gSpinner.getValue());
            initView.draw();
        });
        gSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        gPanel.add(gSpinner);
        optionsPanel.add(gPanel);

        JPanel zfPanel = new JPanel();
        zfPanel.add(new JLabel("zf"));
        JSpinner zfSpinner = new JSpinner(new SpinnerNumberModel(data.getZf(), 0, 100, 1));
        zfSpinner.addChangeListener((ChangeEvent e) -> {
            data.setZf((double) zfSpinner.getValue());
            initView.draw();
        });
        zfSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        zfPanel.add(zfSpinner);
        optionsPanel.add(zfPanel);

        JPanel qPanel = new JPanel();
        qPanel.add(new JLabel("Quality"));
        Quality[] quality = {Quality.ROUGH, Quality.NORMAL, Quality.FINE};
        JComboBox<Quality> qComboBox = new JComboBox<>(quality);
        qComboBox.addActionListener((ActionEvent e) -> {
            data.setQuality((Quality) qComboBox.getSelectedItem());
        });
        qComboBox.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        qPanel.add(qComboBox);
        optionsPanel.add(qPanel);

        JPanel swPanel = new JPanel();
        swPanel.add(new JLabel("sw"));
        JSpinner swSpinner = new JSpinner(new SpinnerNumberModel(data.getSw(), 0, 1000, 1));
        swSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSw((double) swSpinner.getValue());
            initView.draw();
        });
        swSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        swPanel.add(swSpinner);
        optionsPanel.add(swPanel);

        JPanel colorPanel = new JPanel();
        JButton colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        colorButton.setBackground(data.getBackground());
        colorButton.setContentAreaFilled(false);
        colorButton.setOpaque(true);
        colorButton.addActionListener((ActionEvent actionEvent) -> {
            Color initialBackground = colorButton.getBackground();
            Color background = JColorChooser.showDialog(null, "Change color", initialBackground);
            if (background != null) {
                colorButton.setBackground(background);
                data.setBackground(background);
                initView.draw();
            }
        });
        colorPanel.add(colorButton);
        optionsPanel.add(colorPanel);

        JPanel shPanel = new JPanel();
        shPanel.add(new JLabel("sh"));
        JSpinner shSpinner = new JSpinner(new SpinnerNumberModel(data.getSh(), 0, 1000, 1));
        shSpinner.addChangeListener((ChangeEvent e) -> {
            data.setSh((double) shSpinner.getValue());
            initView.draw();
        });
        shSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        shPanel.add(shSpinner);
        optionsPanel.add(shPanel);

        mainPanel.add(optionsPanel);

        JPanel buttonPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent ae) -> dispose());
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel);
        add(mainPanel);
    }

}
