package com.adelean.core;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class ElasticSearchClient {
    private static final RestClient client = RestClient.builder(
            new HttpHost(
                    System.getenv("ES_HOST"),
                    Integer.parseInt(System.getenv("ES_PORT")),
                    System.getenv("ES_SCHEME")
            )
    ).build();

    private ElasticSearchClient() {
    }

    public static RestClient getClient() {
        return client;
    }
}