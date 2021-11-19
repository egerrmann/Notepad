import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotkeysHandler implements KeyListener {
    GUI gui;
    FileMenu fileMenu;
    EditMenu editMenu;

    public HotkeysHandler(GUI gui) {
        this.gui = gui;
        fileMenu = new FileMenu(gui);
        editMenu = new EditMenu(gui);
    }

    public void ctrlD() {
        int startOfLine = getStartOfCurrentLine();
        int endOfLine = getEndOfCurrentLine();
        String lineToDuplicate = gui.textArea.getText().substring(startOfLine, endOfLine + 1);
        if (endOfLine + 1 == gui.textArea.getText().length() && gui.textArea.getText().charAt(endOfLine) != '\n') {
            lineToDuplicate = '\n' + lineToDuplicate;
        }
        String newData = gui.textArea.getText().substring(0, endOfLine + 1)
                + lineToDuplicate
                + gui.textArea.getText().substring(endOfLine + 1);
        int caretPosition = gui.textArea.getCaretPosition();
        gui.textArea.setText(newData);
        gui.textArea.setCaretPosition(caretPosition + lineToDuplicate.length());
    }

    public void ctrlX() {
        int startOfLine = getStartOfCurrentLine();
        int endOfLine = getEndOfCurrentLine();
        String newData = gui.textArea.getText().substring(0, startOfLine)
                + gui.textArea.getText().substring(endOfLine + 1);
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
        int caretPosition, initialCaretPosition = gui.textArea.getCaretPosition();
        int startOfCurrentLine = getStartOfCurrentLine();
        int endOfCurrentLine = getEndOfCurrentLine();
        if (startOfCurrentLine != 0) {
            caretPosition = startOfCurrentLine - 1;
            gui.textArea.setCaretPosition(caretPosition);
            int startOfPreviousLine = getStartOfCurrentLine();
            int endOfPreviousLine = getEndOfCurrentLine();
            String dataBeforeTwoLines = gui.textArea.getText().substring(0, startOfPreviousLine);
            String dataOfFirstLine = gui.textArea.getText().substring(startOfPreviousLine, endOfPreviousLine + 1);
            String dataOfSecondLine = gui.textArea.getText().substring(startOfCurrentLine, endOfCurrentLine + 1);
            if (endOfCurrentLine + 1 == gui.textArea.getText().length()
                    && gui.textArea.getText().charAt(endOfCurrentLine) != '\n')
                dataOfSecondLine += '\n';
            String dataAfterTwoLines = gui.textArea.getText().substring(endOfCurrentLine + 1);
            String data = dataBeforeTwoLines + dataOfSecondLine + dataOfFirstLine + dataAfterTwoLines;
            gui.textArea.setText(data);
            gui.textArea.setCaretPosition(initialCaretPosition - dataOfFirstLine.length());
        }
    }

    public void ctrlShiftDown() {
        int caretPosition, initialCaretPosition = gui.textArea.getCaretPosition();
        int startOfCurrentLine = getStartOfCurrentLine();
        int endOfCurrentLine = getEndOfCurrentLine();
        if (endOfCurrentLine + 1 != gui.textArea.getText().length()) {
            caretPosition = endOfCurrentLine + 1;
            gui.textArea.setCaretPosition(caretPosition);
            int startOfNextLine = getStartOfCurrentLine();
            int endOfNextLine = getEndOfCurrentLine();
            String dataBeforeTwoLines = gui.textArea.getText().substring(0, startOfCurrentLine);
            String dataOfFirstLine = gui.textArea.getText().substring(startOfCurrentLine, endOfCurrentLine + 1);
            String dataOfSecondLine = gui.textArea.getText().substring(startOfNextLine, endOfNextLine + 1);
            if (endOfNextLine + 1 == gui.textArea.getText().length()
                    && gui.textArea.getText().charAt(endOfNextLine) != '\n')
                dataOfSecondLine += '\n';
            String dataAfterTwoLines = gui.textArea.getText().substring(endOfNextLine + 1);
            String data = dataBeforeTwoLines + dataOfSecondLine + dataOfFirstLine + dataAfterTwoLines;
            gui.textArea.setText(data);
            gui.textArea.setCaretPosition(initialCaretPosition + dataOfSecondLine.length());
        }
    }

    public int getStartOfCurrentLine() {
        int caretPosition = gui.textArea.getCaretPosition();
        if (caretPosition == 0) {
            return 0;
        }
        else if (caretPosition == gui.textArea.getText().length()) {
            caretPosition--;
            if (gui.textArea.getText().charAt(caretPosition) == '\n')
                return ++caretPosition;
        }
        while (caretPosition != 0 && gui.textArea.getText().charAt(caretPosition - 1) != '\n')
            caretPosition--;
        return caretPosition;
    }

    public int getEndOfCurrentLine() { // returns index of last element of line
        int caretPosition = gui.textArea.getCaretPosition();
        if (caretPosition == gui.textArea.getText().length())
            caretPosition--;
        while (caretPosition != gui.textArea.getText().length() - 1
                && gui.textArea.getText().charAt(caretPosition) != '\n')
            caretPosition++;
        return caretPosition;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Ctrl+D - duplicate line
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D)
            ctrlD();

        // Ctrl+X - delete line
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X)
            ctrlX();

        // Ctrl+UpArrow - move scroll bar
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_UP)
            ctrlUp();

        // Ctrl+DownArrow - move scroll bar
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DOWN)
            ctrlDown();

        // Ctrl+Shift+UpArrow - swap lines
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_UP)
            ctrlShiftUp();

        // Ctrl+Shift+DownArrow - swap lines
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_DOWN)
            ctrlShiftDown();

        // Ctrl+N - new file
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N)
            fileMenu.newFile();

        // Ctrl+S - save file
        if (e.isControlDown() && !e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_S)
            fileMenu.save();

        // Ctrl+Shift+S - save file as
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_S)
            fileMenu.saveAs();

        // Ctrl+O - open file
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O)
            fileMenu.open();

        // Ctrl+F, Ctrl+R - find and replace
        if ((e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F)
                || (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R))
            gui.edit.findAndReplace();

        // Ctrl+Z - undo
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z)
            gui.edit.undo();

        // Ctrl+Y - redo
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y)
            gui.edit.redo();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
