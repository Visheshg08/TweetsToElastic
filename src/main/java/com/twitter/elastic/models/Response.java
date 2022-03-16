package com.twitter.elastic.models;

import java.util.List;

public class Response {

    private List<Tweet> data;


    public List<Tweet> getData() {
        return data;
    }

    public void setData(List<Tweet> data) {
        this.data = data;
    }


}
