package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;


public interface ClientValidator {

    void validate(Client client) throws Exception;
}
