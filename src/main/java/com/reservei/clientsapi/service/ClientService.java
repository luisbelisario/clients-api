package com.reservei.clientsapi.service;

import com.reservei.clientsapi.config.feign.UserClient;
import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.dto.MessageDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.domain.record.TokenData;
import com.reservei.clientsapi.domain.record.UserData;
import com.reservei.clientsapi.exception.ApiCommunicationException;
import com.reservei.clientsapi.exception.ClientNotFoundException;
import com.reservei.clientsapi.exception.GenericException;
import com.reservei.clientsapi.exception.InactiveAccountException;
import com.reservei.clientsapi.exception.InvalidTokenException;
import com.reservei.clientsapi.repository.ClientRepository;
import com.reservei.clientsapi.service.clientvalidator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserClient userClient;

    @Autowired
    List<ClientValidator> validators;


    public ClientDto create(ClientData data) throws Exception {
        Client client = Client.toClient(data);
        validateCreation(client);
        String password = generatePassword(data.password());
        UserData dataUser = new UserData(client.getPublicId(),
                client.getEmail(), password, client.getRole());
        try {
            userClient.createUser(dataUser);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }

        return ClientDto.toDto(clientRepository.save(client));
    }

    public ClientDto findById(Long id, String token) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        validateToken(token, client);
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return ClientDto.toDto(client);
    }

    public ClientDto findByPublicId(String publicId, String token) throws Exception {
        Client client = clientRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o public id informado"));
        validateToken(token, client);
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return ClientDto.toDto(client);
    }

    public ClientDto findByPublicId(String publicId) throws Exception {
        Client client = clientRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o public id informado"));
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        return ClientDto.toDto(client);
    }

    public ClientDto updateByPublicId(String publicId, ClientData data, String token) throws Exception {
        Client client = clientRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o public id informado"));
        validateToken(token, client);
        if (client.getDeletedAt() != null) {
            throw new InactiveAccountException("Cliente com a conta inativa");
        }
        Client updatedClient = Client.updateClient(client, data);
        validateUpdate(updatedClient, client);
        String password = generatePassword(data.password());
        try {
            UserData dataUser = new UserData(updatedClient.getPublicId(),
                    updatedClient.getEmail(), password, updatedClient.getRole());
            userClient.updateUser(updatedClient.getPublicId(), dataUser, token);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        clientRepository.save(updatedClient);
        return ClientDto.toDto(updatedClient);
    }

    public MessageDto reactivateById(String publicId, String token) throws Exception {
        Client client = clientRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        validateToken(token, client);
        if(client.getDeletedAt() == null) {
            throw new GenericException("Cliente já está com a conta ativa");
        }
        client.setDeletedAt(null);
        try{
            userClient.reactivateUser(client.getPublicId(), token);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        clientRepository.save(client);


        return MessageDto.toDto("Cliente reativado com sucesso");
    }

    public MessageDto deleteById(String publicId, String token) throws Exception {
        Client client = clientRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ClientNotFoundException("Cliente não encontrado para o id informado"));
        validateToken(token, client);
        if(client.getDeletedAt() != null) {
            throw new GenericException("Cliente já está com a conta inativa");
        }
        client.setDeletedAt(LocalDate.now());
        try {
            userClient.deleteUser(client.getPublicId(), token);
        } catch (Exception ex) {
            throw new ApiCommunicationException("Falha na comunicação com o serviço de usuários");
        }
        clientRepository.save(client);


        return MessageDto.toDto("Cliente excluído com sucesso");
    }

    private void validateCreation(Client client) throws Exception {
        for(ClientValidator validator : validators) {
            validator.validateCreation(client);
        }
    }

    private void validateUpdate(Client updatedClient, Client client) throws Exception {
        for(ClientValidator validator : validators) {
            validator.validateUpdate(client, updatedClient);
        }
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }

    public Optional<Client> findByPublicIdValidation(String publicId) {
        return clientRepository.findByPublicId(publicId);
    }

    private static String generatePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public String getUsername(TokenData data) {
        return userClient.validateToken(data);
    }

    private void validateToken(String token, Client client) throws InvalidTokenException {
        if( (userClient.validateToken(TokenData.toData(token)).equals("invalid")) ||
                !(userClient.validateToken(TokenData.toData(token)).equals(client.getEmail()))) {
            throw new InvalidTokenException("Token inválido ou expirado");
        }
    }
}
