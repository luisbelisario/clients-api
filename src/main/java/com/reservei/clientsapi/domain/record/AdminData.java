package com.reservei.clientsapi.domain.record;

public record AdminData(String name, String email, String cpfCnpj) {
    public String getCpfCnpj() {
        return cpfCnpj;
    }
}
