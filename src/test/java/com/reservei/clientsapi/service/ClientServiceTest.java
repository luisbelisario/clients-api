package com.reservei.clientsapi.service;

import com.reservei.clientsapi.domain.dto.ClientDto;
import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.repository.ClientRepository;
import mocks.ClientDataMock;
import mocks.ClientDtoMock;
import mocks.ClientMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;


    @Test
    @DisplayName("Must register a client correctly")
    public void testeCreate_ValidCase() throws Exception {
        ClientData data = ClientDataMock.getClientDataMock();
        Client client = ClientMock.toClientMock(data);

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        ClientDto dtoSaved = clientService.create(data);

        Assertions.assertThat(dtoSaved).isNotNull();
    }

    @Test
    @DisplayName("Must register a client correctly with the named and email that was passed")
    public void testeCreate_ValidCase2() throws Exception {
        ClientData data = ClientDataMock.getClientDataMock();
        Client client = ClientMock.toClientMock(data);

        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        ClientDto dtoSaved = clientService.create(data);

        Assertions.assertThat(dtoSaved.getName()).isEqualTo(client.getName());
        Assertions.assertThat(dtoSaved.getEmail()).isEqualTo(client.getEmail());
    }
}