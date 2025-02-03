package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrowingLocationService {

    private final GrowingLocationRepository growingLocationRepository;

    public GrowingLocationService(GrowingLocationRepository growingLocationRepository) {
        this.growingLocationRepository = growingLocationRepository;
    }

    public List<GrowingLocationDTO> getAllGrowingLocations() {
        return growingLocationRepository.findAll().stream()
                .map(GrowingLocationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<GrowingLocationDTO> getGrowingLocationById(Long id) {
        return growingLocationRepository.findById(id)
                .map(GrowingLocationMapper::toDTO);
    }

    public GrowingLocationDTO createGrowingLocation(GrowingLocationDTO growingLocationDTO) {
        GrowingLocation growingLocation = GrowingLocationMapper.toEntity(growingLocationDTO);
        GrowingLocation createdGrowingLocation = growingLocationRepository.save(growingLocation);
        return GrowingLocationMapper.toDTO(createdGrowingLocation);
    }

    public GrowingLocationDTO updateGrowingLocation(Long id, GrowingLocationDTO growingLocationDTO) {
        Optional<GrowingLocation> optionalGrowingLocation = growingLocationRepository.findById(id);
        if (optionalGrowingLocation.isPresent()) {
            GrowingLocation existingGrowingLocation = optionalGrowingLocation.get();
            existingGrowingLocation.setLocationName(growingLocationDTO.locationName());
            existingGrowingLocation.setOccupied(growingLocationDTO.occupied());
            GrowingLocation updatedGrowingLocation = growingLocationRepository.save(existingGrowingLocation);
            return GrowingLocationMapper.toDTO(updatedGrowingLocation);
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