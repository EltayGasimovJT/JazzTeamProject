package entity;

import java.math.BigDecimal;
import java.util.List;

public class Client {
    private long id;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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
                List<AbstractLocation> route
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
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public OrderState getState() {
            return state;
        }

        public void setState(OrderState state) {
            this.state = state;
        }

        public ParcelParameters getParcelParameters() {
            return parcelParameters;
        }

        public void setParcelParameters(ParcelParameters parcelParameters) {
            this.parcelParameters = parcelParameters;
        }

        public Client getSender() {
            return sender;
        }

        public void setSender(Client sender) {
            this.sender = sender;
        }

        public Client getRecipient() {
            return recipient;
        }

        public void setRecipient(Client recipient) {
            this.recipient = recipient;
        }

        public BigDecimal getPrise() {
            return prise;
        }

        public void setPrise(BigDecimal prise) {
            this.prise = prise;
        }

        public AbstractBuilding getDestinationPlace() {
            return destinationPlace;
        }

        public void setDestinationPlace(AbstractBuilding destinationPlace) {
            this.destinationPlace = destinationPlace;
        }

        public AbstractLocation getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(AbstractLocation currentLocation) {
            this.currentLocation = currentLocation;
        }

        public OrderHistory getHistory() {
            return history;
        }

        public void setHistory(OrderHistory history) {
            this.history = history;
        }
    }
}
