package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.EmailRegisteredException;
import com.reservei.clientsapi.repository.ClientRepository;
import com.reservei.clientsapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements ClientValidator {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void validateCreation(Client client) throws EmailRegisteredException {
        if (clientRepository.findByEmail(client.getEmail()) != null) {
            throw new EmailRegisteredException("Email já cadastrado");
        }
    }

    @Override
    public void validateUpdate(Client client, Client updatedClient) throws Exception {
        if (!(client.getEmail().equals(updatedClient.getEmail())) &&
                (clientRepository.findByEmail(client.getEmail()) != null)) {
            throw new EmailRegisteredException("Email já cadastrado");
        }
    }
}
