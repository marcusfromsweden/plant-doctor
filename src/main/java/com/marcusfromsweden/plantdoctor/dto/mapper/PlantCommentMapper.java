package com.marcusfromsweden.plantdoctor.dto.mapper;

import com.marcusfromsweden.plantdoctor.dto.PlantCommentDTO;
import com.marcusfromsweden.plantdoctor.entity.Plant;
import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import com.marcusfromsweden.plantdoctor.service.PlantService;
import org.springframework.stereotype.Component;

@Component
public class PlantCommentMapper {

    private final PlantService plantService;

    public PlantCommentMapper(PlantService plantService) {
        this.plantService = plantService;
    }

    public PlantCommentDTO toDto(PlantComment plantComment) {
        return PlantCommentDTO.builder()
                .id(plantComment.getId())
                .plantId(plantComment.getPlant().getId())
                .text(plantComment.getText())
                .createdDate(plantComment.getCreatedDate())
                .build();
    }

    public void updateEntityUsingDTO(PlantComment plantComment,
                                     PlantCommentDTO plantCommentDTO) {
        toEntity(plantComment, plantCommentDTO);
    }

    public PlantComment toEntity(PlantCommentDTO plantCommentDTO) {
        PlantComment plantComment = new PlantComment();
        plantComment.setId(plantCommentDTO.id());
        return toEntity(plantComment, plantCommentDTO);
    }

    private PlantComment toEntity(PlantComment plantComment,
                                  PlantCommentDTO plantCommentDTO) {
        Plant plant = plantService.getPlantEntityByIdOrThrow(plantCommentDTO.plantId());
        plantComment.setPlant(plant);
        plantComment.setText(plantCommentDTO.text());
        plantComment.setCreatedDate(plantCommentDTO.createdDate());
        return plantComment;
    }
}