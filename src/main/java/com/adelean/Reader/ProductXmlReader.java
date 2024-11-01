package com.adelean.Reader;

import com.adelean.Reader.models.Product;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductXmlReader {
    public List<Product> readXml(Path filePath) throws FileNotFoundException, XMLStreamException {


        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath.toFile()));

        List<Product> products = new ArrayList<>();

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {
                String elementName = reader.getLocalName();
                if (elementName.equals("product")) {
                    products.add(Product.fromXml(reader));
                }
            }
        }

        reader.close();

        return products;
    }
}
