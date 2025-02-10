package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.BotanicalSpeciesMapper;
import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import com.marcusfromsweden.plantdoctor.exception.BotanicalSpeciesNotFoundByIdException;
import com.marcusfromsweden.plantdoctor.exception.DuplicateBotanicalSpeciesLatinNameException;
import com.marcusfromsweden.plantdoctor.repository.BotanicalSpeciesRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BotanicalSpeciesService {

    private final Logger log = LoggerFactory.getLogger(BotanicalSpeciesService.class);
    private final BotanicalSpeciesRepository botanicalSpeciesRepository;
    private final BotanicalSpeciesMapper botanicalSpeciesMapper;

    public BotanicalSpeciesService(BotanicalSpeciesRepository botanicalSpeciesRepository,
                                   BotanicalSpeciesMapper botanicalSpeciesMapper) {
        this.botanicalSpeciesRepository = botanicalSpeciesRepository;
        this.botanicalSpeciesMapper = botanicalSpeciesMapper;
    }

    public List<BotanicalSpeciesDTO> getAllBotanicalSpecies() {
        return botanicalSpeciesRepository.findAll().stream()
                .map(botanicalSpeciesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesById(Long id) {
        return botanicalSpeciesRepository.findById(id)
                .map(botanicalSpeciesMapper::toDTO);
    }

    public BotanicalSpeciesDTO createBotanicalSpecies(BotanicalSpeciesDTO botanicalSpeciesDTO) {
        Optional<BotanicalSpecies> existingBotanicalSpecies =
                botanicalSpeciesRepository.findByLatinName(botanicalSpeciesDTO.latinName());
        if (existingBotanicalSpecies.isPresent()) {
            throw new DuplicateBotanicalSpeciesLatinNameException(botanicalSpeciesDTO.latinName());
        }
        BotanicalSpecies botanicalSpecies = botanicalSpeciesMapper.toEntity(botanicalSpeciesDTO);
        botanicalSpeciesRepository.save(botanicalSpecies);
        return botanicalSpeciesMapper.toDTO(botanicalSpecies);
    }

    @Transactional
    public BotanicalSpeciesDTO updateBotanicalSpecies(Long id,
                                                      BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpecies botanicalSpecies = getBotanicalSpeciesEntityByIdOrThrow(id);
        botanicalSpeciesMapper.updateEntityUsingDTO(botanicalSpecies, botanicalSpeciesDTO);
        return botanicalSpeciesMapper.toDTO(botanicalSpecies);
    }

    public Optional<BotanicalSpeciesDTO> getBotanicalSpeciesByLatinName(String latinName) {
        return botanicalSpeciesRepository.findByLatinName(latinName)
                .map(botanicalSpeciesMapper::toDTO);
    }

    public void deleteBotanicalSpecies(Long id) {
        botanicalSpeciesRepository.deleteById(id);
    }

    public BotanicalSpeciesDTO getOrCreateBotanicalSpeciesByName(String botanicalSpeciesName) {
        Optional<BotanicalSpeciesDTO> botanicalSpecies = getBotanicalSpeciesByLatinName(botanicalSpeciesName);

        if (botanicalSpecies.isPresent()) {
            return botanicalSpecies.get();
        }

        log.debug("Botanical species {} not found, creating a new one", botanicalSpeciesName);
        BotanicalSpeciesDTO botanicalSpeciesDTO = BotanicalSpeciesDTO.builder().latinName(botanicalSpeciesName).build();

        return createBotanicalSpecies(botanicalSpeciesDTO);
    }

    public BotanicalSpecies getBotanicalSpeciesEntityByIdOrThrow(Long botanicalSpeciesId) {
        return botanicalSpeciesRepository.findById(botanicalSpeciesId)
                .orElseThrow(() -> new BotanicalSpeciesNotFoundByIdException(botanicalSpeciesId));
    }

}