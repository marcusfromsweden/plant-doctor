package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class BotanicalSpecies {

    private static final int GENERAL_DAYS_TO_GERMINATION_DEFAULT = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    @NotNull
    @Column(unique = true, nullable = false)
    private String latinName;
    @Column(length = 1000)
    private String description;
    private Integer estimatedDaysToGermination = GENERAL_DAYS_TO_GERMINATION_DEFAULT;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
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
