package com.gadys.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck() {
        // This endpoint will be used by Azure to check if the application is healthy.
        // It just needs to return a 200 OK status.
        return ResponseEntity.ok("Gadys backend is healthy!");
    }
}
