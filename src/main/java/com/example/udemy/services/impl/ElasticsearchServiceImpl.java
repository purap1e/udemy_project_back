package com.example.udemy.services.impl;

import com.example.udemy.services.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Value("${elasticsearch.index-names.spotify}")
    private String indexName;


    @Override
    public List<Object> searchByFields(String name) {
        return null;
    }
}
