import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;

public class FileEditor {
    private final File file;
    private final String FILENAME_;
    private java.io.FileWriter output;
    private Scanner scanner;
    private String[] data;

    private String[] importFileData() {
        int amountOfLines = 0;
        try {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                amountOfLines++;
            }
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        String[] fileData = new String[amountOfLines];
        for (int i = 0; i < fileData.length; i++) {
            fileData[i] = scanner.nextLine();
        }
        return fileData;
    }

    public FileEditor(String filename) {
        FILENAME_ = filename;
        file = new File(filename);
        try {
            scanner = new Scanner(file);
            data = importFileData();
            output = new java.io.FileWriter(filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void exportFileData(String[] strings) {
        try {
            for (String string : strings) {
                output.write(string + "\n");
            }
            output.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void exportFileData(String string) {
        String[] stringArr = new String[1];
        stringArr[0] = string;
        exportFileData(stringArr);
    }

    public void inputFromConsole() {
        Scanner in = new Scanner(System.in);
        System.out.println("input string:");
        data = new String[1];
        data[0] = in.nextLine();
    }

    public void printFileData() {
        System.out.println(FILENAME_ + " data:");
        for (int i = 0; i < getAmountOfLines(); i++) {
            System.out.println(data[i]);
        }
    }

    public void findWord (String regex) {
        for (int i = 0; i < this.getAmountOfLines(); i++) {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(data[i]);
            while (matcher.find()) {
                System.out.print("Line: " + (i + 1) + ", ");
                System.out.print("start index: " + matcher.start() + ", ");
                System.out.print("end index: " + matcher.end() + ". ");
                System.out.println(matcher.group());
            }
        }
    }

    public void replaceWord (String wordToFind, String replaceWith) {
        importFileData();
        for (int i = 0; i < this.getAmountOfLines(); i++) {
            data[i] = data[i].replaceAll(wordToFind, replaceWith);
        }
    }

    public int getAmountOfLines() {
        return data.length;
    }

    public String[] getData() {
        return data;
    }
}
