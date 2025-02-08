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

    public SeedPackageDTO updateSeedPackage(Long id,
                                            SeedPackageDTO seedPackageDetails) {
        //todo use SeedPackage exceptions instead of RuntimeException
        //todo create a service method getSeedPackageByIdOrThrow
        SeedPackage seedPackage = seedPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + id));

        SeedPackageDTO seedPackageDTO = SeedPackageDTO.builder()
                .id(seedPackage.getId())
                .plantSpeciesId(seedPackageDetails.plantSpeciesId())
                .name(seedPackageDetails.name())
                .build();

        return seedPackageMapper.toDTO(seedPackageRepository.save(seedPackageMapper.toEntity(seedPackageDTO)));
    }

    public void deleteSeedPackage(Long id) {
        //todo add entity specific exception
        SeedPackage seedPackage = seedPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeedPackage not found with ID: " + id));
        seedPackageRepository.delete(seedPackage);
    }

    public SeedPackageDTO getOrCreateSeedPackageByNameAndPlantSpeciesId(String seedPackageName,
                                                                        Long plantSpeciesId) {
        List<SeedPackage> seedPackages = seedPackageRepository.findByNameAndPlantSpeciesId(seedPackageName, plantSpeciesId);
        if (seedPackages.size() > 1) {
            //todo add entity specific exception
            throw new RuntimeException("Multiple SeedPackages found with name: %s and plant species id: %d"
                                               .formatted(seedPackageName, plantSpeciesId));
        }

        if (seedPackages.isEmpty()) {
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