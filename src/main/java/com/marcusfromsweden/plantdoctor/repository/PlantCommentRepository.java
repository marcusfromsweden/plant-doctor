package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.entity.PlantComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantCommentRepository extends JpaRepository<PlantComment, Long> {
    List<PlantComment> findAllByPlantId(Long plantId);
}