package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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

}
