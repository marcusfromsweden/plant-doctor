package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.entity.PlantSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantSpeciesRepository extends JpaRepository<PlantSpecies, Long> {
    Optional<PlantSpecies> findByName(String name);
}