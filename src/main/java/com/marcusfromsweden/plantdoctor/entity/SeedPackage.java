package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class SeedPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "botanical_species_id", nullable = false)
    private BotanicalSpecies botanicalSpecies;

    @NotNull
    @Column(nullable = false)
    private String name;

    @PositiveOrZero
    private Integer numberOfSeeds;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BotanicalSpecies getBotanicalSpecies() {
        return botanicalSpecies;
    }

    public void setBotanicalSpecies(BotanicalSpecies botanicalSpecies) {
        this.botanicalSpecies = botanicalSpecies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfSeeds() {
        return numberOfSeeds;
    }

    public void setNumberOfSeeds(Integer numberOfSeeds) {
        this.numberOfSeeds = numberOfSeeds;
    }
}