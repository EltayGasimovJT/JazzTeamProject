package entity;

import java.util.List;

public class Warehouse extends AbstractBuilding {
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<RouteBetweenWarehouses> connectedWarehouses;

    public Warehouse(long id, String location, List<OrderProcessingPoint> orderProcessingPoints, List<RouteBetweenWarehouses> connectedWarehouses) {
        super(id, location);
        this.orderProcessingPoints = orderProcessingPoints;
        this.connectedWarehouses = connectedWarehouses;
    }

    public Warehouse(long id, List<Client.Order> expectedOrders, List<Client.Order> dispatchedOrders, String location, List<OrderProcessingPoint> orderProcessingPoints, List<RouteBetweenWarehouses> connectedWarehouses) {
        super(id, expectedOrders, dispatchedOrders, location);
        this.orderProcessingPoints = orderProcessingPoints;
        this.connectedWarehouses = connectedWarehouses;
    }

    public void addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPoints.add(orderProcessingPoint);
    }

    public void addAllOrderProcessingPoints(List<OrderProcessingPoint> orderProcessingPoints) {
        orderProcessingPoints.addAll(orderProcessingPoints);
    }

    public void removeOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPoints.remove(orderProcessingPoint);
    }

    public void removeAllOrderProcessingPoints(List<OrderProcessingPoint> orderProcessingPoints) {
        orderProcessingPoints.removeAll(orderProcessingPoints);
    }

    public void addRouteBetweenWarehouses(RouteBetweenWarehouses routeBetweenWarehouses) {
        connectedWarehouses.add(routeBetweenWarehouses);
    }

    public void addAllRoutesBetweenWarehouses(List<RouteBetweenWarehouses> routeBetweenWarehouses) {
        connectedWarehouses.addAll(routeBetweenWarehouses);
    }

    public void removeRouteBetweenWarehouses(RouteBetweenWarehouses routeBetweenWarehouses) {
        connectedWarehouses.remove(routeBetweenWarehouses);
    }

    public void removeAllRoutesBetweenWarehouses(List<RouteBetweenWarehouses> routeBetweenWarehouses) {
        connectedWarehouses.removeAll(routeBetweenWarehouses);
    }
}
