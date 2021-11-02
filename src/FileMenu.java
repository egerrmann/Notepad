import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileMenu {
    GUI gui;
    String filename;
    String fileAddress;

    public FileMenu(GUI gui) {
        this.gui = gui;
    }

    public void newFile() {
        gui.textArea.setText("");
        gui.notepadWindow.setTitle("New");
        filename = null;
        fileAddress = null;
    }

    public void open() {
        FileDialog fileDialog = new FileDialog(gui.notepadWindow, "Open", FileDialog.LOAD);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() != null) {
            filename = fileDialog.getFile();
            fileAddress = fileDialog.getDirectory();
            gui.notepadWindow.setTitle(filename);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileAddress + filename));
            gui.textArea.setText("");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                gui.textArea.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred (file isn't opened).");
        }
    }

    public void save() {
        if (filename == null) {
            saveAs();
        }
        else {
            gui.notepadWindow.setTitle(filename);
            try {
                FileWriter fileWriter = new FileWriter(fileAddress + filename);
                fileWriter.write(gui.textArea.getText());
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("An error occurred (file isn't saved.");
            }
        }
    }

    public void saveAs() {
        FileDialog fileDialog = new FileDialog(gui.notepadWindow, "Save", FileDialog.SAVE);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() != null) {
            filename = fileDialog.getFile();
            fileAddress = fileDialog.getDirectory();
            gui.notepadWindow.setTitle(filename);
        }

        try {
            FileWriter fileWriter = new FileWriter(fileAddress + filename);
            fileWriter.write(gui.textArea.getText());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred (file isn't saved.");
        }
    }

    public void exit() {
        System.exit(0);
    }
}
