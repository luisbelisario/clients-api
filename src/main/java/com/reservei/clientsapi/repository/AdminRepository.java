package com.reservei.clientsapi.repository;

import com.reservei.clientsapi.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
