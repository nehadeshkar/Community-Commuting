package com.tripmanagement.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication (scanBasePackages ="com.tripmanagement.*")
@EntityScan (value="com.tripmanagement.entities")
@EnableJpaRepositories(basePackages ="com.tripmanagement.repositories")
@EnableDiscoveryClient(autoRegister = true)
public class TripmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripmanagementApplication.class, args);
	}

}
