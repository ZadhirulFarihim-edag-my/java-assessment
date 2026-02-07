package com.farihim.assessment.demo.service;

import com.farihim.assessment.demo.config.WeatherApiProperties;
import com.farihim.assessment.demo.dto.response.WeatherResponse;
import com.farihim.assessment.demo.dto.response.WeatherResponse.CurrentWeather;
import com.farihim.assessment.demo.dto.response.WeatherSummaryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherApiProperties properties;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void getCurrentWeather_shouldReturnMappedWeatherSummary() {
        // Arrange
        when(properties.getBaseUrl()).thenReturn("https://api.weather.com");
        when(properties.getLatitude()).thenReturn(Double.valueOf("10.0"));
        when(properties.getLongitude()).thenReturn(Double.valueOf("20.0"));
        when(properties.getLocationName()).thenReturn("TestCity");

        WeatherResponse weatherResponse = new WeatherResponse();
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setTemperature(23.456);
        currentWeather.setWindSpeed(3.2);
        currentWeather.setWeatherCode(0);
        currentWeather.setTime("2026-02-07T12:00:00Z");
        weatherResponse.setCurrentWeather(currentWeather);

        String expectedUrl = "https://api.weather.com?latitude=10.0&longitude=20.0&current_weather=true";
        when(restTemplate.getForObject(expectedUrl, WeatherResponse.class)).thenReturn(weatherResponse);

        // Act
        WeatherSummaryResponse summary = weatherService.getCurrentWeather();

        // Assert
        assertEquals("TestCity", summary.getLocation());
        assertEquals("Clear sky", summary.getCondition());
        assertEquals("23.5°C", summary.getTemperature());
        assertEquals("Light wind (3.2 km/h)", summary.getWind());
        assertEquals("2026-02-07T12:00:00Z", summary.getObservationTime());
    }

    @Test
    void getCurrentWeather_shouldFormatWindAboveThreshold() {
        when(properties.getBaseUrl()).thenReturn("https://api.weather.com");
        when(properties.getLatitude()).thenReturn(Double.valueOf("10.0"));
        when(properties.getLongitude()).thenReturn(Double.valueOf("20.0"));
        when(properties.getLocationName()).thenReturn("WindyCity");

        WeatherResponse weatherResponse = new WeatherResponse();
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setTemperature(18.0);
        currentWeather.setWindSpeed(7.5); // > 5 km/h
        currentWeather.setWeatherCode(1);
        currentWeather.setTime("2026-02-07T13:00:00Z");
        weatherResponse.setCurrentWeather(currentWeather);

        String expectedUrl = "https://api.weather.com?latitude=10.0&longitude=20.0&current_weather=true";
        when(restTemplate.getForObject(expectedUrl, WeatherResponse.class)).thenReturn(weatherResponse);

        WeatherSummaryResponse summary = weatherService.getCurrentWeather();

        assertEquals("WindyCity", summary.getLocation());
        assertEquals("Mostly clear", summary.getCondition());
        assertEquals("18.0°C", summary.getTemperature());
        assertEquals("Wind 7.5 km/h", summary.getWind());
        assertEquals("2026-02-07T13:00:00Z", summary.getObservationTime());
    }

    @Test
    void getCurrentWeather_shouldThrowIfApiReturnsNull() {
        when(properties.getBaseUrl()).thenReturn("https://api.weather.com");
        when(properties.getLatitude()).thenReturn(Double.valueOf("10.0"));
        when(properties.getLongitude()).thenReturn(Double.valueOf("20.0"));

        String expectedUrl = "https://api.weather.com?latitude=10.0&longitude=20.0&current_weather=true";
        when(restTemplate.getForObject(expectedUrl, WeatherResponse.class)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> weatherService.getCurrentWeather());
    }

    @Test
    void getCurrentWeather_shouldMapAllWeatherCodes() {
        when(properties.getBaseUrl()).thenReturn("https://api.weather.com");
        when(properties.getLatitude()).thenReturn(Double.valueOf("0.0"));
        when(properties.getLongitude()).thenReturn(Double.valueOf("0.0"));
        when(properties.getLocationName()).thenReturn("CodeCity");

        int[] codes = {0, 1, 2, 3, 45, 48, 51, 53, 55, 61, 63, 65, 80, 81, 82, 999};
        String[] expectedConditions = {
                "Clear sky",
                "Mostly clear",
                "Mostly clear",
                "Partly cloudy",
                "Foggy",
                "Foggy",
                "Light drizzle",
                "Light drizzle",
                "Light drizzle",
                "Rain",
                "Rain",
                "Rain",
                "Rain showers",
                "Rain showers",
                "Rain showers",
                "Unknown weather condition"
        };

        for (int i = 0; i < codes.length; i++) {
            WeatherResponse weatherResponse = new WeatherResponse();
            CurrentWeather currentWeather = new CurrentWeather();
            currentWeather.setTemperature(20);
            currentWeather.setWindSpeed(2);
            currentWeather.setWeatherCode(codes[i]);
            currentWeather.setTime("2026-02-07T14:00:00Z");
            weatherResponse.setCurrentWeather(currentWeather);

            String expectedUrl = "https://api.weather.com?latitude=0.0&longitude=0.0&current_weather=true";
            when(restTemplate.getForObject(expectedUrl, WeatherResponse.class)).thenReturn(weatherResponse);

            WeatherSummaryResponse summary = weatherService.getCurrentWeather();
            assertEquals(expectedConditions[i], summary.getCondition());
        }
    }
}
