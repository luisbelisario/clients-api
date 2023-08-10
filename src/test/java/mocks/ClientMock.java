package mocks;

import com.reservei.clientsapi.domain.model.Client;
import com.reservei.clientsapi.domain.record.ClientData;
import com.reservei.clientsapi.util.StringUtils;

import java.time.LocalDate;

public class ClientMock {

    public static Client toClientMock(ClientData data) {
        Client client = new Client();
        client.setName(data.name());
        client.setEmail(data.email());
        client.setCpf(StringUtils.removeDotsAndDashes(data.cpf()));
        client.setPhone(StringUtils.removeDotsAndDashes(data.phone()));
        client.setRole("ROLE_USER");
        client.setCreatedAt(LocalDate.now());

        return client;
    }

}
