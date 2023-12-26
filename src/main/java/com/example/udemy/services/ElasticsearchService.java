package com.example.udemy.services;

import java.util.List;

public interface ElasticsearchService {
    void createIndex(String index);
    List<Object> searchByFields(String name);
}
