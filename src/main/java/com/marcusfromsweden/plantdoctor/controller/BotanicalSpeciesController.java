package com.marcusfromsweden.plantdoctor.controller;

import com.marcusfromsweden.plantdoctor.dto.BotanicalSpeciesDTO;
import com.marcusfromsweden.plantdoctor.service.BotanicalSpeciesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plant-species")
public class BotanicalSpeciesController {

    private final BotanicalSpeciesService botanicalSpeciesService;

    public BotanicalSpeciesController(BotanicalSpeciesService botanicalSpeciesService) {
        this.botanicalSpeciesService = botanicalSpeciesService;
    }

    @GetMapping
    public ResponseEntity<List<BotanicalSpeciesDTO>> getAllBotanicalSpecies() {
        List<BotanicalSpeciesDTO> botanicalSpeciesDTOList = botanicalSpeciesService.getAllBotanicalSpecies();
        return new ResponseEntity<>(botanicalSpeciesDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotanicalSpeciesDTO> getBotanicalSpeciesById(@PathVariable Long id) {
        Optional<BotanicalSpeciesDTO> botanicalSpeciesDTO = botanicalSpeciesService.getBotanicalSpeciesById(id);
        return botanicalSpeciesDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BotanicalSpeciesDTO> createBotanicalSpecies(
            @Valid @RequestBody BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpeciesDTO createdBotanicalSpeciesDTO = botanicalSpeciesService.createBotanicalSpecies(botanicalSpeciesDTO);
        return new ResponseEntity<>(createdBotanicalSpeciesDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BotanicalSpeciesDTO> updateBotanicalSpecies(@PathVariable Long id,
                                                                      @Valid @RequestBody BotanicalSpeciesDTO botanicalSpeciesDTO) {
        BotanicalSpeciesDTO updatedBotanicalSpeciesDTO = botanicalSpeciesService.updateBotanicalSpecies(id, botanicalSpeciesDTO);
        return new ResponseEntity<>(updatedBotanicalSpeciesDTO, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<BotanicalSpeciesDTO> getBotanicalSpeciesByName(@PathVariable String name) {
        Optional<BotanicalSpeciesDTO> botanicalSpeciesDTO = botanicalSpeciesService.getBotanicalSpeciesByName(name);
        return botanicalSpeciesDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBotanicalSpecies(@PathVariable Long id) {
        botanicalSpeciesService.deleteBotanicalSpecies(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}