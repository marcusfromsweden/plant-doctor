package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import com.marcusfromsweden.plantdoctor.exception.DuplicateBotanicalSpeciesNameException;
import com.marcusfromsweden.plantdoctor.repository.BotanicalSpeciesRepository;
import com.marcusfromsweden.plantdoctor.util.PBotanicalSpeciesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BotanicalSpeciesService {

    private final Logger log = LoggerFactory.getLogger(BotanicalSpeciesService.class);
    private final BotanicalSpeciesRepository botanicalSpeciesRepository;

    public BotanicalSpeciesService(BotanicalSpeciesRepository botanicalSpeciesRepository) {
        this.botanicalSpeciesRepository = botanicalSpeciesRepository;
    }

    public List<BotanicalSpeciesDTO> getAllBotanicalSpecies() {
        return botanicalSpeciesRepository.findAll().stream()
                .map(PBotanicalSpeciesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesById(Long id) {
        return botanicalSpeciesRepository.findById(id)
                .map(PBotanicalSpeciesMapper::toDTO);
    }

    public BotanicalSpeciesDTO createBotanicalSpecies(BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies botanicalSpecies = PBotanicalSpeciesMapper.toEntity(botanicalSpeciesDTO);
        try {
            BotanicalSpecies createdBotanicalSpecies = botanicalSpeciesRepository.save(botanicalSpecies);
            return PBotanicalSpeciesMapper.toDTO(createdBotanicalSpecies);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateBotanicalSpeciesNameException("Plant species name already exists: " + botanicalSpecies.getName());
        }
    }

    public BotanicalSpeciesDTO updateBotanicalSpecies(Long id,
                                                      BotanicalSpeciesDTO botanicalSpeciesDTO) {
        //todo add entity specific exception
        //todo change to ...orElseThrow
        Optional<BotanicalSpecies> optionalBotanicalSpecies = botanicalSpeciesRepository.findById(id);
        if (optionalBotanicalSpecies.isPresent()) {
            BotanicalSpecies existingBotanicalSpecies = optionalBotanicalSpecies.get();
            existingBotanicalSpecies.setName(botanicalSpeciesDTO.name());
            existingBotanicalSpecies.setDescription(botanicalSpeciesDTO.description());
            existingBotanicalSpecies.setEstimatedDaysToGermination(botanicalSpeciesDTO.estimatedDaysToGermination());
            try {
                BotanicalSpecies updatedBotanicalSpecies = botanicalSpeciesRepository.save(existingBotanicalSpecies);
                return PBotanicalSpeciesMapper.toDTO(updatedBotanicalSpecies);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicateBotanicalSpeciesNameException("Plant species name already exists: " + existingBotanicalSpecies.getName());
            }
        } else {
            throw new RuntimeException("BotanicalSpecies not found with id " + id);
        }
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesByName(String name) {
        return botanicalSpeciesRepository.findByName(name)
                .map(PBotanicalSpeciesMapper::toDTO);
    }

    public void deleteBotanicalSpecies(Long id) {
        botanicalSpeciesRepository.deleteById(id);
    }

    public BotanicalSpeciesDTO getOrCreateBotanicalSpeciesByName(String botanicalSpeciesName) {
        Optional<BotanicalSpeciesDTO> botanicalSpecies = getBotanicalSpeciesByName(botanicalSpeciesName);

        if (botanicalSpecies.isPresent()) {
            log.debug("Plant species {} found", botanicalSpeciesName);
            return botanicalSpecies.get();
        }

        log.debug("Plant species {} not found, creating a new one", botanicalSpeciesName);
        BotanicalSpeciesDTO botanicalSpeciesDTO = BotanicalSpeciesDTO.builder().name(botanicalSpeciesName).build();

        log.debug("Created plant species {}", botanicalSpeciesDTO);

        return createBotanicalSpecies(botanicalSpeciesDTO);
    }

    public BotanicalSpecies getBotanicalSpeciesEntityByIdOrThrow(Long botanicalSpeciesId) {
        //todo update when entity specific exception is added
        return botanicalSpeciesRepository.findById(botanicalSpeciesId)
                .orElseThrow(() -> new RuntimeException("BotanicalSpecies not found with id " + botanicalSpeciesId));
    }
}