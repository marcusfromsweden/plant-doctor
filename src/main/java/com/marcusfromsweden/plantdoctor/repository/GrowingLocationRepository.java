package com.marcusfromsweden.plantdoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcusfromsweden.plantdoctor.entity.GrowingLocation;

@Repository
public interface GrowingLocationRepository extends JpaRepository<GrowingLocation, Long> {
}
