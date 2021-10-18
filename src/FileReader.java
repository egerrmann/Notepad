import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private final File file;
    private Scanner scanner;
    private String[] data;

    public FileReader(String filename) {
        file = new File(filename);
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String[] fileDataToStringArray() {
        int amountOfLinesInFile = 0;
        try {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                amountOfLinesInFile++;
            }
            scanner = new Scanner(file);
            data = new String[amountOfLinesInFile];
            for (int i = 0; i < amountOfLinesInFile; i++) {
                data[i] = scanner.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
}
