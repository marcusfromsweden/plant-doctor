package com.marcusfromsweden.plantdoctor.dto;

public record SeedPackageDTO(
        Long id,
        Long plantSpeciesId,
        String name,
        Integer numberOfSeeds
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long plantSpeciesId;
        private String name;
        private Integer numberOfSeeds;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder plantSpeciesId(Long plantSpeciesId) {
            this.plantSpeciesId = plantSpeciesId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder numberOfSeeds(Integer numberOfSeeds) {
            this.numberOfSeeds = numberOfSeeds;
            return this;
        }

        public SeedPackageDTO build() {
            return new SeedPackageDTO(id, plantSpeciesId, name, numberOfSeeds);
        }
    }
}