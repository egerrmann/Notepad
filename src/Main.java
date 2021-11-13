import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        SaxXmlParser saxXmlParser = new SaxXmlParser();
        System.out.println("\n\n");
        DomXmlParser domxmlParser = new DomXmlParser();
        String fomParserData = domxmlParser.getData();
        FileEditor file = new FileEditor("students.xml");
        GUI gui = new GUI(fomParserData);

        file.printFileData();

//        file.findWord("34");
//        file.replaceWord("56789", "321");
//        file.inputFromConsole();

        file.exportFileData(file.getData());
    }
}
