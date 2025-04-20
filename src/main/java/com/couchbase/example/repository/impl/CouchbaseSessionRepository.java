package com.couchbase.example.repository.impl;

import static com.couchbase.client.java.kv.UpsertOptions.upsertOptions;

import com.couchbase.client.java.Collection;
import com.couchbase.example.config.SpringSessionAdapter;
import java.time.Duration;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CouchbaseSessionRepository implements FindByIndexNameSessionRepository<Session> {

  private static final Logger logger = LoggerFactory.getLogger(CouchbaseSessionRepository.class);

  private final Collection collection;

  public CouchbaseSessionRepository(Collection collection) {
    this.collection = collection;
  }

  @Override
  public Session createSession() {
    logger.debug("Creating a new session");
    return new SpringSessionAdapter();
  }

  @Override
  public void save(Session session) {
    SpringSessionAdapter adapter = (SpringSessionAdapter) session;
    // validate TTL
    long ttl = adapter.getMaxInactiveInterval().getSeconds();
    if (ttl <= 0) {
      ttl = 3600;  // default value
      logger.warn("TTL is invalid or not set, using default TTL of 1 hour");
    }
    collection.upsert(adapter.getId(), adapter, upsertOptions().expiry(Duration.ofSeconds(ttl)));
  }

  @Override
  public Session findById(String id) {
    // retrieves a session by ID. Returns null if the session does not exist or is expired
    return collection.get(id).contentAs(SpringSessionAdapter.class);
  }

  @Override
  public void deleteById(String id) {
    collection.remove(id);
  }

  @Override
  public Map<String, Session> findByIndexNameAndIndexValue(String indexName, String indexValue) {
    throw new UnsupportedOperationException("Index search not implemented");
  }
}
