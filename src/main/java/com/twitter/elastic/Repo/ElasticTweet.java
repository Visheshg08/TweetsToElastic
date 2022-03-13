package com.twitter.elastic.Repo;

import com.twitter.elastic.models.Tweet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface ElasticTweet extends ElasticsearchRepository<Tweet, String> {


}

