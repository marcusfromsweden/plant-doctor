package com.marcusfromsweden.plantdoctor.service;

import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.repository.PlantCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlantCommentService {

    private final PlantCommentRepository plantCommentRepository;
    private final PlantService plantService;

    public PlantCommentService(PlantCommentRepository plantCommentRepository, PlantService plantService) {
        this.plantCommentRepository = plantCommentRepository;
        this.plantService = plantService;
    }

    public PlantComment addComment(Long plantId, String text) {
        Plant plant = plantService.getPlantEntityByIdOrThrow(plantId);
        PlantComment comment = new PlantComment();
        comment.setPlant(plant);
        comment.setText(text);
        comment.setCreatedDate(LocalDateTime.now());

        return plantCommentRepository.save(comment);
    }

    public List<PlantComment> getCommentsByPlantId(Long plantId) {
        return plantCommentRepository.findAllByPlantId(plantId);
    }
}