package com.farihim.assessment.demo.controller;

import com.farihim.assessment.demo.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getExternalPost(@PathVariable int id) {
        return ResponseEntity.ok(externalApiService.getExternalPost(id));
    }
}
