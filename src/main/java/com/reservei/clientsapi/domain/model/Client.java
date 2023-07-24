package com.reservei.clientsapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

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
}
