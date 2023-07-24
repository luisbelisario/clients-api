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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String phone;
    private String rg;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
