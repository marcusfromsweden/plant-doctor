package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import com.marcusfromsweden.plantdoctor.exception.BotanicalSpeciesNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.exception.DuplicateBotanicalSpeciesNameException;
import com.marcusfromsweden.plantdoctor.repository.BotanicalSpeciesRepository;
import com.marcusfromsweden.plantdoctor.util.BotanicalSpeciesMapper;
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
                .map(BotanicalSpeciesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesById(Long id) {
        return botanicalSpeciesRepository.findById(id)
                .map(BotanicalSpeciesMapper::toDTO);
    }

    public BotanicalSpeciesDTO createBotanicalSpecies(BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies botanicalSpecies = BotanicalSpeciesMapper.toEntity(botanicalSpeciesDTO);
        BotanicalSpecies createdBotanicalSpecies = botanicalSpeciesRepository.save(botanicalSpecies);
        return BotanicalSpeciesMapper.toDTO(createdBotanicalSpecies);
    }

    public BotanicalSpeciesDTO updateBotanicalSpecies(Long id,
                                                      BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies existingBotanicalSpecies = getBotanicalSpeciesEntityByIdOrThrow(botanicalSpeciesDTO.id());
        existingBotanicalSpecies.setName(botanicalSpeciesDTO.name());
        BotanicalSpecies updatedBotanicalSpecies;
        try {
            updatedBotanicalSpecies = botanicalSpeciesRepository.save(existingBotanicalSpecies);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateBotanicalSpeciesNameException(existingBotanicalSpecies.getName());
        }

        updatedBotanicalSpecies.setDescription(botanicalSpeciesDTO.description());
        updatedBotanicalSpecies.setEstimatedDaysToGermination(botanicalSpeciesDTO.estimatedDaysToGermination());
        BotanicalSpecies finalBotanicalSpecies = botanicalSpeciesRepository.save(updatedBotanicalSpecies);
        return BotanicalSpeciesMapper.toDTO(finalBotanicalSpecies);
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesByName(String name) {
        return botanicalSpeciesRepository.findByName(name)
                .map(BotanicalSpeciesMapper::toDTO);
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
        return botanicalSpeciesRepository.findById(botanicalSpeciesId)
                .orElseThrow(() -> new BotanicalSpeciesNotFoundByIdException(botanicalSpeciesId));
    }

    private BotanicalSpeciesDTO getBotanicalSpeciesByIdOrThrow(Long id) {
        return BotanicalSpeciesMapper.toDTO(getBotanicalSpeciesEntityByIdOrThrow(id));
    }


}