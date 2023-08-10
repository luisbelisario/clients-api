package mocks;

import com.reservei.clientsapi.domain.record.ClientData;

public class ClientDataMock {

    public static ClientData getClientDataMock() {
        return new ClientData("Cliente Teste", "teste@gmail.com", "12345", "00000000000", "869954674894");
    }
}
