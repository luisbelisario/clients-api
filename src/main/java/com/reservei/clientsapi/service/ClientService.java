package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.exception.EmailCadastradoException;
import com.reservei.clientsapi.repository.ClientRepository;
import com.reservei.clientsapi.service.validator.ClientValidatorImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientValidatorImpl clientValidator;


    public ClientDto create(ClientData data) throws EmailCadastradoException {
        Client client = Client.toClient(data);
        if (clientValidator.emailExistsOnDatabase(client.getEmail())) {
            throw new EmailCadastradoException("Email já cadastrado");
        }
        Client clientSaved = clientRepository.save(client);
        return ClientDto.toDto(clientSaved);
    }

    public ClientDto findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (client.getDeletedAt() != null) {
            throw new RuntimeException("Cliente com a conta inativa");
        }
        return ClientDto.toDto(client);
    }

    public ClientDto updateById(Long id, ClientData data) {
        Client client = clientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (client.getDeletedAt() != null) {
            throw new RuntimeException("Cliente com a conta inativa");
        }
        Client updatedClient = Client.updateClient(client, data);
        clientRepository.save(updatedClient);
        return ClientDto.toDto(updatedClient);
    }

    public MessageDto deleteById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        client.setDeletedAt(LocalDate.now());
        clientRepository.save(client);

        return MessageDto.toDto("Cliente excluído com sucesso");
    }

    public MessageDto reactivateById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        client.setDeletedAt(null);
        clientRepository.save(client);

        return MessageDto.toDto("Cliente reativado com sucesso");
    }
}
