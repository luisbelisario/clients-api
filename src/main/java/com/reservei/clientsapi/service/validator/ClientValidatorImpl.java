package com.reservei.clientsapi.service.validator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientValidatorImpl implements ClientValidator {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public boolean emailExistsOnDatabase(String email) {
        Client client = clientRepository.findByEmail(email);
        return client != null;
    }

    @Override
    public boolean cpfExistsOnDatabase(String cpf) {
        Client client = clientRepository.findByEmail(cpf);
        return client != null;
    }
}
