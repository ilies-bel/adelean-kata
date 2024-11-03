package com.adelean.xmlReader;

import com.adelean.core.xml.XmlReadingException;
import com.adelean.core.xml.XmlStreamReader;
import com.adelean.xmlReader.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ProductXmlReader {
    private static final String PRODUCT_ELEMENT = "product";
    Logger logger = LoggerFactory.getLogger(ProductXmlReader.class);

    public List<Product> read(Path filePath) {
        logger.info("Reading XML file: {}", filePath);
        List<Product> products;

        try (FileInputStream fileStream = new FileInputStream(filePath.toFile());
        ) {

            XMLStreamReader reader = getXmlReader(fileStream);
            products = parseProducts(reader);

            logger.info("Read {} products", products.size());

            reader.close();
        } catch (IOException | XMLStreamException e) {
            throw new XmlReadingException(
                    String.format("Failed to read XML file: %s", filePath), e);
        }

        return products;
    }

    private XMLStreamReader getXmlReader(FileInputStream fileStream)
            throws XMLStreamException {
        return XmlStreamReader.INSTANCE.getNewReader(fileStream);
    }

    private List<Product> parseProducts(XMLStreamReader reader)
            throws XMLStreamException {
        List<Product> products = new LinkedList<>();

        while (reader.hasNext()) {
            if (isProductElement(reader)) {
                products.add(Product.fromXml(reader));
            }
            reader.next();
        }

        return products;
    }

    private boolean isProductElement(XMLStreamReader reader) {
        return reader.getEventType() == XMLStreamConstants.START_ELEMENT
                && PRODUCT_ELEMENT.equals(reader.getLocalName());
    }
}

