package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrowingLocationRepository extends JpaRepository<GrowingLocation, Long> {
    Optional<GrowingLocation> findByName(String name);
}
