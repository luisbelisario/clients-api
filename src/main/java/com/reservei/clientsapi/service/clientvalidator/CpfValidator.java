package com.reservei.clientsapi.service.clientvalidator;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.exception.CpfRegisteredException;
import com.reservei.clientsapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CpfValidator implements ClientValidator {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public void validateCreation(Client client) throws CpfRegisteredException {
        if ((clientRepository.findByCpf(client.getCpf()) != null)){
            throw new CpfRegisteredException("Cpf já cadastrado");
        }
    }

    @Override
    public void validateUpdate(Client client, Client updatedClient) throws CpfRegisteredException {
        if (!(client.getCpf().equals(updatedClient.getCpf())) &&
                (clientRepository.findByCpf(client.getCpf()) != null)) {
            throw new CpfRegisteredException("CPF já cadastrado");
        }
    }
}
