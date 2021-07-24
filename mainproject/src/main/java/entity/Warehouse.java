package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;


@Builder
@Getter @Setter
public class Warehouse extends AbstractBuilding {
    @Singular
    private List<OrderProcessingPoint> orderProcessingPoints;
    @Singular
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
        this.orderProcessingPoints.addAll(orderProcessingPoints);
    }

    public void removeOrderProcessingPoint(OrderProcessingPoint orderProcessingPoint) {
        orderProcessingPoints.remove(orderProcessingPoint);
    }

    public void removeAllOrderProcessingPoints(List<OrderProcessingPoint> orderProcessingPoints) {
        this.orderProcessingPoints.removeAll(orderProcessingPoints);
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
