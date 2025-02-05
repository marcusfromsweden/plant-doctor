package com.marcusfromsweden.plantdoctor.dto;

public record GrowingLocationDTO(
        Long id,
        String name,
        boolean occupied
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private boolean occupied;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String locationName) {
            this.name = locationName;
            return this;
        }

        public Builder occupied(boolean occupied) {
            this.occupied = occupied;
            return this;
        }

        public GrowingLocationDTO build() {
            return new GrowingLocationDTO(id, name, occupied);
        }
    }
}