package com.reservei.clientsapi.domain.record;

public record UserData(
        String publicId,
        String login,
        String password,
        String role) {
}
