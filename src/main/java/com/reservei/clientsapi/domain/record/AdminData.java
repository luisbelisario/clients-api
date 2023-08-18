package com.reservei.clientsapi.domain.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminData(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        String cpfCnpj) {

    public String getCpfCnpj() {
        return cpfCnpj;
    }
}
