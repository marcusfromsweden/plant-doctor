package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.entity.BotanicalSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotanicalSpeciesRepository extends JpaRepository<BotanicalSpecies, Long> {
    Optional<BotanicalSpecies> findByName(String name);
}