package com.farihim.assessment.demo.controller;

import com.farihim.assessment.demo.dto.response.WeatherSummaryResponse;
import com.farihim.assessment.demo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExternalApiController.class)
class ExternalApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Test
    void getCurrentWeather_shouldReturnWeatherSummary() throws Exception {
        WeatherSummaryResponse mockResponse = new WeatherSummaryResponse(
                "Shah Alam, Selangor",
                "Clear sky",
                "30.0°C",
                "Light wind (3.2 km/h)",
                "2026-02-07T10:00:00Z"
        );

        when(weatherService.getCurrentWeather()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/external/weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Shah Alam, Selangor"))
                .andExpect(jsonPath("$.condition").value("Clear sky"))
                .andExpect(jsonPath("$.temperature").value("30.0°C"))
                .andExpect(jsonPath("$.wind").value("Light wind (3.2 km/h)"))
                .andExpect(jsonPath("$.observationTime").value("2026-02-07T10:00:00Z"));
    }
}
