package com.reservei.clientsapi.repository;

import com.reservei.clientsapi.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);

    Client findByCpf(String cpf);

    Optional<Client> findByPublicId(String publicId);
}
