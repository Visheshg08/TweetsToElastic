package com.twitter.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.elastic.Repo.TweetRepo;
import com.twitter.elastic.models.TwitterResponse;
import com.twitter.elastic.models.Tweet;
import org.elasticsearch.client.RestHighLevelClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


@Service
public class TweetData {
    private Logger logger=  LoggerFactory.getLogger(TweetData.class);

    @Autowired
    TweetRepo tweetRepo;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;



    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${my.bearerToken}")
    private String bearerToken;

    @Value("${index.name}")
    private String indexName;



    public List<String> tweetsData() throws Exception {
//
//
//
//        GetIndexRequest response = new GetIndexRequest(indexName);
//        if (response== null){
//
//            IndexRequest request= new IndexRequest(indexName);
//
//            String settings="{\n" +
//                    "  \"settings\": {\n" +
//                    "    \"analysis\": {\n" +
//                    "      \"analyzer\": {\n" +
//                    "        \"english_stop\": {\n" +
//                    "          \"tokenizer\": \"whitespace\",\n" +
//                    "          \"filter\": [ \"my_custom_stop_words_filter\" ]\n" +
//                    "        }\n" +
//                    "      },\n" +
//                    "      \"filter\": {\n" +
//                    "        \"my_custom_stop_words_filter\": {\n" +
//                    "          \"type\": \"stop\",\n" +
//                    "          \"ignore_case\": true\n" +
//                    "        }\n" +
//                    "      }\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}";
//
//            String mapping="{\n" +
//                    "  \"mappings\": {\n" +
//                    "    \"properties\": {\n" +
//                    "      \"text\": {\n" +
//                    "        \"type\": \"text\",\n" +
//                    "        \"analyzer\": \"english_stop\",\n" +
//                    "        \"fielddata\": true\n" +
//                    "      },\n" +
//                    "      \"id\":{\n" +
//                    "        \"type\": \"text\"\n" +
//                    "      }\n" +
//                    "    }\n" +
//                    "  }\n" +
//                    "}\n";
//
//            request.source(settings, XContentType.JSON);
//            request.source(mapping,XContentType.JSON);
//            IndexResponse indexResponse = restHighLevelClient.index(request,
//                    RequestOptions.DEFAULT);


//
//            Request request= new Request("PUT", "/" + indexName);
//            request.setJsonEntity(settings);
//            request.setJsonEntity(mapping);
//            restHighLevelClient.getLowLevelClient().performRequest(request);
//
//




//
//
//            XContentBuilder builder = XContentFactory.jsonBuilder();
//            builder.startObject();
//            {
//                builder.startObject("properties");
//                {
//                    builder.startObject("text");
//                    {
//                        builder.field("type", "text");
//                    }
//                    builder.endObject();
//                    builder.startObject("id");
//                    {
//                        builder.field("type", "text");
//                    }
//                    builder.endObject();
//                }
//                builder.endObject();
//            }
//            builder.endObject();
//            request.mapping(builder);
//
//
//
    //}
//





        URL url = new URL("https://api.twitter.com/2/tweets/search/recent?query=%20donald%20OR%20trump%20OR%20USpresident");
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
        return data;
    }

    @Scheduled(fixedDelay = 1000)
    public List<Tweet> saveTweets() throws Exception {

        List<String> data=tweetsData();
        String temp= data.get(0);
        TwitterResponse response =objectMapper.readValue(temp, TwitterResponse.class);
        tweetRepo.saveAll(response.getData());
        return response.getData();
    }


    public Iterable<Tweet> savedTweets(){
        return  tweetRepo.findAll();
    }
}
