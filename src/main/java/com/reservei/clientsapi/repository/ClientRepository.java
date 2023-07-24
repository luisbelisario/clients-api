package com.reservei.clientsapi.repository;

import com.reservei.clientsapi.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
