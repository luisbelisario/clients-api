package mocks;

import com.reservei.clientsapi.domain.record.ClientData;

public class ClientDataMock {

    public static ClientData getClientDataMock() {
        return new ClientData("Cliente Teste", "teste@gmail.com", "00000000", "869954674894");
    }
}
