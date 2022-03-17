package com.twitter.elastic.repo;

import com.twitter.elastic.models.Tweet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


// ElasticsearchRestTemplate

@Component
public interface TweetRepo extends ElasticsearchRepository<Tweet, String> {



}

