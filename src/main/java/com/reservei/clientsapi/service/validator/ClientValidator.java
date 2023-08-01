package com.reservei.clientsapi.service.validator;

import com.reservei.clientsapi.domain.model.Person;

public interface ClientValidator {

    boolean emailExistsOnDatabase(String email);

    boolean cpfExistsOnDatabase(String cpf);
}
