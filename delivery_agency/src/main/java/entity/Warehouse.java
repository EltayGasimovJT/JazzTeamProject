package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse extends AbstractBuilding {
    private List<OrderProcessingPoint> orderProcessingPoints;
    private List<Warehouse> connectedWarehouses;
}