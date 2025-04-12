package com.marcusfromsweden.plantdoctor.client.trefle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Plant {
    private Long id;
    @JsonProperty("scientific_name")
    private String scientificName;
    @JsonProperty("common_name")
    private String commonName;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}