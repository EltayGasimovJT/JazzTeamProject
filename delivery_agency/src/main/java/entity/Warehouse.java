package entity;

import lombok.Data;

import java.util.List;



@Data
public class Warehouse extends AbstractBuilding {
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;

}
