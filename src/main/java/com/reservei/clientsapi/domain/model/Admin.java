package com.reservei.clientsapi.domain.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "admin")
@Entity(name = "Admin")
public class Admin extends Person {

    private String cpfCnpj;
}
