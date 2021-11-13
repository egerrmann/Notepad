import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditMenu implements ActionListener {
    GUI gui;
    JLabel findLabel, replaceWithLabel;
    JTextField findTextField, replaceWithTextField;
    JButton findNextButton, findPreviousButton, replaceNextButton, replaceAllButton;

    public EditMenu(GUI gui) {
        this.gui = gui;
    }

    public void findAndReplace() {
        gui.createFindAndReplaceWindow();
        gui.findAndReplaceWindow.setVisible(true);

        findLabel = new JLabel("Find");
        findLabel.setText("Find:");
        findLabel.setBounds(30, 30, 50, 15);
        gui.findAndReplaceWindow.add(findLabel);

        replaceWithLabel = new JLabel("Replace with");
        replaceWithLabel.setText("Replace with:");
        replaceWithLabel.setBounds(30, 60, 100, 15);
        gui.findAndReplaceWindow.add(replaceWithLabel);

        findTextField = new JTextField();
        findTextField.setBounds(140, 30, 150, 20);
        gui.findAndReplaceWindow.add(findTextField);

        replaceWithTextField = new JTextField();
        replaceWithTextField.setBounds(140, 60, 150, 20);
        gui.findAndReplaceWindow.add(replaceWithTextField);

        findNextButton = new JButton("Find Next");
        findNextButton.setBounds(30, 110, 125, 20);
        findNextButton.addActionListener(this);
        findNextButton.setActionCommand("Find next");
        gui.findAndReplaceWindow.add(findNextButton);

        findPreviousButton = new JButton("Find Previous");
        findPreviousButton.setBounds(30, 140, 125, 20);
        findPreviousButton.addActionListener(this);
        findPreviousButton.setActionCommand("Find previous");
        gui.findAndReplaceWindow.add(findPreviousButton);

        replaceNextButton = new JButton("Replace Next");
        replaceNextButton.setBounds(165, 110, 125, 20);
        replaceNextButton.addActionListener(this);
        replaceNextButton.setActionCommand("Replace next");
        gui.findAndReplaceWindow.add(replaceNextButton);

        replaceAllButton = new JButton("Replace All");
        replaceAllButton.setBounds(165, 140, 125, 20);
        replaceAllButton.addActionListener(this);
        replaceAllButton.setActionCommand("Replace all");
        gui.findAndReplaceWindow.add(replaceAllButton);
    }

    public void findNext(String regex) {
        String data = gui.textArea.getText();

        int cursorPosition = gui.textArea.getSelectionEnd();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find(cursorPosition)) {
            gui.textArea.setSelectionStart(matcher.start());
            gui.textArea.setSelectionEnd(matcher.end());
        }
        else JOptionPane.showMessageDialog(new JFrame(), "No any matches", "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    public void findPrevious(String regex) {
        /*int cursorPosition = gui.textArea.getSelectionEnd();
        String data = gui.textArea.getText();
        StringBuilder dataBeforeCursor = new StringBuilder(cursorPosition);
        dataBeforeCursor.setCharAt(0, 'a');
        int a = dataBeforeCursor.charAt(0);
        for (int i = 0; i < cursorPosition; i++) {
            dataBeforeCursor.setCharAt(i, data.charAt(i));
        }
        data = dataBeforeCursor.toString();*/ // not working (why???)

        int cursorPosition = gui.textArea.getSelectionStart();
        String data = gui.textArea.getText();
        StringBuilder dataBeforeCursor = new StringBuilder(data);
        dataBeforeCursor.delete(cursorPosition, data.length());
        data = dataBeforeCursor.toString();

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        int amountOfMatches = (int) matcher.results().count();
        if (amountOfMatches == 0)
            JOptionPane.showMessageDialog(new JFrame(), "No any matches", "Error", JOptionPane.INFORMATION_MESSAGE);
        else {
            matcher.find(0);
            for (int i = 1; i < amountOfMatches; i++) {
                matcher.find();
            }
            gui.textArea.setSelectionStart(matcher.start());
            gui.textArea.setSelectionEnd(matcher.end());
        }
    }

    public void replaceNext(String regexFind, String regexReplace) {
        String data = gui.textArea.getText();

        int cursorPosition = gui.textArea.getSelectionStart();
        Pattern pattern = Pattern.compile(regexFind, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find(cursorPosition)) {
            if (cursorPosition != matcher.start()) {
                gui.textArea.setSelectionStart(matcher.start());
                gui.textArea.setSelectionEnd(matcher.end());
            }
            else {
                StringBuilder editedData = new StringBuilder(data);

                editedData.delete(cursorPosition, cursorPosition + regexReplace.length());
                editedData.insert(cursorPosition, regexReplace);
                data = editedData.toString();

                gui.textArea.setText(data);
                gui.textArea.setSelectionStart(cursorPosition);
                gui.textArea.setSelectionEnd(cursorPosition + regexReplace.length());
            }
        }
        else JOptionPane.showMessageDialog(new JFrame(), "No any matches", "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    public void replaceAll(String regexFind, String regexReplace) {
        String data = gui.textArea.getText();

        Pattern pattern = Pattern.compile(regexFind, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        int amountOfMatches = (int) matcher.results().count();

        if (amountOfMatches != 0) {
            for (int i = 0; i < amountOfMatches; i++) {
                matcher = pattern.matcher(data);
                matcher.find();

                StringBuilder editedData = new StringBuilder(data);
                editedData.delete(matcher.start(), matcher.end());
                editedData.insert(matcher.start(), regexReplace);
                data = editedData.toString();
            }

            gui.textArea.setText(data);
            gui.textArea.setSelectionStart(matcher.start());
            gui.textArea.setSelectionEnd(matcher.start() + regexReplace.length());
        }
        else JOptionPane.showMessageDialog(new JFrame(), "No any matches", "Error", JOptionPane.INFORMATION_MESSAGE);
    }

        @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        switch (command) {
            case "Find next" -> findNext(findTextField.getText());
            case "Find previous" -> findPrevious(findTextField.getText());
            case "Replace next" -> replaceNext(findTextField.getText(), replaceWithTextField.getText());
            case "Replace all" -> replaceAll(findTextField.getText(), replaceWithTextField.getText());
        }
    }
}
