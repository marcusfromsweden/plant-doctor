package com.marcusfromsweden.plantdoctor.auth;

public record AuthenticationRequest(
        String username,
        String password) {
}
