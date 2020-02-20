package run;

import javafx.scene.control.TextField;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addTextListener;

public class LimitedTextField extends TextField implements TextListener, KeyListener {

    private int limit;
    private int previousCaretPosition;
    private String previousValidText = "";

    public LimitedTextField() {
        this(-1);
    }

    public LimitedTextField(int limit) {
        this.limit = limit;
        addTextListener(this);
        addKeyListener(this);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        previousCaretPosition = getCaretPosition();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void textValueChanged(TextEvent textEvent) {
        String t = getText();
        if (getLimit() >= 0 && t.length() > limit) {
            setText(previousValidText);
            setLimit(previousCaretPosition);
        }
        else {
            previousValidText = t;
        }
    }
}