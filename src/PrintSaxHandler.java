import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class PrintSaxHandler extends DefaultHandler {

    private StringBuilder currentValue = new StringBuilder();

    @Override
    public void startDocument() {
        System.out.println("Start of document");
    }

    @Override
    public void endDocument() {
        System.out.println("End of document");
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) {
        currentValue.setLength(0);

        System.out.printf("Start element: %s%n", qName);

        if (qName.equalsIgnoreCase("student")) {
            String id = attributes.getValue("id");
            System.out.printf("Student id: %s%n", id);
        }

        if (qName.equalsIgnoreCase("scholarship")) {
            String currency = attributes.getValue(0);
            System.out.printf("Currency: %s%n", currency);
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {
        System.out.printf("End Element: %s%n", qName);

        if (qName.equalsIgnoreCase("firstname")) {
            System.out.printf("First name: %s%n", currentValue.toString());
        }

        if (qName.equalsIgnoreCase("lastname")) {
            System.out.printf("Last name: %s%n", currentValue.toString());
        }

        if (qName.equalsIgnoreCase("scholarship")) {
            System.out.printf("Scholarship: %s%n", currentValue.toString());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }
}
