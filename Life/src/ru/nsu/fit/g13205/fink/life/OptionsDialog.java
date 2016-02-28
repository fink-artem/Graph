package ru.nsu.fit.g13205.fink.life;

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
    private final int HEIGHT = 380;
    private final int NUMBER_RADIO_BUTTONS = 2;
    private final int TEXT_FIELD_SIZE = 5;
    private final Options options;
    private final String REPLACE_COMMAND = "Replace";
    private final String XOR_COMMAND = "XOR";
    private final JRadioButton[] radioButtons;
    private boolean error = false;

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
        final JSlider rowsSlider = new JSlider(JSlider.HORIZONTAL, Options.MIN_ROW_NUMBER, Options.MAX_ROW_NUMBER, options.getRowNumber());
        rowsSlider.setMajorTickSpacing(5);
        rowsSlider.setMinorTickSpacing(1);
        rowsSlider.setPaintLabels(true);
        rowsSlider.setPaintTicks(true);
        rowsSlider.setSnapToTicks(true);
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
                int value = options.getRowNumber();
                try {
                    value = Integer.parseInt(rowsTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    rowsTextField.setText(String.valueOf(value));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < Options.MIN_ROW_NUMBER) {
                    value = Options.MIN_ROW_NUMBER;
                    rowsTextField.setText(String.valueOf(rowsSlider.getMinimum()));
                }
                if (value > Options.MAX_ROW_NUMBER) {
                    value = Options.MAX_ROW_NUMBER;
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
        final JSlider columnsSlider = new JSlider(JSlider.HORIZONTAL, Options.MIN_COLUMN_NUMBER, Options.MAX_COLUMN_NUMBER, options.getColumnNumber());
        columnsSlider.setMajorTickSpacing(5);
        columnsSlider.setMinorTickSpacing(1);
        columnsSlider.setPaintLabels(true);
        columnsSlider.setPaintTicks(true);
        columnsSlider.setSnapToTicks(true);
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
                int value = options.getColumnNumber();
                try {
                    value = Integer.parseInt(columnsTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    columnsTextField.setText(String.valueOf(value));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < Options.MIN_COLUMN_NUMBER) {
                    value = Options.MIN_COLUMN_NUMBER;
                    columnsTextField.setText(String.valueOf(columnsSlider.getMinimum()));
                }
                if (value > Options.MAX_COLUMN_NUMBER) {
                    value = Options.MAX_COLUMN_NUMBER;
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
        final JSlider cellSizeSlider = new JSlider(JSlider.HORIZONTAL, Options.MIN_CELL_SIZE, Options.MAX_CELL_SIZE, options.getCellSize());
        cellSizeSlider.setMajorTickSpacing(10);
        cellSizeSlider.setMinorTickSpacing(1);
        cellSizeSlider.setPaintLabels(true);
        cellSizeSlider.setPaintTicks(true);
        cellSizeSlider.setSnapToTicks(true);
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
                int value = options.getCellSize();
                try {
                    value = Integer.parseInt(cellSizeTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    cellSizeTextField.setText(String.valueOf(value));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < Options.MIN_CELL_SIZE) {
                    value = Options.MIN_CELL_SIZE;
                    cellSizeTextField.setText(String.valueOf(cellSizeSlider.getMinimum()));
                }
                if (value > Options.MAX_CELL_SIZE) {
                    value = Options.MAX_CELL_SIZE;
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
        final JSlider gridWidthSlider = new JSlider(JSlider.HORIZONTAL, Options.MIN_GRID_WIDTH, Options.MAX_GRID_WIDTH, options.getGridWidth());
        gridWidthSlider.setMajorTickSpacing(4);
        gridWidthSlider.setMinorTickSpacing(1);
        gridWidthSlider.setPaintLabels(true);
        gridWidthSlider.setPaintTicks(true);
        gridWidthSlider.setSnapToTicks(true);
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
                int value = options.getGridWidth();
                try {
                    value = Integer.parseInt(gridWidthTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    gridWidthTextField.setText(String.valueOf(value));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (value < Options.MIN_GRID_WIDTH) {
                    value = Options.MIN_GRID_WIDTH;
                    gridWidthTextField.setText(String.valueOf(gridWidthSlider.getMinimum()));
                }
                if (value > Options.MAX_GRID_WIDTH) {
                    value = Options.MAX_GRID_WIDTH;
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
        final JSlider timerSlider = new JSlider(JSlider.HORIZONTAL, 200, 1000, options.getTimer());
        timerSlider.setMajorTickSpacing(200);
        timerSlider.setPaintLabels(true);
        timerSlider.setPaintTicks(true);
        timerSlider.setSnapToTicks(true);
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
                int value = options.getTimer();
                try {
                    value = Integer.parseInt(timerTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    timerTextField.setText(String.valueOf(value));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        fstImpactTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(fstImpactTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    fstImpactTextField.setText(String.valueOf(options.getFstImpact()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        impactPanel.add(fstImpactTextField);
        impactPanel.add(new JLabel("SND_IMPACT"));
        final JTextField sndImpactTextField = new JTextField(String.valueOf(options.getSndImpact()));
        sndImpactTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(sndImpactTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    sndImpactTextField.setText(String.valueOf(options.getSndImpact()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        impactPanel.add(sndImpactTextField);
        modeImpactPanel.add(impactPanel);
        mainPanel.add(modeImpactPanel);

        JPanel environmentPanel = new JPanel(new GridLayout(2, 4, 10, 7));
        environmentPanel.setBorder(BorderFactory.createTitledBorder("Environment"));
        environmentPanel.add(new JLabel("LIVE_BEGIN"));
        final JTextField liveBeginTextField = new JTextField(String.valueOf(options.getLiveBegin()));
        liveBeginTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(liveBeginTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    liveBeginTextField.setText(String.valueOf(options.getLiveBegin()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        environmentPanel.add(liveBeginTextField);
        environmentPanel.add(new JLabel("LIVE_END"));
        final JTextField liveEndTextField = new JTextField(String.valueOf(options.getLiveEnd()));
        liveEndTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(liveEndTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    liveEndTextField.setText(String.valueOf(options.getLiveEnd()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        environmentPanel.add(liveEndTextField);
        environmentPanel.add(new JLabel("BIRTH_BEGIN"));
        final JTextField birthBeginTextField = new JTextField(String.valueOf(options.getBirthBegin()));
        birthBeginTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(birthBeginTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    birthBeginTextField.setText(String.valueOf(options.getBirthBegin()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        environmentPanel.add(birthBeginTextField);
        environmentPanel.add(new JLabel("BIRTH_END"));
        final JTextField birthEndTextField = new JTextField(String.valueOf(options.getBirthEnd()));
        birthEndTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                try {
                    Double.parseDouble(birthEndTextField.getText());
                } catch (NumberFormatException e) {
                    error = true;
                    birthEndTextField.setText(String.valueOf(options.getBirthEnd()));
                    JOptionPane.showMessageDialog(OptionsDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        environmentPanel.add(birthEndTextField);
        mainPanel.add(environmentPanel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (error) {
                    error = false;
                } else {
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
                    initMainWindow.redraw();
                    setVisible(false);
                }
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
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

    public void updateMode() {
        if (options.getPaintMode() == Options.PaintMode.REPLACE) {
            radioButtons[0].setSelected(true);
        } else {
            radioButtons[1].setSelected(true);
        }
    }

}
