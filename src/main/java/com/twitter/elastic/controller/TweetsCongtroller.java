package com.twitter.elastic.controller;
import com.twitter.elastic.models.Tweet;
import com.twitter.elastic.service.TweetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping
public class TweetsCongtroller {

    @Autowired
    private TweetData tweetData;

    @GetMapping("/latestTweets")
    public  List<String> getTweets() throws Exception {
        return tweetData.tweetsData();
    }

    @GetMapping("/saveTweets")
    public List<Tweet> saveTweets() throws Exception {
        return tweetData.saveTweets();
    }
    @GetMapping("savedTweets")
    public Iterable<Tweet> savedTweets(){
        return tweetData.savedTweets();

    }


}
