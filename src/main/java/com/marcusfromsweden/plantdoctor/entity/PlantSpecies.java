package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class PlantSpecies {

    private static final int GENERAL_DAYS_TO_GERMINATION = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private Integer estimatedDaysToGermination = GENERAL_DAYS_TO_GERMINATION;

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

    public Integer getEstimatedDaysToGermination() {
        return estimatedDaysToGermination;
    }

    public void setEstimatedDaysToGermination(Integer estimatedDaysToGermination) {
        this.estimatedDaysToGermination = estimatedDaysToGermination;
    }
}
