package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.exception.DuplicateGrowingLocationNameException;
import com.marcusfromsweden.plantdoctor.exception.GrowingLocationNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import com.marcusfromsweden.plantdoctor.util.GrowingLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrowingLocationService {

    private final Logger log = LoggerFactory.getLogger(GrowingLocationService.class);
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
        try {
            GrowingLocation growingLocation = GrowingLocationMapper.toEntity(growingLocationDTO);
            GrowingLocation createdGrowingLocation = growingLocationRepository.save(growingLocation);
            return GrowingLocationMapper.toDTO(createdGrowingLocation);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateGrowingLocationNameException("Growing location name already exists: " + growingLocationDTO.name());
        }
    }

    public GrowingLocationDTO updateGrowingLocation(Long id,
                                                    GrowingLocationDTO growingLocationDTO) {
        GrowingLocation existingGrowingLocation = getGrowingLocationEntityByIdOrThrow(id);

        existingGrowingLocation.setName(growingLocationDTO.name());
        existingGrowingLocation.setOccupied(growingLocationDTO.occupied());
        try {
            GrowingLocation updatedGrowingLocation = growingLocationRepository.save(existingGrowingLocation);
            return GrowingLocationMapper.toDTO(updatedGrowingLocation);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateGrowingLocationNameException("Growing location name already exists: " + growingLocationDTO.name());
        }
    }

    public Optional<GrowingLocationDTO> getGrowingLocationByName(String name) {
        return growingLocationRepository.findByName(name)
                .map(GrowingLocationMapper::toDTO);
    }

    public GrowingLocationDTO getOrCreateGrowingLocationByName(String growingLocationName) {
        Optional<GrowingLocationDTO> growingLocation = getGrowingLocationByName(growingLocationName);

        if (growingLocation.isPresent()) {
            log.debug("Growing location {} found", growingLocationName);
            return growingLocation.get();
        }

        log.debug("Growing location {} not found, creating a new one", growingLocationName);
        GrowingLocationDTO growingLocationDTO =
                createGrowingLocation(GrowingLocationDTO.builder().name(growingLocationName).build());
        log.debug("Created growing location {}", growingLocationDTO);

        return growingLocationDTO;
    }

    public GrowingLocation getGrowingLocationEntityByIdOrThrow(Long id) {
        return growingLocationRepository.findById(id)
                .orElseThrow(() -> new GrowingLocationNotFoundByIdException(id));
    }

    public void deleteGrowingLocation(Long id) {
        growingLocationRepository.deleteById(id);
    }

    public void deleteAllGrowingLocations() {
        growingLocationRepository.deleteAll();
    }
}