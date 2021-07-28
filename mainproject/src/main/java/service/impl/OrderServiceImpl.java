package service.impl;

import entity.*;
import lombok.extern.slf4j.Slf4j;
import repository.OrderRepository;
import repository.WareHouseRepository;
import repository.impl.OrderRepositoryImpl;
import repository.impl.WareHouseRepositoryImpl;
import service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";

    @Override
    public Order UpdateOrderCurrentLocation(long id, AbstractLocation newLocation) {
        Order order = orderRepository.findOne(id);
        order.setCurrentLocation(newLocation);
        return orderRepository.update(order);
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) {
        Order order = orderRepository.findOne(id);
        order.setHistory(newHistory);
        orderRepository.update(order);
    }

    @Override
    public Order create(Order order) {
        OrderState orderState = updateState("ReadyToSend");
        BigDecimal price = calculatePrice(order);
        order.setPrise(price);
        order.setState(orderState);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order findByRecipient(Client recipient) {
        return orderRepository.findByRecipient(recipient);
    }

    @Override
    public Order findBySender(Client sender) {
        return orderRepository.findBySender(sender);
    }

    @Override
    public AbstractLocation getCurrentOrderLocation(long id) {
        Order order = orderRepository.findOne(id);
        return order.getCurrentLocation();
    }

    @Override
    public void send(List<Order> orders, Voyage voyage) {
        for (Order order : orders) {
            order.setCurrentLocation(voyage);
        }
        orderRepository.saveSentOrders(orders);
    }

    @Override
    public void accept(List<Order> orders) {
        List<Order> orders1 = orderRepository.acceptOrders(orders);
        for (Order order : orders1) {
            log.info(order.toString());
        }
    }

    @Override
    public String getState(long id) {
        Order order = orderRepository.findOne(id);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<Order> expectedOrders, List<Order> acceptedOrders) throws IllegalArgumentException {
        for (Order expectedOrder : expectedOrders) {
            if (!expectedOrder.equals(acceptedOrders)) {
                throw new IllegalArgumentException("Expected List is not equal to actual!!!");
            }
        }
    }

    @Override
    public boolean isFinalWarehouse(Order order) {
        return order.getDestinationPlace().equals(order.getCurrentLocation());
    }

    private BigDecimal calculatePrice(Order order) throws IllegalArgumentException {
        BigDecimal decimal = BigDecimal.valueOf(1);
        BigDecimal resultPrice;
        String location = order.getDestinationPlace().getLocation();
        switch (location) {
            case "Moskov": {
                resultPrice = decimal.multiply(new BigDecimal((20 * 10) / 5));
                return resultPrice;
            }
            case "Poland": {
                resultPrice = decimal.multiply(new BigDecimal((30 * 10) / 5));
                return resultPrice;
            }
            case "Ukraine": {
                resultPrice = decimal.multiply(new BigDecimal((40 * 10) / 5));
                return resultPrice;
            }
            default: {
                throw new IllegalArgumentException("Country is not supported Yet!!!");
            }
        }
    }

    private OrderState updateState(String state) {
        List<String> newRolesAllowedPutToState = new ArrayList<>();
        List<String> newRolesAllowedToWithdrawFromState = new ArrayList<>();
        OrderState orderState = new OrderState();
        orderState.setState(state);
        if (state.equals("Ready To Send")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add("PickUp Worker");

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add("Pickup Worker");
        }
        if (state.equals("On the way to the warehouse")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_PICKUP_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals("On the warehouse")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals("On the way to the final warehouse")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals("On the final warehouse")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals("On the way to the pick up/reception")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_PICKUP_WORKER);
        }
        if (state.equals("Order completed")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_PICKUP_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_PICKUP_WORKER);
        }
        if (state.equals("Order locked")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
        }
        orderState.setRolesAllowedPutToState(newRolesAllowedPutToState);
        orderState.setRolesAllowedWithdrawFromState(newRolesAllowedToWithdrawFromState);
        return orderState;
    }
}
