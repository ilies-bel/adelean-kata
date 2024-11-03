package com.adelean;

import com.adelean.repository.ElasticRepository;
import com.adelean.xmlReader.ProductXmlReader;

public class Main {
    public static void main(String[] args) {

        // unzip the file
        var filePath = new ProductZipService().unzipFolder();


        // read the content and map it to objects
        ProductXmlReader productXmlReader = new ProductXmlReader();
        var products = productXmlReader.read(filePath);


        // load the file into elastic adding indexes
        ElasticRepository elasticRepository = new ElasticRepository();
        elasticRepository.save(products);
    }
}

