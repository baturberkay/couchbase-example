package com.couchbase.example.config;

import org.springframework.session.Session;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class SpringSessionAdapter implements Session {

  private String id;
  private Instant creationTime;
  private Instant lastAccessedTime;
  private int maxInactiveIntervalInSeconds;

  public SpringSessionAdapter() {
    this.id = UUID.randomUUID().toString();
    this.creationTime = Instant.now();
    this.lastAccessedTime = Instant.now();
    this.maxInactiveIntervalInSeconds = 1800; // 30 minutes
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String changeSessionId() {
    this.id = UUID.randomUUID().toString();
    return this.id;
  }

  @Override
  public <T> T getAttribute(String attributeName) {
    return null;
  }

  @Override
  public Set<String> getAttributeNames() {
    return Set.of();
  }

  @Override
  public void setAttribute(String attributeName, Object attributeValue) {
  }

  @Override
  public void removeAttribute(String attributeName) {
  }

  @Override
  public Instant getCreationTime() {
    return creationTime;
  }

  @Override
  public void setLastAccessedTime(Instant lastAccessedTime) {
    this.lastAccessedTime = lastAccessedTime;
  }

  @Override
  public Instant getLastAccessedTime() {
    return lastAccessedTime;
  }

  @Override
  public void setMaxInactiveInterval(Duration interval) {
    this.maxInactiveIntervalInSeconds = (int) interval.getSeconds();
  }

  @Override
  public Duration getMaxInactiveInterval() {
    return Duration.ofSeconds(maxInactiveIntervalInSeconds);
  }

  @Override
  public boolean isExpired() {
    return Instant.now().isAfter(lastAccessedTime.plusSeconds(maxInactiveIntervalInSeconds));
  }
}
