package com.twitter.elastic;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ElasticApplication {


	@Autowired RestHighLevelClient restHighLevelClient;

	public static void main(String[] args) {
		SpringApplication.run(ElasticApplication.class, args);
	}


}
