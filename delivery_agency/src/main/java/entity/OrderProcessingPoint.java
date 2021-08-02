package entity;

import lombok.Data;


@Data
public class OrderProcessingPoint extends AbstractBuilding {
    private Warehouse warehouse;
}
