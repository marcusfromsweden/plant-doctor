package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
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
    public ResponseEntity<List<GrowingLocationDTO>> getAllGrowingLocations() {
        List<GrowingLocationDTO> growingLocationDTOList = growingLocationService.getAllGrowingLocations();
        log.info("Returning {} growing locations", growingLocationDTOList.size());
        return new ResponseEntity<>(growingLocationDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowingLocationDTO> getGrowingLocationById(@PathVariable Long id) {
        Optional<GrowingLocationDTO> growingLocationDTO = growingLocationService.getGrowingLocationById(id);
        return growingLocationDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<GrowingLocationDTO> createGrowingLocation(
            @Valid @RequestBody GrowingLocationDTO growingLocationDTO) {
        GrowingLocationDTO createdGrowingLocationDTO = growingLocationService.createGrowingLocation(growingLocationDTO);
        return new ResponseEntity<>(createdGrowingLocationDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrowingLocationDTO> updateGrowingLocation(@PathVariable Long id,
                                                                    @Valid @RequestBody GrowingLocationDTO growingLocationDTO) {
        GrowingLocationDTO updatedGrowingLocationDTO = growingLocationService.updateGrowingLocation(id, growingLocationDTO);
        return new ResponseEntity<>(updatedGrowingLocationDTO, HttpStatus.OK);
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

    @GetMapping("/name/{name}")
    public ResponseEntity<GrowingLocationDTO> getGrowingLocationByName(@PathVariable String name) {
        Optional<GrowingLocationDTO> growingLocationDTO = growingLocationService.getGrowingLocationByName(name);
        return growingLocationDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}