package com.couchbase.example.controller;

import com.couchbase.example.service.SessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Session API")
@RequestMapping("/api/v1/session")
public class SessionController {

  private final SessionService sessionService;

  public SessionController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @GetMapping(value = "/create")
  public ResponseEntity<Session> createSession() {
    return new ResponseEntity<>(sessionService.createSession(), HttpStatus.CREATED);
  }
}
