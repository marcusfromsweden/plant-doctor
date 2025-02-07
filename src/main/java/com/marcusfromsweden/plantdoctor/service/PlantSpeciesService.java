package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import com.marcusfromsweden.plantdoctor.exception.DuplicatePlantSpeciesNameException;
import com.marcusfromsweden.plantdoctor.repository.PlantSpeciesRepository;
import com.marcusfromsweden.plantdoctor.util.PlantSpeciesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantSpeciesService {

    private final Logger log = LoggerFactory.getLogger(PlantSpeciesService.class);
    private final PlantSpeciesRepository plantSpeciesRepository;

    public PlantSpeciesService(PlantSpeciesRepository plantSpeciesRepository) {
        this.plantSpeciesRepository = plantSpeciesRepository;
    }

    public List<PlantSpeciesDTO> getAllPlantSpecies() {
        return plantSpeciesRepository.findAll().stream()
                .map(PlantSpeciesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlantSpeciesDTO> getPlantSpeciesById(Long id) {
        return plantSpeciesRepository.findById(id)
                .map(PlantSpeciesMapper::toDTO);
    }

    public PlantSpeciesDTO createPlantSpecies(PlantSpeciesDTO plantSpeciesDTO) {
        PlantSpecies plantSpecies = PlantSpeciesMapper.toEntity(plantSpeciesDTO);
        try {
            PlantSpecies createdPlantSpecies = plantSpeciesRepository.save(plantSpecies);
            return PlantSpeciesMapper.toDTO(createdPlantSpecies);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatePlantSpeciesNameException("Plant species name already exists: " + plantSpecies.getName());
        }
    }

    public PlantSpeciesDTO updatePlantSpecies(Long id,
                                              PlantSpeciesDTO plantSpeciesDTO) {
        Optional<PlantSpecies> optionalPlantSpecies = plantSpeciesRepository.findById(id);
        if (optionalPlantSpecies.isPresent()) {
            PlantSpecies existingPlantSpecies = optionalPlantSpecies.get();
            existingPlantSpecies.setName(plantSpeciesDTO.name());
            existingPlantSpecies.setDescription(plantSpeciesDTO.description());
            existingPlantSpecies.setEstimatedDaysToGermination(plantSpeciesDTO.estimatedDaysToGermination());
            try {
                PlantSpecies updatedPlantSpecies = plantSpeciesRepository.save(existingPlantSpecies);
                return PlantSpeciesMapper.toDTO(updatedPlantSpecies);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicatePlantSpeciesNameException("Plant species name already exists: " + existingPlantSpecies.getName());
            }
        } else {
            throw new RuntimeException("PlantSpecies not found with id " + id);
        }
    }

    public Optional<PlantSpeciesDTO> getPlantSpeciesByName(String name) {
        return plantSpeciesRepository.findByName(name)
                .map(PlantSpeciesMapper::toDTO);
    }

    public void deletePlantSpecies(Long id) {
        plantSpeciesRepository.deleteById(id);
    }

    public PlantSpeciesDTO getOrCreatePlantSpeciesByName(String plantSpeciesName) {
        Optional<PlantSpeciesDTO> plantSpecies = getPlantSpeciesByName(plantSpeciesName);

        if (plantSpecies.isPresent()) {
            log.debug("Plant species {} found", plantSpeciesName);
            return plantSpecies.get();
        }

        log.debug("Plant species {} not found, creating a new one", plantSpeciesName);
        PlantSpeciesDTO plantSpeciesDTO = PlantSpeciesDTO.builder().name(plantSpeciesName).build();

        log.debug("Created plant species {}", plantSpeciesDTO);

        return createPlantSpecies(plantSpeciesDTO);
    }

    public PlantSpecies getPlantSpeciesEntityByIdOrThrow(Long plantSpeciesId) {
        //todo update when entity specific exception is added
        return plantSpeciesRepository.findById(plantSpeciesId)
                .orElseThrow(() -> new RuntimeException("PlantSpecies not found with id " + plantSpeciesId));
    }
}