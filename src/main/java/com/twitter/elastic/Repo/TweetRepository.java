package com.twitter.elastic.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.management.Query;

public class TweetRepository {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    public void findAll(){

    }
}
