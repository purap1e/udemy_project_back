package com.example.udemy.configs;

import com.example.udemy.properties.ElasticSearchProperties;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfig  {

    private final ElasticSearchProperties elasticSearchProperties;

    @Bean(name = "customElasticsearchClient")
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticSearchProperties.getHost(), elasticSearchProperties.getPort()))
        );
    }
}
