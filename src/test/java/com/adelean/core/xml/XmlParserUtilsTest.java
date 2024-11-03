package com.adelean.core.xml;


import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlParserUtilsTest {

    private XMLStreamReader createReaderFromXml(String xml) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        return factory.createXMLStreamReader(new StringReader(xml));
    }

    @Test
    void readCharacters_SimpleText_ReturnsText() throws XMLStreamException {
        // Arrange
        String xml = "<root>Hello World</root>";
        XMLStreamReader reader = createReaderFromXml(xml);
        reader.next();

        // Act
        String result = XmlParserUtils.readCharacters(reader);

        // Assert
        assertEquals("Hello World", result);
    }

    @Test
    void readCharacters_EmptyElement_ReturnsEmptyString() throws XMLStreamException {
        // Arrange
        String xml = "<root></root>";
        XMLStreamReader reader = createReaderFromXml(xml);
        reader.next(); // Move to START_ELEMENT

        // Act
        String result = XmlParserUtils.readCharacters(reader);

        // Assert
        assertEquals("", result);
    }

    @Test
    void readCharacters_WhitespaceText_ReturnsTrimmedText() throws XMLStreamException {
        // Arrange
        String xml = "<root>  \n  Hello World  \t  </root>";
        XMLStreamReader reader = createReaderFromXml(xml);
        reader.next(); // Move to START_ELEMENT

        // Act
        String result = XmlParserUtils.readCharacters(reader);

        // Assert
        assertEquals("Hello World", result);
    }

    @Test
    void readCharacters_MultipleTextNodes_ConcatenatesText() throws XMLStreamException {
        // Arrange
        String xml = "<root>Hello<!-- comment -->World</root>";
        XMLStreamReader reader = createReaderFromXml(xml);
        reader.next(); // Move to START_ELEMENT

        // Act
        String result = XmlParserUtils.readCharacters(reader);

        // Assert
        assertEquals("HelloWorld", result);
    }

    @Test
    void readCharacters_WithXmlStreamException_PropagatesException() {
        // Arrange
        String malformedXml = "<root>Hello<unclosed>";

        // Act & Assert
        assertThrows(XMLStreamException.class, () -> {
            XMLStreamReader reader = createReaderFromXml(malformedXml);
            reader.next(); // Move to START_ELEMENT
            XmlParserUtils.readCharacters(reader);
        });
    }
}