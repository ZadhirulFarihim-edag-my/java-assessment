package com.farihim.assessment.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    @Getter
    @Setter
    public static class CurrentWeather {
        private double temperature;
        @JsonProperty("windspeed")
        private double windSpeed;
        @JsonProperty("weathercode")
        private int weatherCode;
        @JsonProperty("winddirection")
        private double windDirection;
        private String time;
    }
}
