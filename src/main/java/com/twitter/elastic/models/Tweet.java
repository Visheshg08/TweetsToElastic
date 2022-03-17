package com.twitter.elastic.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;


@Document(indexName = "uselection")
@Setting(settingPath = "/settings/elasticsettings.json")
@Mapping(mappingPath = "/mappings/mappings.json")
public class Tweet {

    private String text;

    @Id
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "text='" + text + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
