package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.GrowingLocationDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.GrowingLocationMapper;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import com.marcusfromsweden.plantdoctor.exception.DuplicateGrowingLocationNameException;
import com.marcusfromsweden.plantdoctor.exception.GrowingLocationNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.repository.GrowingLocationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrowingLocationService {

    private static final Logger log = LoggerFactory.getLogger(GrowingLocationService.class);
    private final GrowingLocationRepository growingLocationRepository;
    private final GrowingLocationMapper growingLocationMapper;

    public GrowingLocationService(GrowingLocationRepository growingLocationRepository,
                                  GrowingLocationMapper growingLocationMapper) {
        this.growingLocationRepository = growingLocationRepository;
        this.growingLocationMapper = growingLocationMapper;
    }

    public List<GrowingLocationDTO> getAllGrowingLocations() {
        return growingLocationRepository.findAll().stream()
                .map(growingLocationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<GrowingLocationDTO> getGrowingLocationById(Long id) {
        return growingLocationRepository.findById(id)
                .map(growingLocationMapper::toDTO);
    }

    public GrowingLocationDTO createGrowingLocation(GrowingLocationDTO growingLocationDTO) {
        assertNonDuplicateName(growingLocationDTO);
        GrowingLocation growingLocation = growingLocationMapper.toEntity(growingLocationDTO);
        growingLocation = growingLocationRepository.save(growingLocation);
        return growingLocationMapper.toDTO(growingLocation);
    }

    @Transactional
    public GrowingLocationDTO updateGrowingLocation(Long id,
                                                    GrowingLocationDTO growingLocationDTO) {
        assertNonDuplicateName(growingLocationDTO);
        GrowingLocation growingLocation = getGrowingLocationEntityByIdOrThrow(id);
        growingLocationMapper.updateEntityUsingDTO(growingLocation, growingLocationDTO);
        return growingLocationMapper.toDTO(growingLocation);
    }

    public Optional<GrowingLocationDTO> getGrowingLocationByName(String name) {
        return growingLocationRepository.findByName(name)
                .map(growingLocationMapper::toDTO);
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

    private void assertNonDuplicateName(GrowingLocationDTO growingLocationDTO) {
        if (growingLocationRepository.findByName(growingLocationDTO.name()).isPresent()) {
            throw new DuplicateGrowingLocationNameException(growingLocationDTO.name());
        }
    }
}