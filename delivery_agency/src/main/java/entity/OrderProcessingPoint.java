package entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class OrderProcessingPoint extends AbstractBuilding {
    private Warehouse warehouse;
}
