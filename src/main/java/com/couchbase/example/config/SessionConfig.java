package com.couchbase.example.config;


import com.couchbase.example.repository.impl.CouchbaseSessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
public class SessionConfig {

  @Bean
  public SessionRepositoryFilter<?> springSessionRepositoryFilter(
      CouchbaseSessionRepository sessionRepository) {
    return new SessionRepositoryFilter<>(sessionRepository);
  }
}

