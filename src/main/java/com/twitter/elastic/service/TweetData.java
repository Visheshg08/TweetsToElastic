package com.twitter.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.twitter.elastic.Repo.ElasticTweet;
import com.twitter.elastic.models.Response;
import com.twitter.elastic.models.Tweet;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class TweetData {
    private Logger logger=  LoggerFactory.getLogger(TweetData.class);

    @Autowired
    ElasticTweet elasticTweet;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${my.bearerToken}")
    private String bearerToken;




    public List<String> tweetsData() throws Exception {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("tweets");
            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT); // 1
        } catch (Exception ignored) {
        }

        CreateIndexRequest createIndexRequest = new CreateIndexRequest("tweets");
        createIndexRequest.settings(
                Settings.builder().put("index.number_of_shards", 1)
                        .put("index.number_of_replicas", 0));
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT); // 2

        URL url = new URL("https://api.twitter.com/2/tweets/search/recent?query=%23nowplaying%20(exciting)");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer "+bearerToken);


        List<String> data= new ArrayList<>();
        BufferedReader br = null;
        if (http.getResponseCode() == 200) {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String strCurrentLine;
            while ((strCurrentLine = br.readLine()) != null) {
                data.add(strCurrentLine);
            }
        } else {
            br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            String strCurrentLine;
            while ((strCurrentLine = br.readLine()) != null) {
                data.add(strCurrentLine);
            }
        }

        http.disconnect();
        logger.info(String.valueOf(data.size()));
        return data;
    }
    public List<Tweet> saveTweets() throws Exception {

        List<String> data=tweetsData();

        String temp= data.get(0);

        Response response =objectMapper.readValue(temp, Response.class);

        elasticTweet.saveAll(response.getData());

        return response.getData();


        //return response.getData();
    }


    public Iterable<Tweet> savedTweets(){

        Gson g= new Gson();
        ObjectMapper mapper= new ObjectMapper();

        return  elasticTweet.findAll();

    }
}
