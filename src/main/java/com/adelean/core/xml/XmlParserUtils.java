package com.adelean.core.xml;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlParserUtils {
    public static String readCharacters(XMLStreamReader reader) throws XMLStreamException {
        StringBuilder result = new StringBuilder();
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.CHARACTERS) {
                result.append(reader.getText().trim());
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                break;
            }
        }
        return result.toString();
    }
}
