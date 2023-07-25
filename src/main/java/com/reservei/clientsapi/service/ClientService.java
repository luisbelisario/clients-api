package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    public ClientDto create(ClientData data) {
        Client client = Client.toClient(data);
        Client clientSaved = clientRepository.save(client);
        return ClientDto.toDto(clientSaved);
    }
}
