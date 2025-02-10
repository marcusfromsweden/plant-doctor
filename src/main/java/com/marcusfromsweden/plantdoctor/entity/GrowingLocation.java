package com.marcusfromsweden.plantdoctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class GrowingLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

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

    public void setName(String locationName) {
        this.name = locationName;
    }

}
