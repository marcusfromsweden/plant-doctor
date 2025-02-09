package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.dto.PlantCommentDTO;
import com.marcusfromsweden.plantdoctor.dto.mapper.PlantCommentMapper;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.repository.PlantCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlantCommentService {

    private final PlantCommentRepository plantCommentRepository;
    private final PlantCommentMapper plantCommentMapper;
    private final PlantService plantService;

    public PlantCommentService(PlantCommentRepository plantCommentRepository,
                               PlantCommentMapper plantCommentMapper,
                               PlantService plantService) {
        this.plantCommentRepository = plantCommentRepository;
        this.plantCommentMapper = plantCommentMapper;
        this.plantService = plantService;
    }

    public PlantCommentDTO createComment(Long plantId,
                                         String text) {
        PlantCommentDTO plantCommentDTO = PlantCommentDTO.builder()
                .plantId(plantId)
                .text(text)
                .createdDate(LocalDateTime.now())
                .build();
        PlantComment plantComment = plantCommentMapper.toEntity(plantCommentDTO);
        plantCommentRepository.save(plantComment);
        return plantCommentMapper.toDto(plantComment);
    }

    public List<PlantComment> getCommentsByPlantId(Long plantId) {
        return plantCommentRepository.findAllByPlantId(plantId);
    }
}