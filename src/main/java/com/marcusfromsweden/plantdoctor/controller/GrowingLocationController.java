package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/growing-locations")
public class GrowingLocationController {

    private static final Logger log = LoggerFactory.getLogger(GrowingLocationController.class);

    private final GrowingLocationService growingLocationService;

    public GrowingLocationController(GrowingLocationService growingLocationService) {
        this.growingLocationService = growingLocationService;
    }

    @GetMapping
    public ResponseEntity<List<GrowingLocation>> getAllGrowingLocations() {
        List<GrowingLocation> growingLocationList = growingLocationService.getAllGrowingLocations();
        log.info("Returning {} growing locations", growingLocationList.size());
        return new ResponseEntity<>(growingLocationList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowingLocation> getGrowingLocationById(@PathVariable Long id) {
        Optional<GrowingLocation> growingLocation =
                growingLocationService.getGrowingLocationById(id);
        return growingLocation.map(location -> new ResponseEntity<>(location, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<GrowingLocation> createGrowingLocation(
            @Valid @RequestBody GrowingLocation growingLocation) {
        GrowingLocation createdGrowingLocation =
                growingLocationService.createGrowingLocation(growingLocation);
        return new ResponseEntity<>(createdGrowingLocation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrowingLocation> updateGrowingLocation(@PathVariable Long id,
                                                                 @Valid @RequestBody GrowingLocation growingLocation) {
        Optional<GrowingLocation> existingLocation =
                growingLocationService.getGrowingLocationById(id);
        if (existingLocation.isPresent()) {
            growingLocation.setId(id);
            GrowingLocation updatedGrowingLocation =
                    growingLocationService.updateGrowingLocation(id, growingLocation);
            return new ResponseEntity<>(updatedGrowingLocation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingLocation(@PathVariable Long id) {
        growingLocationService.deleteGrowingLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllGrowingLocations() {
        growingLocationService.deleteAllGrowingLocations();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
