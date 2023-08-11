package com.reservei.clientsapi.domain.record;

public record UserData(
        String publicId,
        String email,
        String password,
        String role) {
}
