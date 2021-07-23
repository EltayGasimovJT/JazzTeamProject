package service;


import entity.Client;
import org.junit.jupiter.api.Test;
import service.impl.ClientServiceImpl;

import java.util.Arrays;

class ClientServiceTest {

    @Test
    void addClient() {
        ClientService clientService = new ClientServiceImpl();
        Client.Order order = Client.Order
                .builder()
                .currentLocation(null)
                .destinationPlace(null)
                .history(null)
                .id(1)
                .parcelParameters(null)
                .prise(null)
                .recipient(null)
                .route(null)
                .sender(null)
                .state(null)
                .build();

        Client client = Client
                .builder()
                .id(1)
                .name("Client")
                .surName("client")
                .orders(Arrays.asList(order))
                .passportId("152536")
                .phoneNumber("+375447567535")
                .build();

        clientService.addClient(client);
        clientService.showClients();
    }
}