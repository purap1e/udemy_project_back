package com.example.udemy.services;

import java.util.List;

public interface ElasticsearchService {
    List<Object> searchByFields(String name);
}
