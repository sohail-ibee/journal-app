package com.heysohail.journalApp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Public APIs")
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
