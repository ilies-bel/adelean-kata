package com.adelean.Reader;

import com.adelean.Reader.models.Product;
import com.adelean.core.xml.XmlReadingException;
import com.adelean.core.xml.XmlStreamReader;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductXmlReader {

    public List<Product> read(Path filePath) {

        List<Product> products = new ArrayList<>();


        try (var fileStream = new FileInputStream(filePath.toFile())) {

            var reader = XmlStreamReader.INSTANCE.getReader(fileStream);

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
        } catch (IOException e) {
            throw new RuntimeException("The file " + filePath + " was not found", e);
        } catch (XMLStreamException e) {
            throw new XmlReadingException(
                    "An error occurred while reading the XML file " + filePath, e);
        }


        return products;
    }
}

