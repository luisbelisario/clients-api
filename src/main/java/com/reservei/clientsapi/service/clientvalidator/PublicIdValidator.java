package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.PublicIdRegisteredException;
import com.reservei.clientsapi.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class PublicIdValidator implements ClientValidator {

    private final ClientService clientService;

    public PublicIdValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void validate(Client client) throws Exception {
        if ((clientService.findByPublicIdValidation(client.getPublicId()).isPresent())){
            throw new PublicIdRegisteredException("Public Id já cadastrado");
        }
    }
}
