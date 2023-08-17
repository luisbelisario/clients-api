package com.reservei.clientsapi.domain.model;

import com.reservei.clientsapi.domain.record.AdminData;
import com.reservei.clientsapi.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "admin")
@Entity(name = "Admin")
public class Admin extends Person {

    private String cpfCnpj;
    public static Admin toClient(AdminData data) {
        Admin admin = new Admin();
        admin.setName(data.name());
        admin.setEmail(data.email());
        admin.setCpfCnpj(StringUtils.removeDotsAndDashes(data.cpfCnpj()));
        admin.setRole("ADMIN");
        admin.setCreatedAt(LocalDate.now());

        return admin;
    }

    public static Admin updateClient(Admin admin, AdminData data) {
        admin.setName(data.name());
        admin.setEmail(data.email());
        admin.setCpfCnpj(StringUtils.removeDotsAndDashes(data.cpfCnpj()));
        admin.setUpdatedAt(LocalDate.now());
        return admin;
    }
}
