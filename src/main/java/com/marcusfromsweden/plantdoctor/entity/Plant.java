package com.marcusfromsweden.plantdoctor.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SeedPackage seedPackage;

    @ManyToOne
    private GrowingLocation growingLocation;

    private LocalDate plantingDate;
    private LocalDate germinationDate;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PlantComment> comments;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeedPackage getSeedPackage() {
        return seedPackage;
    }

    public void setSeedPackage(SeedPackage botanicalSpecies) {
        this.seedPackage = botanicalSpecies;
    }

    public GrowingLocation getGrowingLocation() {
        return growingLocation;
    }

    public void setGrowingLocation(GrowingLocation growingLocation) {
        this.growingLocation = growingLocation;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }

    public LocalDate getGerminationDate() {
        return germinationDate;
    }

    public void setGerminationDate(LocalDate germinationDate) {
        this.germinationDate = germinationDate;
    }

    public List<PlantComment> getComments() {
        return comments;
    }

    public void setComments(List<PlantComment> comments) {
        this.comments = comments;
    }
}
