package com.marcusfromsweden.plantdoctor.client.trefle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrefleApiClient {

    private final static int DISTRIBUTION_ZONE_ID_EUROPE = 369;
    private final static Logger log = LoggerFactory.getLogger(TrefleApiClient.class);

    private final RestTemplate restTemplate;

    @Value("${trefle.api.base-url:http://trefle.io/api/v1}")
    private String baseUrl;

    public TrefleApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPlantData(String endpoint, String queryParam) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path(endpoint)
                .queryParam("param", queryParam)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    public List<Plant> getPlantsInEurope() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/distributions/" + DISTRIBUTION_ZONE_ID_EUROPE + "/plants")
                .queryParam("token", "LnIeGVDRsmmwUaFw4FgRgxVZ-XEnzqt9-XxtgR-jTM4")
                .toUriString();

/*
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String responseBody = responseEntity.getBody();
        log.warn("Response Body: " + responseBody);
*/

        PlantResponse response = restTemplate.getForObject(url, PlantResponse.class);
        if (response == null || response.getData() == null) {
            return Collections.emptyList();
        }

        // Use a Map to filter out duplicates by id
        return response.getData().stream()
                .collect(Collectors.toMap(
                        Plant::getId, // Use the id as the key
                        plant -> plant, // Use the Plant object as the value
                        (existing, duplicate) -> existing // Keep the first occurrence
                ))
                .values()
                .stream()
                .toList();
    }
}