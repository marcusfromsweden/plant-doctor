package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import com.marcusfromsweden.plantdoctor.util.SeedPackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeedPackageService {

    private final SeedPackageRepository seedPackageRepository;
    private final SeedPackageMapper seedPackageMapper;

    @Autowired
    public SeedPackageService(SeedPackageRepository seedPackageRepository,
                              SeedPackageMapper seedPackageMapper) {
        this.seedPackageRepository = seedPackageRepository;
        this.seedPackageMapper = seedPackageMapper;
    }

    public List<SeedPackageDTO> getAllSeedPackages() {
        return seedPackageRepository.findAll().stream().map(seedPackageMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<SeedPackageDTO> getSeedPackageById(Long id) {
        return seedPackageRepository.findById(id).map(seedPackageMapper::toDTO);
    }

    public SeedPackageDTO createSeedPackage(SeedPackageDTO seedPackageDTO) {
        SeedPackage seedPackage = seedPackageRepository.save(seedPackageMapper.toEntity(seedPackageDTO));
        return seedPackageMapper.toDTO(seedPackage);
    }

    public SeedPackage updateSeedPackage(Long id,
                                         SeedPackage seedPackageDetails) {
        SeedPackage seedPackage = seedPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + id));

        seedPackage.setPlantSpecies(seedPackageDetails.getPlantSpecies());
        seedPackage.setName(seedPackageDetails.getName());
        seedPackage.setNumberOfSeeds(seedPackageDetails.getNumberOfSeeds());

        return seedPackageRepository.save(seedPackage);
    }

    public void deleteSeedPackage(Long id) {
        SeedPackage seedPackage = seedPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + id));
        seedPackageRepository.delete(seedPackage);
    }

    public SeedPackageDTO getOrCreateSeedPackageByNameAndPlantSpeciesId(String seedPackageName,
                                                                        Long plantSpeciesId) {
        List<SeedPackage> seedPackages = seedPackageRepository.findByNameAndPlantSpeciesId(seedPackageName, plantSpeciesId);
        if (seedPackages.size() > 1) {
            throw new RuntimeException("Multiple SeedPackages found with name: %s and plant species id: %d"
                                               .formatted(seedPackageName, plantSpeciesId));
        }

        if (seedPackages.isEmpty()) {
            // use createSeedPackage(SeedPackageDTO) and SeedPackageDTO.builder to create a new SeedPackage
            SeedPackageDTO seedPackageDTO = SeedPackageDTO.builder()
                    .name(seedPackageName)
                    .plantSpeciesId(plantSpeciesId)
                    .numberOfSeeds(null)
                    .build();
            return createSeedPackage(seedPackageDTO);
        }

        return seedPackageMapper.toDTO(seedPackages.get(0));
    }
}