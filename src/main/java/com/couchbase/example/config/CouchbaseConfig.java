package com.couchbase.example.config;

import com.couchbase.client.core.env.PasswordAuthenticator;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.codec.JacksonJsonSerializer;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbaseConfig {

  @Value("${spring.couchbase.connection-string:couchbase}")
  private String host;

  @Value("${spring.couchbase.username:Administrator}")
  private String username;

  @Value("${spring.couchbase.password:Administrator}")
  private String password;

  @Bean
  public ObjectMapper couchbaseObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  @Bean(destroyMethod = "shutdown")
  public ClusterEnvironment couchbaseEnvironment(ObjectMapper couchbaseObjectMapper) {
    return ClusterEnvironment.builder()
        .jsonSerializer(JacksonJsonSerializer.create(couchbaseObjectMapper))
        .build();
  }


  @Bean(destroyMethod = "disconnect")
  public Cluster couchbaseCluster(ClusterEnvironment environment) {

    ClusterOptions options = ClusterOptions.clusterOptions(
            PasswordAuthenticator.create(username, password))
        .environment(environment);

    return Cluster.connect(host, options);
  }

  @Bean
  public Bucket couchbaseBucket(Cluster cluster) {
    return cluster.bucket("session_bucket");
  }

  @Bean
  public Collection couchbaseCollection(Bucket bucket) {
    return bucket.defaultCollection();
  }
}
