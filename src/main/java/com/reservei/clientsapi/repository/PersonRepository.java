package com.reservei.clientsapi.repository;

import com.reservei.clientsapi.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
