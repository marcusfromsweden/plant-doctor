package com.marcusfromsweden.plantdoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcusfromsweden.plantdoctor.entity.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
}