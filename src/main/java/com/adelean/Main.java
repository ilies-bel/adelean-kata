package com.adelean;

import com.adelean.repository.ElasticProductRepository;
import com.adelean.xmlReader.ProductXmlReader;

public class Main {

    public static void main(String[] args) {
        ProductZipService productZipService = new ProductZipService();
        ElasticProductRepository elasticProductRepository = new ElasticProductRepository();
        ProductXmlReader productXmlReader = new ProductXmlReader();


        // unzip the file
        var filePath = productZipService.unzipFolder();

        // read the content and map it to objects
        var products = productXmlReader.read(filePath);

        // load the file into elastic adding indexes
        elasticProductRepository.save(products);
    }
}

