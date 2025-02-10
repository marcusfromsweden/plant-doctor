package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.service.SeedPackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seed-packages")
public class SeedPackageController {

    private final SeedPackageService seedPackageService;

    public SeedPackageController(SeedPackageService seedPackageService) {
        this.seedPackageService = seedPackageService;
    }

    @GetMapping
    public List<SeedPackageDTO> getAllSeedPackages() {
        return seedPackageService.getAllSeedPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeedPackageDTO> getSeedPackageById(@PathVariable Long id) {
        Optional<SeedPackageDTO> seedPackage = seedPackageService.getSeedPackageById(id);
        return seedPackage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SeedPackageDTO> createSeedPackage(@RequestBody SeedPackageDTO seedPackageDTO) {
        SeedPackageDTO seedPackage = seedPackageService.createSeedPackage(seedPackageDTO);
        return new ResponseEntity<>(seedPackage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeedPackageDTO> updateSeedPackage(@PathVariable Long id,
                                                            @RequestBody SeedPackageDTO seedPackageDetails) {
        try {
            SeedPackageDTO updatedSeedPackage = seedPackageService.updateSeedPackage(id, seedPackageDetails);
            return ResponseEntity.ok(updatedSeedPackage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeedPackage(@PathVariable Long id) {
        try {
            seedPackageService.deleteSeedPackage(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}