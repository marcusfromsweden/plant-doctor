package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.service.GrowingLocationService;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<GrowingLocation> growingLocationList = growingLocationService.getAllGrowingLocations();
        List<GrowingLocationDTO> growingLocationDTOList = growingLocationList.stream()
                .map(GrowingLocationMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Returning {} growing locations", growingLocationDTOList.size());
        return new ResponseEntity<>(growingLocationDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowingLocationDTO> getGrowingLocationById(@PathVariable Long id) {
        Optional<GrowingLocation> growingLocation = growingLocationService.getGrowingLocationById(id);
        return growingLocation.map(location -> new ResponseEntity<>(GrowingLocationMapper.toDTO(location), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<GrowingLocationDTO> createGrowingLocation(
            @Valid @RequestBody GrowingLocationDTO growingLocationDTO) {
        GrowingLocation growingLocation = GrowingLocationMapper.toEntity(growingLocationDTO);
        GrowingLocation createdGrowingLocation = growingLocationService.createGrowingLocation(growingLocation);
        return new ResponseEntity<>(GrowingLocationMapper.toDTO(createdGrowingLocation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrowingLocationDTO> updateGrowingLocation(@PathVariable Long id,
                                                                    @Valid @RequestBody GrowingLocationDTO growingLocationDTO) {
        GrowingLocation growingLocation = GrowingLocationMapper.toEntity(growingLocationDTO);
        GrowingLocation updatedGrowingLocation = growingLocationService.updateGrowingLocation(id, growingLocation);
        return new ResponseEntity<>(GrowingLocationMapper.toDTO(updatedGrowingLocation), HttpStatus.OK);
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