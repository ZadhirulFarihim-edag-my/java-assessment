package com.farihim.assessment.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {

    private String baseUrl;
    private double latitude;
    private double longitude;
    private String locationName;
}