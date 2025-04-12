package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.client.trefle.Plant;
import com.marcusfromsweden.plantdoctor.client.trefle.TrefleApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trefle")
public class TrefleController {

    private static final Logger log = LoggerFactory.getLogger(TrefleController.class);

    private final TrefleApiClient trefleApiClient;

    public TrefleController(TrefleApiClient trefleApiClient) {
        this.trefleApiClient = trefleApiClient;
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getAllPlants() {
        log.warn("Getting all plants");
        List<Plant> plants = trefleApiClient.getPlantsInEurope();
        return new ResponseEntity<>(plants, HttpStatus.OK);
    }
}