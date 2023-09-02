package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;


public interface ClientValidator {

    void validateCreation(Client client) throws Exception;

    void validateUpdate(Client client, Client updatedClient) throws Exception;
}
