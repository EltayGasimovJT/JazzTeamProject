package entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode @ToString
public class Client {
    private long id;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;
    @Singular
    private List<Order> orders;

    public Client(long id, String name, String surName, String passportId, String phoneNumber, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.passportId = passportId;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void addOrder(Client.Order order) {
        orders.add(order);
    }

    public void addAllOrders(List<Client.Order> orders) {
        orders.addAll(orders);
    }

    public void removeOrder(Client.Order order) {
        orders.remove(order);
    }

    public void removeAllOrders(List<Client.Order> orders) {
        orders.removeAll(orders);
    }

    @Getter @Setter @NoArgsConstructor
    @ToString @EqualsAndHashCode
    public class Order {
        private int id;
        private OrderState state;
        private ParcelParameters parcelParameters;
        private Client sender;
        private Client recipient;
        private BigDecimal prise;
        private AbstractBuilding destinationPlace;
        private AbstractLocation currentLocation;
        private OrderHistory history;
        private long stillageSpaceId;


        private List<AbstractLocation> route;

        public Order(
                int id,
                OrderState state,
                ParcelParameters parcelParameters,
                Client sender,
                Client recipient,
                BigDecimal prise,
                AbstractBuilding destinationPlace,
                AbstractLocation currentLocation,
                OrderHistory history,
                List<AbstractLocation> route,
                long stillageSpaceId
        ) {
            this.id = id;
            this.state = state;
            this.parcelParameters = parcelParameters;
            this.sender = sender;
            this.recipient = recipient;
            this.prise = prise;
            this.destinationPlace = destinationPlace;
            this.currentLocation = currentLocation;
            this.history = history;
            this.route = route;
            this.stillageSpaceId = stillageSpaceId;
        }

    }
}
