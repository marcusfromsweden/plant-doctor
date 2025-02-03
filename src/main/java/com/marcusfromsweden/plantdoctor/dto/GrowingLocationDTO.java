package com.marcusfromsweden.plantdoctor.dto;

public record GrowingLocationDTO(
        Long id,
        String locationName,
        boolean occupied
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String locationName;
        private boolean occupied;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder locationName(String locationName) {
            this.locationName = locationName;
            return this;
        }

        public Builder occupied(boolean occupied) {
            this.occupied = occupied;
            return this;
        }

        public GrowingLocationDTO build() {
            return new GrowingLocationDTO(id, locationName, occupied);
        }
    }
}