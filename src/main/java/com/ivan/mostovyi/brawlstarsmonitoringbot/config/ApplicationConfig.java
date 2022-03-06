package com.ivan.mostovyi.brawlstarsmonitoringbot.config;

import brawljars.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public Api getBrawlStarsApi(@Value("${client.brawlStars.url}") String url, @Value("${client.brawlStars.apiKey}") String apiKey) {
    return new Api(url, apiKey);
  }

}
