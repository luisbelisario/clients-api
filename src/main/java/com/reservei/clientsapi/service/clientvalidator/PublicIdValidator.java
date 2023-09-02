package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.EmailRegisteredException;
import com.reservei.clientsapi.exception.PublicIdRegisteredException;
import com.reservei.clientsapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicIdValidator implements ClientValidator {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void validateCreation(Client client) throws Exception {
        if ((clientRepository.findByPublicId(client.getPublicId()).isPresent())){
            throw new PublicIdRegisteredException("Public Id já cadastrado");
        }
    }

    @Override
    public void validateUpdate(Client client, Client updatedClient) throws Exception {
        if (!(client.getPublicId().equals(updatedClient.getPublicId())) &&
                (clientRepository.findByPublicId(client.getPublicId()).isPresent())) {
            throw new EmailRegisteredException("Email já cadastrado");
        }
    }
}
