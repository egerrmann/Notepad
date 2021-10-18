public class Main {
    public static void main(String[] args) {
        FileReader in = new FileReader("input.txt");
        String[] fileData = in.fileDataToStringArray();
        System.out.println("input.txt data:");
        for (String fileDatum : fileData) {
            System.out.println(fileDatum);
        }

        OutputToFile out = new OutputToFile("output.txt");
        out.stringsToFile(fileData);
    }
}
