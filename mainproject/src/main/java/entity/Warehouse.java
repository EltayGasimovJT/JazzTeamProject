package entity;

import lombok.*;

import java.util.List;



@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Warehouse extends AbstractBuilding {
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;

}
