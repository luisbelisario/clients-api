package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.exception.ClientNotFoundException;
import com.reservei.clientsapi.exception.GenericException;
import com.reservei.clientsapi.exception.InactiveAccountException;
import com.reservei.clientsapi.repository.ClientRepository;
import com.reservei.clientsapi.service.clientvalidator.CpfValidator;
import com.reservei.clientsapi.service.clientvalidator.EmailValidator;
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

    public ClientDto findById(Long id) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return ClientDto.toDto(client);
    }

    public ClientDto updateById(Long id, ClientData data) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        Client updatedClient = Client.updateClient(client, data);
        validate(updatedClient);
        clientRepository.save(updatedClient);
        return ClientDto.toDto(updatedClient);
    }

    public MessageDto reactivateById(Long id) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        if(client.getDeletedAt() == null) {
            throw new GenericException("Cliente já está com a conta ativa");
        }
        client.setDeletedAt(null);
        clientRepository.save(client);

        return MessageDto.toDto("Cliente reativado com sucesso");
    }

    public MessageDto deleteById(Long id) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        if(client.getDeletedAt() != null) {
            throw new GenericException("Cliente já está com a conta inativa");
        }
        client.setDeletedAt(LocalDate.now());
        clientRepository.save(client);

        return MessageDto.toDto("Cliente excluído com sucesso");
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
