package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.di.SpringExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

@Configuration
public class ActorConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;
   
    @Bean
    public ActorSystem actorSystem() {
        ActorSystem actorSystem = ActorSystem.create("demo-actor-system", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return actorSystem;
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
    
    
}
