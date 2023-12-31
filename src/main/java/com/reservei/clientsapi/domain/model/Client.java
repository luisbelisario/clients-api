package com.reservei.clientsapi.domain.model;

import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "client")
@Entity(name = "Client")
public class Client extends Person {

    private String cpf;
    private String phone;

    public static Client toClient(ClientData data) {
        Client client = new Client();
        client.setPublicId(UUID.randomUUID().toString());
        client.setName(data.name());
        client.setEmail(data.email());
        client.setCpf(StringUtils.removeDotsAndDashes(data.cpf()));
        client.setPhone(StringUtils.removeDotsAndDashes(data.phone()));
        client.setRole("USER");
        client.setCreatedAt(LocalDate.now());

        return client;
    }

    public static Client updateClient(Client client, ClientData data) {
        client.setName(data.name());
        client.setEmail(data.email());
        client.setCpf(StringUtils.removeDotsAndDashes(data.cpf()));
        client.setPhone(StringUtils.removeDotsAndDashes(data.phone()));
        client.setUpdatedAt(LocalDate.now());

        return client;
    }
}
