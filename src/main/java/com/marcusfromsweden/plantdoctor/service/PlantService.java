package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantDTO;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.exception.PlantNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.repository.PlantRepository;
import com.marcusfromsweden.plantdoctor.util.PlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantService {

    //todo add validation of DTOs

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;
    private final GrowingLocationService growingLocationService;
    private final SeedPackageService seedPackageService;

    @Autowired
    public PlantService(PlantRepository plantRepository,
                        PlantMapper plantMapper,
                        GrowingLocationService growingLocationService,
                        SeedPackageService seedPackageService) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
        this.growingLocationService = growingLocationService;
        this.seedPackageService = seedPackageService;
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
        Plant plant = new Plant();
        updatePlantUsingDTO(plant, plantDTO);
        return plantMapper.toDTO(plantRepository.save(plant));
    }

    public PlantDTO updatePlant(Long plantId,
                                PlantDTO plantDTO) {
        Plant plant = getPlantEntityByIdOrThrow(plantId);
        updatePlantUsingDTO(plant, plantDTO);
        Plant updatedPlant = plantRepository.save(plant);
        return plantMapper.toDTO(updatedPlant);
    }

    private void updatePlantUsingDTO(Plant plant,
                                     PlantDTO plantDTO) {
        //todo use PlantMapper.toEntity ..?

        plant.setSeedPackage(seedPackageService.getSeedPackageEntityByIdOrThrow(plantDTO.seedPackageId()));
        plant.setGrowingLocation(growingLocationService.getGrowingLocationEntityByIdOrThrow(plantDTO.growingLocationId()));

        plant.setPlantingDate(plantDTO.plantingDate());
        plant.setGerminationDate(plantDTO.germinationDate());
    }

    public Plant getPlantEntityByIdOrThrow(Long id) {
        return plantRepository.findById(id).orElseThrow(() -> new PlantNotFoundByIdException(id));
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}