package ru.nsu.fit.pkg13205.fink.life;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionsDialog extends JDialog {

    private final int WIDTH = 400;
    private final int HEIGHT = 300;
    private final int NUMBER_RADIO_BUTTONS = 2;
    private final int TEXT_FIELD_SIZE = 0;
    private final Options options;
    private final String REPLACE_COMMAND = "Replace";
    private final String XOR_COMMAND = "XOR";
    private final JRadioButton[] radioButtons;

    public OptionsDialog(final Options options, final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        this.options = options;
        setTitle("Options");
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel fieldPropertiesPanel = new JPanel();
        fieldPropertiesPanel.setBorder(BorderFactory.createTitledBorder("Field Properties"));
        fieldPropertiesPanel.add(new JLabel("Rows"));
        fieldPropertiesPanel.add(new JLabel("Columns"));
        fieldPropertiesPanel.add(new JLabel("Cell size"));
        fieldPropertiesPanel.add(new JLabel("Grid width"));
        fieldPropertiesPanel.add(new JLabel("Timer"));
        mainPanel.add(fieldPropertiesPanel);

        JPanel modePanel = new JPanel();
        modePanel.setBorder(BorderFactory.createTitledBorder("Mode"));
        radioButtons = new JRadioButton[NUMBER_RADIO_BUTTONS];
        radioButtons[0] = new JRadioButton("Replace");
        radioButtons[0].setActionCommand(REPLACE_COMMAND);
        radioButtons[1] = new JRadioButton("XOR");
        radioButtons[1].setActionCommand(XOR_COMMAND);
        final ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < NUMBER_RADIO_BUTTONS; i++) {
            group.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);
        for (int i = 0; i < NUMBER_RADIO_BUTTONS; i++) {
            modePanel.add(radioButtons[i]);
        }
        mainPanel.add(modePanel);

        JPanel environmentPanel = new JPanel(new GridLayout(2, 4, 10, 7));
        environmentPanel.setBorder(BorderFactory.createTitledBorder("Environment"));
        environmentPanel.add(new JLabel("LIVE_BEGIN"));
        JTextField liveBeginTextField = new JTextField(String.valueOf(options.getLiveBegin()));
        environmentPanel.add(liveBeginTextField);
        environmentPanel.add(new JLabel("LIVE_END"));
        JTextField liveEndTextField = new JTextField(String.valueOf(options.getLiveEnd()));
        environmentPanel.add(liveEndTextField);
        environmentPanel.add(new JLabel("BIRTH_BEGIN"));
        JTextField birthBeginTextField = new JTextField(String.valueOf(options.getBirthBegin()));
        environmentPanel.add(birthBeginTextField);
        environmentPanel.add(new JLabel("BIRTH_END"));
        JTextField birthEndTextField = new JTextField(String.valueOf(options.getBirthEnd()));
        environmentPanel.add(birthEndTextField);
        mainPanel.add(environmentPanel);

        JPanel impactPanel = new JPanel(new GridLayout(2, 2, 10, 7));
        impactPanel.setBorder(BorderFactory.createTitledBorder("Impact"));
        impactPanel.add(new JLabel("FST_IMPACT"));
        JTextField fstImpactTextField = new JTextField(String.valueOf(options.getFstImpact()));
        impactPanel.add(fstImpactTextField);
        impactPanel.add(new JLabel("SND_IMPACT"));
        JTextField sndImpactTextField = new JTextField(String.valueOf(options.getSndImpact()));
        impactPanel.add(sndImpactTextField);
        mainPanel.add(impactPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();

                switch (command) {
                    case REPLACE_COMMAND:
                        initMainWindow.onReplace();
                        break;
                    case XOR_COMMAND:
                        initMainWindow.onXor();
                        break;
                }
                setVisible(false);
            }
        });
        mainPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        mainPanel.add(cancelButton);

        add(mainPanel);
    }

    public Options getOptions() {
        return options;
    }

    public void updateMode() {
        if (options.getPaintMode() == Options.PaintMode.REPLACE) {
            radioButtons[0].setSelected(true);
        } else {
            radioButtons[1].setSelected(true);
        }
    }

}
