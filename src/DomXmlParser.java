import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomXmlParser {

    private String data;

    public DomXmlParser() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new File("students.xml"));
        doc.getDocumentElement().normalize();

        data = "Root element: " + doc.getDocumentElement().getNodeName()
                + '\n' + "--------------------" + '\n';

        NodeList list = doc.getElementsByTagName("student");

        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String id = element.getAttribute("id");

                String firstName = element.getElementsByTagName("firstname").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastname").item(0).getTextContent();

                NodeList scholarshipNodeList = element.getElementsByTagName("scholarship");
                String scholarship = scholarshipNodeList.item(0).getTextContent();
                String currency =
                        scholarshipNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();

                data += "Current element: " + node.getNodeName() + '\n' +
                        "Student id: " + id + '\n' +
                        "First name: " + firstName + '\n' +
                        "Last name: " + lastName + '\n' +
                        "Currency: " + currency + '\n' +
                        "Scholarship [currency]: " + scholarship + " [" + currency + "]\n\n";
            }
        }
    }

    public String getData() {
        return data;
    }
}
