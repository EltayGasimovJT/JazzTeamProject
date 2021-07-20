package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private List<Order> orders;
    private String name;
    private String surName;
    private String passportId;
    private String phoneNumber;

    public Client() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void removeOrder(Order order){
        orders.remove(order);
    }

    public void clear(){
        orders.clear();
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

    public class Order {
        private int orderId;
        private String parcelWeight;
        private String parcelSize;
        private String parcelDescription;
        private String orderState;
        private String departureDate;
        private String recipientAddress;

        private Client client;

        public Client getClient() {
            return client;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        private Order order;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getParcelWeight() {
            return parcelWeight;
        }

        public void setParcelWeight(String parcelWeight) {
            this.parcelWeight = parcelWeight;
        }

        public String getParcelSize() {
            return parcelSize;
        }

        public void setParcelSize(String parcelSize) {
            this.parcelSize = parcelSize;
        }

        public String getParcelDescription() {
            return parcelDescription;
        }

        public void setParcelDescription(String parcelDescription) {
            this.parcelDescription = parcelDescription;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
            this.orderState = orderState;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getRecipientAddress() {
            return recipientAddress;
        }

        public void setRecipientAddress(String recipientAddress) {
            this.recipientAddress = recipientAddress;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Order order1 = (Order) o;
            return orderId == order1.orderId &&
                    Objects.equals(parcelWeight, order1.parcelWeight) &&
                    Objects.equals(parcelSize, order1.parcelSize) &&
                    Objects.equals(parcelDescription, order1.parcelDescription) &&
                    Objects.equals(orderState, order1.orderState) &&
                    Objects.equals(departureDate, order1.departureDate) &&
                    Objects.equals(recipientAddress, order1.recipientAddress) &&
                    Objects.equals(client, order1.client) &&
                    Objects.equals(order, order1.order);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId,
                    parcelWeight,
                    parcelSize,
                    parcelDescription,
                    orderState,
                    departureDate,
                    recipientAddress, client, order);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(orders, client.orders) &&
                Objects.equals(name, client.name) &&
                Objects.equals(surName, client.surName) &&
                Objects.equals(passportId, client.passportId) &&
                Objects.equals(phoneNumber, client.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, name, surName, passportId, phoneNumber);
    }
}
