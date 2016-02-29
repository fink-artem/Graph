package ru.nsu.fit.g13205.fink.life;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class NewDocumentDialog extends JDialog {

    private final int WIDTH = 250;
    private final int HEIGHT = 110;
    private final int TEXT_FIELD_SIZE = 5;
    private final Options options;

    public NewDocumentDialog(final Options options, final InitMainWindow initMainWindow) {
        super(new JFrame(), true);
        this.options = options;
        setTitle("New document");
        setResizable(false);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        JPanel mainPanel = new JPanel();

        JPanel fieldPropertiesPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        fieldPropertiesPanel.add(new JLabel("Rows"));
        final JTextField rowsTextField = new JTextField(String.valueOf(options.getRowNumber()), TEXT_FIELD_SIZE);
        fieldPropertiesPanel.add(rowsTextField);
        final JTextField columnsTextField = new JTextField(String.valueOf(options.getColumnNumber()), TEXT_FIELD_SIZE);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(rowsTextField.getText());
                    int m = Integer.parseInt(columnsTextField.getText());
                    if (n >= Options.MIN_ROW_NUMBER && m >= Options.MIN_COLUMN_NUMBER && n <= Options.MAX_ROW_NUMBER && m <= Options.MAX_COLUMN_NUMBER) {
                        options.setRowNumber(n);
                        options.setColumnNumber(m);
                        initMainWindow.createNewDocument();
                    } else {
                        JOptionPane.showMessageDialog(NewDocumentDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    setVisible(false);
                } catch (NumberFormatException me) {
                    JOptionPane.showMessageDialog(NewDocumentDialog.this, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fieldPropertiesPanel.add(okButton);
        fieldPropertiesPanel.add(new JLabel("Columns"));
        fieldPropertiesPanel.add(columnsTextField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });
        fieldPropertiesPanel.add(cancelButton);
        mainPanel.add(fieldPropertiesPanel);
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
                rowsTextField.setText(String.valueOf(options.getRowNumber()));
                columnsTextField.setText(String.valueOf(options.getColumnNumber()));
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }

}
