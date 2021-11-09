import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotkeysHandler implements KeyListener {
    GUI gui;

    public HotkeysHandler(GUI gui) {
        this.gui = gui;
    }

    public void ctrlD() {
        int startOfLine = getStartOfCurrentLine();
        int endOfLine = getEndOfCurrentLine();
        String lineToDuplicate = gui.textArea.getText().substring(startOfLine, endOfLine);
        if (startOfLine == 0)
            lineToDuplicate = '\n' + lineToDuplicate;
        String newData = gui.textArea.getText().substring(0, endOfLine)
                + lineToDuplicate
                + gui.textArea.getText().substring(endOfLine);
        int caretPosition = gui.textArea.getCaretPosition();
        gui.textArea.setText(newData);
        gui.textArea.setCaretPosition(caretPosition + lineToDuplicate.length());
    }

    public void ctrlX() {
        int startOfLine = getStartOfCurrentLine();
        int endOfLine = getEndOfCurrentLine();
        String newData = gui.textArea.getText().substring(0, startOfLine)
                + gui.textArea.getText().substring(endOfLine);
        gui.textArea.setText(newData);
        gui.textArea.setCaretPosition(startOfLine);
    }

    public void ctrlUp() {
        JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getValue() - gui.textArea.getFont().getSize());
    }

    public void ctrlDown() {
        JScrollBar vertical = gui.scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getValue() + gui.textArea.getFont().getSize());
    }

    public void ctrlShiftUp() {
        int caretPosition;
        int startOfCurrentLine = getStartOfCurrentLine();
        int endOfCurrentLine = getEndOfCurrentLine();
        if (startOfCurrentLine != 0) {
            caretPosition = gui.textArea.getCaretPosition() - endOfCurrentLine + startOfCurrentLine;
            gui.textArea.setCaretPosition(caretPosition);
            int startOfPreviousLine = getStartOfCurrentLine();
            int endOfPreviousLine = getEndOfCurrentLine();
            String dataBeforeTwoLines = gui.textArea.getText().substring(0, startOfPreviousLine);
            String dataOfFirstLine = gui.textArea.getText().substring(startOfPreviousLine, endOfPreviousLine);
            String dataOfSecondLine = gui.textArea.getText().substring(startOfCurrentLine, endOfCurrentLine);
            String dataAfterTwoLines = gui.textArea.getText().substring(endOfCurrentLine);
            String data = dataBeforeTwoLines + dataOfSecondLine + dataOfFirstLine + dataAfterTwoLines;
            gui.textArea.setText(data);
            gui.textArea.setCaretPosition(caretPosition);
        }
    }

    public void ctrlShiftDown() {

    }

    public int getStartOfCurrentLine() {
        int caretPosition = gui.textArea.getCaretPosition();
        if (caretPosition == gui.textArea.getText().length()
                || gui.textArea.getText().charAt(caretPosition) == '\n')
            caretPosition--;
        while (gui.textArea.getText().charAt(caretPosition) != '\n' && caretPosition != 0)
            caretPosition--;
        return caretPosition;
    }

    public int getEndOfCurrentLine() {
        int caretPosition = gui.textArea.getCaretPosition();
        if (caretPosition == gui.textArea.getText().length())
            caretPosition--;
        while (caretPosition != gui.textArea.getText().length()
                && gui.textArea.getText().charAt(caretPosition) != '\n')
            caretPosition++;
        return caretPosition;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D)
            ctrlD();

        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X)
            ctrlX();

        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_UP)
            ctrlUp();

        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DOWN)
            ctrlDown();

        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_UP)
            ctrlShiftUp();

        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_DOWN)
            ctrlShiftDown();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
