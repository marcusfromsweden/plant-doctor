package com.marcusfromsweden.plantdoctor.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlantSpecies plantSpecies;

    @ManyToOne
    private GrowingLocation growingLocation;

    private LocalDate plantingDate;
    private LocalDate germinationDate;
    private String comment;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlantSpecies getPlantSpecies() {
        return plantSpecies;
    }

    public void setPlantSpecies(PlantSpecies plantSpecies) {
        this.plantSpecies = plantSpecies;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
