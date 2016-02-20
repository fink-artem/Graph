package ru.nsu.fit.pkg13205.fink.life;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class OptionsDialog extends JDialog {

    private final int WIDTH = 500;
    private final int HEIGHT = 300;
    private final int NUMBER_RADIO_BUTTONS = 2;
    private final int TEXT_FIELD_SIZE = 5;
    private final Options options;
    private final String REPLACE_COMMAND = "Replace";
    private final String XOR_COMMAND = "XOR";
    private final JRadioButton[] radioButtons;

    public OptionsDialog(final Options options, final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        this.options = options;
        setTitle("Options");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel fieldPropertiesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        fieldPropertiesPanel.setBorder(BorderFactory.createTitledBorder("Field Properties"));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        fieldPropertiesPanel.add(new JLabel("Rows"), gridBagConstraints);
        final JTextField rowsTextField = new JTextField(String.valueOf(options.getRowNumber()), TEXT_FIELD_SIZE);
        gridBagConstraints.gridx = 1;
        fieldPropertiesPanel.add(rowsTextField, gridBagConstraints);
        final JSlider rowsSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, options.getRowNumber());
        rowsSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                rowsTextField.setText(String.valueOf(rowsSlider.getValue()));
            }
        });
        rowsTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = Integer.parseInt(rowsTextField.getText());
                if (value < rowsSlider.getMinimum()) {
                    value = rowsSlider.getMinimum();
                    rowsTextField.setText(String.valueOf(rowsSlider.getMinimum()));
                }
                if (value > rowsSlider.getMaximum()) {
                    value = rowsSlider.getMaximum();
                    rowsTextField.setText(String.valueOf(rowsSlider.getMaximum()));
                }
                rowsSlider.setValue(value);
            }
        });
        gridBagConstraints.gridx = 2;
        fieldPropertiesPanel.add(rowsSlider, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        fieldPropertiesPanel.add(new JLabel("Columns"), gridBagConstraints);
        final JTextField columnsTextField = new JTextField(String.valueOf(options.getColumnNumber()), TEXT_FIELD_SIZE);
        gridBagConstraints.gridx = 1;
        fieldPropertiesPanel.add(columnsTextField, gridBagConstraints);
        final JSlider columnsSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, options.getColumnNumber());
        columnsSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                columnsTextField.setText(String.valueOf(columnsSlider.getValue()));
            }
        });
        columnsTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = Integer.parseInt(columnsTextField.getText());
                if (value < columnsSlider.getMinimum()) {
                    value = columnsSlider.getMinimum();
                    columnsTextField.setText(String.valueOf(columnsSlider.getMinimum()));
                }
                if (value > columnsSlider.getMaximum()) {
                    value = columnsSlider.getMaximum();
                    columnsTextField.setText(String.valueOf(columnsSlider.getMaximum()));
                }
                columnsSlider.setValue(value);
            }
        });
        gridBagConstraints.gridx = 2;
        fieldPropertiesPanel.add(columnsSlider, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        fieldPropertiesPanel.add(new JLabel("Cell size"), gridBagConstraints);
        final JTextField cellSizeTextField = new JTextField(String.valueOf(options.getCellSize()), TEXT_FIELD_SIZE);
        gridBagConstraints.gridx = 1;
        fieldPropertiesPanel.add(cellSizeTextField, gridBagConstraints);
        final JSlider cellSizeSlider = new JSlider(JSlider.HORIZONTAL, 3, 150, options.getCellSize());
        cellSizeSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                cellSizeTextField.setText(String.valueOf(cellSizeSlider.getValue()));
            }
        });
        cellSizeTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = Integer.parseInt(cellSizeTextField.getText());
                if (value < cellSizeSlider.getMinimum()) {
                    value = cellSizeSlider.getMinimum();
                    cellSizeTextField.setText(String.valueOf(cellSizeSlider.getMinimum()));
                }
                if (value > cellSizeSlider.getMaximum()) {
                    value = cellSizeSlider.getMaximum();
                    cellSizeTextField.setText(String.valueOf(cellSizeSlider.getMaximum()));
                }
                cellSizeSlider.setValue(value);
            }
        });
        gridBagConstraints.gridx = 2;
        fieldPropertiesPanel.add(cellSizeSlider, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        fieldPropertiesPanel.add(new JLabel("Grid width"), gridBagConstraints);
        final JTextField gridWidthTextField = new JTextField(String.valueOf(options.getGridWidth()), TEXT_FIELD_SIZE);
        gridBagConstraints.gridx = 1;
        fieldPropertiesPanel.add(gridWidthTextField, gridBagConstraints);
        final JSlider gridWidthSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, options.getGridWidth());
        gridWidthSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                gridWidthTextField.setText(String.valueOf(gridWidthSlider.getValue()));
            }
        });
        gridWidthTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = Integer.parseInt(gridWidthTextField.getText());
                if (value < gridWidthSlider.getMinimum()) {
                    value = gridWidthSlider.getMinimum();
                    gridWidthTextField.setText(String.valueOf(gridWidthSlider.getMinimum()));
                }
                if (value > gridWidthSlider.getMaximum()) {
                    value = gridWidthSlider.getMaximum();
                    gridWidthTextField.setText(String.valueOf(gridWidthSlider.getMaximum()));
                }
                gridWidthSlider.setValue(value);
            }
        });
        gridBagConstraints.gridx = 2;
        fieldPropertiesPanel.add(gridWidthSlider, gridBagConstraints);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        fieldPropertiesPanel.add(new JLabel("Timer"), gridBagConstraints);
        final JTextField timerTextField = new JTextField(String.valueOf(options.getTimer()), TEXT_FIELD_SIZE);
        gridBagConstraints.gridx = 1;
        fieldPropertiesPanel.add(timerTextField, gridBagConstraints);
        final JSlider timerSlider = new JSlider(JSlider.HORIZONTAL, 1, 1000, options.getTimer());
        timerSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                timerTextField.setText(String.valueOf(timerSlider.getValue()));
            }
        });
        timerTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                int value = Integer.parseInt(timerTextField.getText());
                if (value < timerSlider.getMinimum()) {
                    value = timerSlider.getMinimum();
                    timerTextField.setText(String.valueOf(timerSlider.getMinimum()));
                }
                if (value > timerSlider.getMaximum()) {
                    value = timerSlider.getMaximum();
                    timerTextField.setText(String.valueOf(timerSlider.getMaximum()));
                }
                timerSlider.setValue(value);
            }
        });
        gridBagConstraints.gridx = 2;
        fieldPropertiesPanel.add(timerSlider, gridBagConstraints);
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

        JPanel modeImpactPanel = new JPanel(new GridLayout(2, 1));
        modeImpactPanel.add(modePanel);

        JPanel impactPanel = new JPanel(new GridLayout(2, 2, 10, 7));
        impactPanel.setBorder(BorderFactory.createTitledBorder("Impact"));
        impactPanel.add(new JLabel("FST_IMPACT"));
        final JTextField fstImpactTextField = new JTextField(String.valueOf(options.getFstImpact()));
        impactPanel.add(fstImpactTextField);
        impactPanel.add(new JLabel("SND_IMPACT"));
        final JTextField sndImpactTextField = new JTextField(String.valueOf(options.getSndImpact()));
        impactPanel.add(sndImpactTextField);
        modeImpactPanel.add(impactPanel);
        mainPanel.add(modeImpactPanel);

        JPanel environmentPanel = new JPanel(new GridLayout(2, 4, 10, 7));
        environmentPanel.setBorder(BorderFactory.createTitledBorder("Environment"));
        environmentPanel.add(new JLabel("LIVE_BEGIN"));
        final JTextField liveBeginTextField = new JTextField(String.valueOf(options.getLiveBegin()));
        environmentPanel.add(liveBeginTextField);
        environmentPanel.add(new JLabel("LIVE_END"));
        final JTextField liveEndTextField = new JTextField(String.valueOf(options.getLiveEnd()));
        environmentPanel.add(liveEndTextField);
        environmentPanel.add(new JLabel("BIRTH_BEGIN"));
        final JTextField birthBeginTextField = new JTextField(String.valueOf(options.getBirthBegin()));
        environmentPanel.add(birthBeginTextField);
        environmentPanel.add(new JLabel("BIRTH_END"));
        final JTextField birthEndTextField = new JTextField(String.valueOf(options.getBirthEnd()));
        environmentPanel.add(birthEndTextField);
        mainPanel.add(environmentPanel);

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
                options.setFstImpact(Double.parseDouble(fstImpactTextField.getText()));
                options.setSndImpact(Double.parseDouble(sndImpactTextField.getText()));
                options.setRowNumber(Integer.parseInt(rowsTextField.getText()));
                options.setColumnNumber(Integer.parseInt(columnsTextField.getText()));
                options.setCellSize(Integer.parseInt(cellSizeTextField.getText()));
                options.setGridWidth(Integer.parseInt(gridWidthTextField.getText()));
                options.setTimer(Integer.parseInt(timerTextField.getText()));
                double liveBegin = Double.parseDouble(liveBeginTextField.getText());
                double liveEnd = Double.parseDouble(liveEndTextField.getText());
                double birthBegin = Double.parseDouble(birthBeginTextField.getText());
                double birthEnd = Double.parseDouble(birthEndTextField.getText());
                if (0 <= liveBegin && liveBegin <= birthBegin && birthBegin <= birthEnd && birthEnd <= liveEnd) {
                    options.setBirthBegin(birthBegin);
                    options.setBirthEnd(birthEnd);
                    options.setLiveBegin(liveBegin);
                    options.setLiveEnd(liveEnd);
                } else {
                    JOptionPane.showMessageDialog(OptionsDialog.this, "LIVE_BEGIN ≤ BIRTH_BEGIN ≤ BIRTH_END ≤ LIVE_END", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                initMainWindow.onInit();
                setVisible(false);
            }
        });
        mainPanel.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                fstImpactTextField.setText(String.valueOf(options.getFstImpact()));
                sndImpactTextField.setText(String.valueOf(options.getSndImpact()));
                rowsTextField.setText(String.valueOf(options.getRowNumber()));
                columnsTextField.setText(String.valueOf(options.getColumnNumber()));
                cellSizeTextField.setText(String.valueOf(options.getCellSize()));
                gridWidthTextField.setText(String.valueOf(options.getGridWidth()));
                timerTextField.setText(String.valueOf(options.getTimer()));
                liveBeginTextField.setText(String.valueOf(options.getLiveBegin()));
                liveEndTextField.setText(String.valueOf(options.getLiveEnd()));
                birthBeginTextField.setText(String.valueOf(options.getBirthBegin()));
                birthEndTextField.setText(String.valueOf(options.getBirthEnd()));
                rowsSlider.setValue(options.getRowNumber());
                columnsSlider.setValue(options.getColumnNumber());
                cellSizeSlider.setValue(options.getCellSize());
                gridWidthSlider.setValue(options.getGridWidth());
                timerSlider.setValue(options.getTimer());
                if (options.getPaintMode() == Options.PaintMode.REPLACE) {
                    radioButtons[0].setSelected(true);
                } else {
                    radioButtons[1].setSelected(true);
                }
                setVisible(false);
            }
        });
        mainPanel.add(cancelButton);

        add(mainPanel);
        
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                fstImpactTextField.setText(String.valueOf(options.getFstImpact()));
                sndImpactTextField.setText(String.valueOf(options.getSndImpact()));
                rowsTextField.setText(String.valueOf(options.getRowNumber()));
                columnsTextField.setText(String.valueOf(options.getColumnNumber()));
                cellSizeTextField.setText(String.valueOf(options.getCellSize()));
                gridWidthTextField.setText(String.valueOf(options.getGridWidth()));
                timerTextField.setText(String.valueOf(options.getTimer()));
                liveBeginTextField.setText(String.valueOf(options.getLiveBegin()));
                liveEndTextField.setText(String.valueOf(options.getLiveEnd()));
                birthBeginTextField.setText(String.valueOf(options.getBirthBegin()));
                birthEndTextField.setText(String.valueOf(options.getBirthEnd()));
                rowsSlider.setValue(options.getRowNumber());
                columnsSlider.setValue(options.getColumnNumber());
                cellSizeSlider.setValue(options.getCellSize());
                gridWidthSlider.setValue(options.getGridWidth());
                timerSlider.setValue(options.getTimer());
                if (options.getPaintMode() == Options.PaintMode.REPLACE) {
                    radioButtons[0].setSelected(true);
                } else {
                    radioButtons[1].setSelected(true);
                }
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
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
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
