package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.PlantMapper;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.exception.PlantNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    @Autowired
    public PlantService(PlantRepository plantRepository,
                        PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

    public List<PlantDTO> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(plantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlantDTO> getPlantById(Long id) {
        return plantRepository.findById(id)
                .map(plantMapper::toDTO);
    }

    public PlantDTO createPlant(PlantDTO plantDTO) {
        Plant plant = plantMapper.toEntity(plantDTO);
        return plantMapper.toDTO(plantRepository.save(plant));
    }

    @Transactional
    public PlantDTO updatePlant(Long plantId,
                                PlantDTO plantDTO) {
        Plant plant = getPlantEntityByIdOrThrow(plantId);
        plantMapper.updateEntityUsingDTO(plant, plantDTO);
        return plantMapper.toDTO(plant);
    }

    public Plant getPlantEntityByIdOrThrow(Long id) {
        return plantRepository.findById(id).orElseThrow(() -> new PlantNotFoundByIdException(id));
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}