package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.SeedPackageDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.SeedPackageMapper;
import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import com.marcusfromsweden.plantdoctor.exception.MultipleSeedPackageFoundException;
import com.marcusfromsweden.plantdoctor.exception.SeedPackageNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.repository.SeedPackageRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public SeedPackageDTO updateSeedPackage(Long id,
                                            SeedPackageDTO seedPackageDetails) {
        SeedPackage seedPackage = getSeedPackageEntityByIdOrThrow(id);
        seedPackageMapper.updateEntityUsingDTO(seedPackage, seedPackageDetails);
        return seedPackageMapper.toDTO(seedPackage);
    }

    public void deleteSeedPackage(Long id) {
        seedPackageRepository.delete(getSeedPackageEntityByIdOrThrow(id));
    }

    public SeedPackageDTO getOrCreateSeedPackageByNameAndBotanicalSpeciesId(String seedPackageName,
                                                                            Long botanicalSpeciesId) {
        List<SeedPackage> seedPackages = seedPackageRepository.findByNameAndBotanicalSpeciesId(seedPackageName, botanicalSpeciesId);
        if (seedPackages.size() > 1) {
            throw new MultipleSeedPackageFoundException("Multiple SeedPackages found with name: %s and plant species id: %d"
                                                                .formatted(seedPackageName, botanicalSpeciesId));
        }

        if (seedPackages.isEmpty()) {
            SeedPackageDTO seedPackageDTO = SeedPackageDTO.builder()
                    .name(seedPackageName)
                    .botanicalSpeciesId(botanicalSpeciesId)
                    .numberOfSeeds(null)
                    .build();
            return createSeedPackage(seedPackageDTO);
        }

        return seedPackageMapper.toDTO(seedPackages.get(0));
    }

    public SeedPackage getSeedPackageEntityByIdOrThrow(Long id) {
        return seedPackageRepository.findById(id)
                .orElseThrow(() -> new SeedPackageNotFoundByIdException(id));
    }
}