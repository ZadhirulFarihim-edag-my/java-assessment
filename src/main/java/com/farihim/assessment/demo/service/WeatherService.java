package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.config.WeatherApiProperties;
import com.farihim.assessment.demo.dto.response.WeatherResponse;
import com.farihim.assessment.demo.dto.response.WeatherSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final WeatherApiProperties properties;

    public WeatherSummaryResponse getCurrentWeather() {

        String url = UriComponentsBuilder
                .fromUri(URI.create(properties.getBaseUrl()))
                .queryParam("latitude", properties.getLatitude())
                .queryParam("longitude", properties.getLongitude())
                .queryParam("current_weather", true)
                .toUriString();

        return Optional.ofNullable(restTemplate.getForObject(url, WeatherResponse.class))
                .map(WeatherResponse::getCurrentWeather)
                .map(cw -> new WeatherSummaryResponse(
                        properties.getLocationName(),
                        mapWeatherCode(cw.getWeatherCode()),
                        formatTemperature(cw.getTemperature()),
                        formatWind(cw.getWindSpeed()),
                        cw.getTime()
                ))
                .orElseThrow(() -> new IllegalStateException("Failed to retrieve weather data"));
    }

    private String formatTemperature(double temperature) {
        return String.format("%.1f°C", temperature);
    }

    private String formatWind(double windSpeed) {
        return windSpeed < 5
                ? String.format("Light wind (%.1f km/h)", windSpeed)
                : String.format("Wind %.1f km/h", windSpeed);
    }

    private String mapWeatherCode(int code) {
        return switch (code) {
            case 0 -> "Clear sky";
            case 1, 2 -> "Mostly clear";
            case 3 -> "Partly cloudy";
            case 45, 48 -> "Foggy";
            case 51, 53, 55 -> "Light drizzle";
            case 61, 63, 65 -> "Rain";
            case 80, 81, 82 -> "Rain showers";
            default -> "Unknown weather condition";
        };
    }
}
