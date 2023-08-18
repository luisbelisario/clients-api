package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.CpfRegisteredException;
import com.reservei.clientsapi.exception.EmailRegisteredException;
import com.reservei.clientsapi.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class CpfValidator implements ClientValidator {

    private final ClientService clientService;

    public CpfValidator(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void validate(Client client) throws CpfRegisteredException {
        if ((clientService.findByCpf(client.getCpf()) != null)){
            throw new CpfRegisteredException("Cpf já cadastrado");
        }
    }

    @Override
    public void validateUpdate(Client client, Client updatedClient) throws Exception {
        if (!(client.getCpf().equals(updatedClient.getCpf())) &&
                (clientService.findByCpf(client.getCpf()) != null)) {
            throw new EmailRegisteredException("CPF já cadastrado");
        }
    }
}
