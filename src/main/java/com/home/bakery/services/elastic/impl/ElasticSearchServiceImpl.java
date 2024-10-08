package com.home.bakery.services.elastic.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.home.bakery.data.constans.ElasticIndex;
import com.home.bakery.data.dto.response.CategoryResponse;
import com.home.bakery.data.dto.response.ProductResponse;
import com.home.bakery.data.entities.Product;
import com.home.bakery.data.repositories.ProductRepository;
import com.home.bakery.mappers.ProductMapper;
import com.home.bakery.services.elastic.ElasticSearchService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest.Builder;
import co.elastic.clients.elasticsearch.core.ExistsRequest;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Value("${elasticsearch.username}")
    private String elasticUserName;
    @Value("${elasticsearch.password}")
    private String elasticPassword;
    @Value("${elastic.host}")
    private String elasticHost;
    @Value("${elastic.port}")
    private Integer elasticPort;

    private void buildBulkRequest(BulkRequest.Builder bulkRequestBuilder, String index, Object id,
            Object document) {
        bulkRequestBuilder.operations(op -> op
                .index(idx -> idx
                        .index(index)
                        .id(String.valueOf(id))
                        .document(document)));
    }

    private UpdateRequest buildBulkUpdateRequest(String index, Object id,
            Object document) {
        UpdateRequest.Builder updateRequestBuilder = new UpdateRequest.Builder()
                .index(index)
                .id(String.valueOf(id))
                .doc(document)
                .refresh(Refresh.True);
        return updateRequestBuilder.build();
    }

    private void bulkDataToElastic(BulkRequest bulkRequest) throws ElasticsearchException, IOException {
        ElasticsearchClient client = setUpClient();
        BulkResponse result = client.bulk(bulkRequest);

        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }

    private void updateElasticData(UpdateRequest updateRequest) throws ElasticsearchException, IOException {
        UpdateResponse updateResponse = setUpClient().update(updateRequest, UpdateResponse.class);

        // Handle the update response
        if (updateResponse.result() == Result.Updated) {
            log.info("Document updated successfully: {}", updateResponse.result());
        } else if (updateResponse.result() == Result.Created) {
            log.info("Document created successfully: {}", updateResponse.result());
        } else {
            log.warn("Update operation didn't succeed: {}", updateResponse.result());
        }
    }

    private ElasticsearchClient setUpClient() {
        // Set up credentials provider
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticUserName, elasticPassword));

        // Create the low-level REST client
        RestClient restClient = RestClient.builder(new HttpHost(elasticHost, elasticPort))
                .setHttpClientConfigCallback(
                        httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // Create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }

    private boolean documentExists(String index, String id) {
        ElasticsearchClient client = setUpClient();
        GetRequest getRequest = GetRequest.of(r -> r.id(id).index(index));
        try {
            GetResponse response = client.get(getRequest, getClass());
            return response.found();
        } catch (IOException e) {
            log.error("Error checking if document exists: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void bulkProductsData() throws ElasticsearchException, IOException {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = productMapper.mapProductsToProductResponses(products);
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (ProductResponse product : productResponses) {
            buildBulkRequest(br, ElasticIndex.PRODUCT_INDEX, product.getId(), product);
        }
        bulkDataToElastic(br.build());
    }

    private void bulkProductData(ProductResponse productResponse) {
        BulkRequest.Builder br = new BulkRequest.Builder();
        buildBulkRequest(br, ElasticIndex.PRODUCT_INDEX, productResponse.getId(),
                productResponse);
        try {
            bulkDataToElastic(br.build());
        } catch (ElasticsearchException e) {
            log.error("Elastic search exception error when bulk data " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException error when bulk data " + e.getMessage());
        }
    }

    @Override
    public void bulkCategoryData() throws ElasticsearchException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bulkCategoryData'");
    }

    private void updateProductData(ProductResponse productResponse) {
        boolean isExist = documentExists(ElasticIndex.PRODUCT_INDEX, String.valueOf(productResponse.getId()));
        UpdateRequest updateRequest = null;
        if (isExist) {
            updateRequest = buildBulkUpdateRequest(ElasticIndex.PRODUCT_INDEX,
                    productResponse.getId(), productResponse);
        }
        try {
            updateElasticData(updateRequest);
        } catch (ElasticsearchException e) {
            log.error("Elastic search exception error when bulk data " + e.getMessage());
        } catch (IOException e) {
            log.error("IOException error when bulk data " + e.getMessage());
        }
    }

    @Override
    public void clearAllDataByIndex(String index) throws ElasticsearchException, IOException {
        ElasticsearchClient client = setUpClient();
        DeleteByQueryRequest.Builder deleteRequestBuilder = new Builder();
        Query query = MatchAllQuery.of(m -> m)._toQuery();
        DeleteByQueryRequest deleteByQueryRequest = deleteRequestBuilder.index(List.of(index)).ignoreUnavailable(true)
                .query(query).build();
        client.deleteByQuery(deleteByQueryRequest);
    }

    @Override
    public void updateCategoryData(CategoryResponse categoryResponse) throws ElasticsearchException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCategoryData'");
    }

    @Override
    public void sendDataProductToElastic(ProductResponse productResponse) {
        boolean isExisted = isExistId(ElasticIndex.PRODUCT_INDEX, productResponse.getId().toString());
        if (isExisted) {
            updateProductData(productResponse);
        } else {
            bulkProductData(productResponse);
        }
    }

    private boolean isExistId(String index, String id) {
        ElasticsearchClient client = setUpClient();
        ExistsRequest existsRequest = ExistsRequest.of(e -> e.index(index).id(id));
        BooleanResponse isExisted;
        try {
            isExisted = client.exists(existsRequest);
            return isExisted.value();
        } catch (ElasticsearchException | IOException e1) {
            log.error("Error while checking id = " + id + " exist on elastic of index " + index);
            log.error(e1.getMessage());
            return false;
        }
    }
}
