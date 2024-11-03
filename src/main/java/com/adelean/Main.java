package com.adelean;

import com.adelean.Reader.ProductXmlReader;
import com.adelean.repository.ElasticRepository;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        // dezip the file

        // read the file using java stax
        Path filePath = Path.of("src/main/resources/xml.xml");

        ProductXmlReader productXmlReader = new ProductXmlReader();
        var products = productXmlReader.read(filePath);

        // load the file into elastic adding indexes

        ElasticRepository elasticRepository = new ElasticRepository();

        elasticRepository.save(products);
    }
}

