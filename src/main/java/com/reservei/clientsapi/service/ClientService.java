package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.repository.ClientRepository;
import com.reservei.clientsapi.service.clientvalidator.CpfValidator;
import com.reservei.clientsapi.service.clientvalidator.EmailValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    private final CpfValidator cpfValidator = new CpfValidator(this);
    private final EmailValidator emailValidator = new EmailValidator(this);


    public ClientDto create(ClientData data) throws Exception {
        Client client = Client.toClient(data);
        validate(client);
        return ClientDto.toDto(clientRepository.save(client));
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

    private void validate(Client client) throws Exception {
        emailValidator.validate(client);
        cpfValidator.validate(client);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }

}
