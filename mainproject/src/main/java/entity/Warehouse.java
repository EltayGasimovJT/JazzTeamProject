package entity;

import lombok.*;

import java.util.List;



@Getter @Setter
@NoArgsConstructor
public class Warehouse extends AbstractBuilding {
    @Singular
    private List<OrderProcessingPoint> orderProcessingPoints;
    @Singular
    private List<Warehouse> connectedWarehouses;

    public Warehouse(long id, String location, List<OrderProcessingPoint> orderProcessingPoints, List<Warehouse> connectedWarehouses) {
        super(id, location);
        this.orderProcessingPoints = orderProcessingPoints;
        this.connectedWarehouses = connectedWarehouses;
    }

    public Warehouse(long id, List<Order> expectedOrders, List<Order> dispatchedOrders, String location, List<OrderProcessingPoint> orderProcessingPoints, List<Warehouse> connectedWarehouses) {
        super(id, expectedOrders, dispatchedOrders, location);
        this.orderProcessingPoints = orderProcessingPoints;
        this.connectedWarehouses = connectedWarehouses;
    }

    public void addOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPoints.add(orderProcessingPoint);
    }

    public void addAllOrderProcessingPoints(List<OrderProcessingPoint> orderProcessingPoints) {
        this.orderProcessingPoints.addAll(orderProcessingPoints);
    }

    public void removeOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPoints.remove(orderProcessingPoint);
    }

    public void removeAllOrderProcessingPoints(List<OrderProcessingPoint> orderProcessingPoints) {
        this.orderProcessingPoints.removeAll(orderProcessingPoints);
    }

    public void addRouteBetweenWarehouses(Warehouse routeBetweenWarehouses) {
        connectedWarehouses.add(routeBetweenWarehouses);
    }

    public void addAllRoutesBetweenWarehouses(List<Warehouse> routeBetweenWarehouses) {
        connectedWarehouses.addAll(routeBetweenWarehouses);
    }

    public void removeRouteBetweenWarehouses(Warehouse routeBetweenWarehouses) {
        connectedWarehouses.remove(routeBetweenWarehouses);
    }

    public void removeAllRoutesBetweenWarehouses(List<Warehouse> routeBetweenWarehouses) {
        connectedWarehouses.removeAll(routeBetweenWarehouses);
    }
}
