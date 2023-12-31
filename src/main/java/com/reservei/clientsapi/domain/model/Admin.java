package com.reservei.clientsapi.domain.model;

import com.reservei.clientsapi.domain.record.AdminData;
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
@Table(name = "admin")
@Entity(name = "Admin")
public class Admin extends Person {

    private String cpfCnpj;
    public static Admin toAdmin(AdminData data) {
        Admin admin = new Admin();
        admin.setPublicId(UUID.randomUUID().toString());
        admin.setName(data.name());
        admin.setEmail(data.email());
        admin.setCpfCnpj(StringUtils.removeDotsAndDashes(data.cpfCnpj()));
        admin.setRole("ADMIN");
        admin.setCreatedAt(LocalDate.now());

        return admin;
    }

    public static Admin updateAdmin(Admin admin, AdminData data) {
        admin.setName(data.name());
        admin.setEmail(data.email());
        admin.setCpfCnpj(StringUtils.removeDotsAndDashes(data.cpfCnpj()));
        admin.setUpdatedAt(LocalDate.now());
        return admin;
    }
}
