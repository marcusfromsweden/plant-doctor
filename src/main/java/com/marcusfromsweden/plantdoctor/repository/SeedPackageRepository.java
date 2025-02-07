package com.marcusfromsweden.plantdoctor.repository;

import com.marcusfromsweden.plantdoctor.entity.SeedPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeedPackageRepository extends JpaRepository<SeedPackage, Long> {
    List<SeedPackage> findByNameAndPlantSpeciesId(String seedPackageName,
                                                  Long plantSpeciesId);
}