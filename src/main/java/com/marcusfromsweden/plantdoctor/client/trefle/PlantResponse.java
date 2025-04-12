package com.marcusfromsweden.plantdoctor.client.trefle;

import java.util.List;

public class PlantResponse {
    private List<Plant> data;
    private Links links;

    // Getters and Setters
    public List<Plant> getData() {
        return data;
    }

    public void setData(List<Plant> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}