package com.ridenow.location_service.service;



import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeoCoderService {

    private final String BASE_URL = "https://nominatim.openstreetmap.org/search";

    public Map<String, Double> getGeoCode(String location) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "RideNow/1.0 (hmmm51065@gmail.com)");
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        // Build URL properly
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", location.replace(" ", ","))
                .queryParam("format", "json")
                .queryParam("limit", 1);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> body = response.getBody();

        if (body == null || body.isEmpty()) {
            throw new RuntimeException("Location not found: " + location);
        }

        Map<String, Object> result = body.get(0);

        Map<String, Double> geo = new HashMap<>();
        geo.put("lat", Double.parseDouble((String) result.get("lat")));
        geo.put("lon", Double.parseDouble((String) result.get("lon")));

        return geo;
    }
}
