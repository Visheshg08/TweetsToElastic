package com.twitter.elastic.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

public class TweetRepository {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    public void findAll(){

    }
}
