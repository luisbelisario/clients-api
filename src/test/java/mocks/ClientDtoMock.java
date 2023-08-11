package mocks;

import com.reservei.clientsapi.domain.dto.ClientDto;

public class ClientDtoMock {

    public static ClientDto getClientDtoMock() {
        return new ClientDto(1L, "12345", "Cliente Teste",
                "teste@gmail.com", "00000000", "869954674894");
    }
}
