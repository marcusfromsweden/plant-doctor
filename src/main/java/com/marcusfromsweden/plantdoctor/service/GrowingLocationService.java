package com.marcusfromsweden.plantdoctor.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;

@Service
public class GrowingLocationService {

    private final GrowingLocationRepository growingLocationRepository;

    public GrowingLocationService(GrowingLocationRepository growingLocationRepository) {
        this.growingLocationRepository = growingLocationRepository;
    }

    public List<GrowingLocation> getAllGrowingLocations() {
        return growingLocationRepository.findAll();
    }

    public Optional<GrowingLocation> getGrowingLocationById(Long id) {
        return growingLocationRepository.findById(id);
    }

    public GrowingLocation createGrowingLocation(GrowingLocation growingLocation) {
        return growingLocationRepository.save(growingLocation);
    }

    public GrowingLocation updateGrowingLocation(Long id, GrowingLocation growingLocation) {
        Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(id);
        if (optionalGrowingLocation.isPresent()) {
            GrowingLocation existingGrowingLocation = optionalGrowingLocation.get();
            existingGrowingLocation.setLocationName(growingLocation.getLocationName());
            existingGrowingLocation.setOccupied(growingLocation.isOccupied());
            return growingLocationRepository.save(existingGrowingLocation);
        } else {
            throw new RuntimeException("GrowingLocation not found with id " + id);
        }
    }

    public void deleteGrowingLocation(Long id) {
        growingLocationRepository.deleteById(id);
    }

    public void deleteAllGrowingLocations() {
        growingLocationRepository.deleteAll();
    }
}
