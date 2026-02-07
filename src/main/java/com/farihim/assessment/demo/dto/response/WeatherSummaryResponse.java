package com.farihim.assessment.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class WeatherSummaryResponse {

    private String location;
    private String condition;
    private String temperature;
    private String wind;
    private String observationTime;
}