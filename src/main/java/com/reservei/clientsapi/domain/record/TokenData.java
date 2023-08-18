package com.reservei.clientsapi.domain.record;

public record TokenData(String token) {

    public static TokenData toData(String token) {
        return new TokenData(token);
    }
}
