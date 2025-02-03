package com.marcusfromsweden.plantdoctor.dto;

import java.time.LocalDate;

public record PlantDTO(
        Long id,
        Long plantSpeciesId,
        Long growingLocationId,
        LocalDate plantingDate,
        LocalDate germinationDate,
        String comment
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long plantSpeciesId;
        private Long growingLocationId;
        private LocalDate plantingDate;
        private LocalDate germinationDate;
        private String comment;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder plantSpeciesId(Long plantSpeciesId) {
            this.plantSpeciesId = plantSpeciesId;
            return this;
        }

        public Builder growingLocationId(Long growingLocationId) {
            this.growingLocationId = growingLocationId;
            return this;
        }

        public Builder plantingDate(LocalDate plantingDate) {
            this.plantingDate = plantingDate;
            return this;
        }

        public Builder germinationDate(LocalDate germinationDate) {
            this.germinationDate = germinationDate;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public PlantDTO build() {
            return new PlantDTO(id, plantSpeciesId, growingLocationId, plantingDate, germinationDate, comment);
        }
    }
}