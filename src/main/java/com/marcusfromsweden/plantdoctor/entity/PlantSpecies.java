package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class PlantSpecies {

    private static final int GENERAL_DAYS_TO_GERMINATION = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5)
    private String name;
    private String description;
    private int estimatedDaysToGermination = GENERAL_DAYS_TO_GERMINATION;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedDaysToGermination() {
        return estimatedDaysToGermination;
    }

    public void setEstimatedDaysToGermination(int estimatedDaysToGermination) {
        this.estimatedDaysToGermination = estimatedDaysToGermination;
    }
}
