package com.adelean.repository;


import com.adelean.core.ElasticSearchClient;
import com.adelean.core.jackson.JacksonObjectMapper;
import com.adelean.xmlReader.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;

public class ElasticProductRepository {
    private static final String PRODUCTS_INDEX = "products";
    private static final String BULK_ENDPOINT = "/_bulk";

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public ElasticProductRepository() {
        this.objectMapper = JacksonObjectMapper.get();
        this.restClient = ElasticSearchClient.getClient();
    }

    public void save(List<Product> products) {

        String bulkRequestBody = buildBulkRequest(products);

        Request request = new Request("POST", BULK_ENDPOINT);

        request.setJsonEntity(bulkRequestBody);

        try {
            Response response = restClient.performRequest(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Bulk index failed: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (IOException e) {
            throw new PersistenceException("Failed to write products to Elastic", e);
        }
    }


    private String buildBulkRequest(List<Product> products) {

        StringBuilder bulkRequestBody = new StringBuilder();

        for (Product prd : products) {
            ObjectNode indexMetadata = objectMapper.createObjectNode();

            indexMetadata.putObject("index")
                    .put("_index", PRODUCTS_INDEX)
                    .put("_id", prd.getId());

            try {
                bulkRequestBody.append(objectMapper.writeValueAsString(indexMetadata))
                        .append("\n")
                        .append(objectMapper.writeValueAsString(prd))
                        .append("\n");
            } catch (JsonProcessingException e) {
                throw new PersistenceException("Failed to serialize the object  " + prd + "with index " + indexMetadata, e);
            }

        }

        return bulkRequestBody.toString();
    }
}

