package com.farihim.assessment.demo.external.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public Object getExternalPost(int id) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        return restTemplate.getForObject(url, Object.class);
    }
}
