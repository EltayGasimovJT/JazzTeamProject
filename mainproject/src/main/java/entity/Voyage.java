package entity;

import java.util.List;

public class Voyage extends AbstractLocation {
    private String departurePoint;
    private String destinationPoint;
    private String sendingTime;

    public Voyage(long id, String departurePoint, String destinationPoint, String sendingTime) {
        super(id);
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.sendingTime = sendingTime;
    }

    public Voyage(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String departurePoint, String destinationPoint, String sendingTime) {
        super(id, expectedOrders, dispatchedOrders);
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.sendingTime = sendingTime;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

}
