public class Main {
    public static void main(String[] args) {
        FileEditor file = new FileEditor("notepad.txt");
        GUI gui = new GUI();

        file.printFileData();

        file.findWord("34");
        file.replaceWord("56789", "321");
        //file.inputFromConsole();

        file.exportFileData(file.getData());
    }
}
