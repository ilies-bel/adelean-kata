package com.adelean.core.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;

public enum XmlStreamReader {
    INSTANCE,
    ;

    private final XMLInputFactory factory = XMLInputFactory.newInstance();

    public XMLStreamReader getReader(FileInputStream fileStream) {
        try {
            return factory.createXMLStreamReader(fileStream);
        } catch (XMLStreamException e) {
            throw new IllegalArgumentException("Cannot create XMLStreamReader", e);
        }
    }
}



