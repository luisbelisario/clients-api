package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.EmailRegisteredException;
import com.reservei.clientsapi.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements ClientValidator {

    private final ClientService clientService;

    public EmailValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void validate(Client client) throws EmailRegisteredException {
        if ((clientService.findByEmail(client.getEmail()) != null)) {
            throw new EmailRegisteredException("Email já cadastrado");
        }
    }
}