package com.everysource.everysource;

import com.everysource.everysource.repository.api.IssueSearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableElasticsearchRepositories
public class EverysourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EverysourceApplication.class, args);
	}
}

