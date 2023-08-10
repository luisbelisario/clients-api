package com.reservei.clientsapi.domain.record;

public record UserData(
        String public_id,
        String email,
        String password,
        String role) {
}
