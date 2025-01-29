package com.marcusfromsweden.plantdoctorh2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcusfromsweden.plantdoctorh2.entity.GrowingLocation;

@Repository
public interface GrowingLocationRepository extends JpaRepository<GrowingLocation, Long> {
}
