package org.jazzteam.eltay.gasimov.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractLocation {
    private Long id;
    private List<Order> expectedOrders;
    private List<Order> dispatchedOrders;
}