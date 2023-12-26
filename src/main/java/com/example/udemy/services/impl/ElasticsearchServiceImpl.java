package com.example.udemy.services.impl;

import com.example.udemy.dto.song.SongResponseDto;
import com.example.udemy.services.ElasticsearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Value("${elasticsearch.index-names.songs}")
    private String indexName;
    private static final String CLASSPATH_SETTINGS = "classpath:settings/elasticsearch-settings.json";
    private static final String CLASSPATH_MAPPINGS = "classpath:mappings/elasticsearch-mappings.json";
    private static final String FIELD_NAME_FOR_SEARCH = "name";


    private final RestHighLevelClient elasticsearchClient;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void createIndex() {
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        try {
            String jsonMappings = readFileAsString(resourceLoader.getResource(CLASSPATH_MAPPINGS).getURI());

            log.info("adding settings and mappings to index from classpath");
            request.mapping(jsonMappings, XContentType.JSON);
            request.settings(Settings.builder()
                    .loadFromPath(Paths.get(resourceLoader.getResource(CLASSPATH_SETTINGS).getURI()))
                    .build());

            elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException elasticsearchStatusException) {
            log.error(elasticsearchStatusException.getLocalizedMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SongResponseDto> searchByName(String name) {
        log.info("creating a request to index '{}', to find fields", indexName);
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        log.info("creating a match query");
        boolQueryBuilder.must(QueryBuilders.matchQuery(FIELD_NAME_FOR_SEARCH, name));

        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);

        List<SongResponseDto> songResponseDtos;
        try {
            log.info("handling response of match query");
            songResponseDtos =  Arrays.stream(elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT)
                            .getHits().getHits())
                    .map(SearchHit::getSourceAsMap)
                    .map(sourceMap -> objectMapper.convertValue(sourceMap, SongResponseDto.class))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return songResponseDtos;
    }

    @Override
    public void addSongToElastic(SongResponseDto songResponseDto) {
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(songResponseDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        sendAddRequestElastic(jsonData);
    }

    private String readFileAsString(URI file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void sendAddRequestElastic(String jsonData) {
        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(UUID.randomUUID().toString())
                .source(jsonData, XContentType.JSON);

        log.info("handling response from elastic");
        try {
            elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT).getId();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
