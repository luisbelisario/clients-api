package com.reservei.clientsapi.repository;

import com.reservei.clientsapi.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);

    Optional<Admin> findByPublicId(String publicId);

    Admin findByCpfCnpj(String cpfCnpj);
}
