
package ru.nsu.fit.g13205.fink.life;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

public class CloseWindowListener implements WindowListener {
    
    private final InitMainWindow frame;

    public CloseWindowListener(InitMainWindow frame) {
        this.frame = frame;
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        Object[] options = {"Save", "Don't save"};
        int answer = JOptionPane.showOptionDialog(frame, "Do you want to save?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (answer == 0) {
            frame.onSave();
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

}
