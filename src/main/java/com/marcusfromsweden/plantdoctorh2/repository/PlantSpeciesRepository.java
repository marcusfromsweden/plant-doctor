package com.marcusfromsweden.plantdoctorh2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcusfromsweden.plantdoctorh2.entity.PlantSpecies;

@Repository
public interface PlantSpeciesRepository extends JpaRepository<PlantSpecies, Long> {
}