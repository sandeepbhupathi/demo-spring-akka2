package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class CassandraConfig {
	
	@Bean
	public Cluster getCluster() {
		return new Cluster.Builder().addContactPoint("127.0.0.1").withPort(9042).build();
	}

	@Bean
	public Session getSession(Cluster cluster) {
		return cluster.connect("pcms");
	}
}
