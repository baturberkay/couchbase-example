package com.couchbase.example.service.impl;

import com.couchbase.example.repository.impl.CouchbaseSessionRepository;
import com.couchbase.example.service.SessionService;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

  private final CouchbaseSessionRepository sessionRepository;

  public SessionServiceImpl(CouchbaseSessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @Override
  public Session createSession() {
    Session session = sessionRepository.createSession();
    sessionRepository.save(session);
    return session;
  }
}
