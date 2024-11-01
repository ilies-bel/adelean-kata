package com.adelean;

import com.adelean.Reader.ProductXmlReader;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {

        // dezip the file

        // read the file using java stax
        Path filePath = Path.of("src/main/resources/xml.xml");

        ProductXmlReader productXmlReader = new ProductXmlReader();
        var products = productXmlReader.readXml(filePath);

        // load the file into elastic adding indexes


    }
}

