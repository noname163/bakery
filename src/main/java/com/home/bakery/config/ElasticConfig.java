package com.home.bakery.config;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.mappers.ProductMapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ElasticConfig {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("dat", "admin12345"));
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
        JsonpMapper jsonMapper = new JsonbJsonpMapper();
        ElasticsearchTransport transport = new RestClientTransport(restClient, jsonMapper);

        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

    @Bean
    public void bulkData() throws ElasticsearchException, IOException {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = productMapper.mapProductsToProductResponses(products);
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (ProductResponse product : productResponses) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("productixdex")
                            .id(String.valueOf(product.getId()))
                            .document(product)));
        }

        ElasticsearchClient elasticsearchClient = elasticsearchClient();
        BulkResponse result = elasticsearchClient.bulk(br.build());

        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }

}
