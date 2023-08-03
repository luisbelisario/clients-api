package com.reservei.clientsapi.domain.dto;

import com.reservei.clientsapi.domain.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDto {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String rg;

    public static ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setCpf(client.getCpf());
        dto.setPhone(client.getPhone());

        return dto;
    }
}
