package com.farihim.assessment.demo.controller;

import com.farihim.assessment.demo.dto.response.WeatherSummaryResponse;
import com.farihim.assessment.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalApiController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherSummaryResponse> getCurrentWeather() {
        return ResponseEntity.ok(weatherService.getCurrentWeather());
    }
}