import java.io.IOException;

public class OutputToFile {
    private java.io.FileWriter output;

    public OutputToFile(String filename) {
        try {
            output = new java.io.FileWriter(filename);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void stringsToFile(String[] strings) {
        try {
            for (int i = 0; i < strings.length; ++i) {
                output.write(strings[i] + "\n");
            }
            output.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
