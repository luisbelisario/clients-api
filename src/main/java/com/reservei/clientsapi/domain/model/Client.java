package com.reservei.clientsapi.domain.model;

import com.reservei.clientsapi.domain.record.ClientData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String rg;

    public static Client toClient(ClientData data) {
        Client client = new Client();
        client.setName(data.name());
        client.setEmail(data.email());
        client.setCpf(data.cpf());
        client.setPhone(data.phone());
        client.setRg(data.rg());
        client.setRole("ROLE_USER");
        client.setCreatedAt(LocalDate.now());

        return client;
    }
}
