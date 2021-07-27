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
    private final WareHouseRepository wareHouseRepository = new WareHouseRepositoryImpl();
    private final String ROLE_ADMIN = "Admin";

    @Override
    public Client.Order UpdateOrderCurrentLocation(long id, AbstractLocation newLocation) {
        Client.Order order = orderRepository.findOne(id);
        order.setCurrentLocation(newLocation);
        return orderRepository.update(order);
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) {
        Client.Order order = orderRepository.findOne(id);
        order.setHistory(newHistory);
        orderRepository.update(order);
    }

    @Override
    public Client.Order create(Client.Order order) {
        OrderState orderState = updateState("ReadyToSend");
        BigDecimal price = calculatePrice(order);
        order.setPrise(price);
        order.setState(orderState);
        return orderRepository.save(order);
    }

    @Override
    public Client.Order findById(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Client.Order findByRecipient(Client recipient) {
        return orderRepository.findByRecipient(recipient);
    }

    @Override
    public Client.Order findBySender(Client sender) {
        return orderRepository.findBySender(sender);
    }

    @Override
    public AbstractLocation getCurrentOrderLocation(long id) {
        Client.Order order = orderRepository.findOne(id);
        return order.getCurrentLocation();
    }

    @Override
    public void send(List<Client.Order> orders, Voyage voyage) {
        for (Client.Order order : orders) {
            order.setCurrentLocation(voyage);
        }
        orderRepository.saveSentOrders(orders);
    }

    @Override
    public void accept(List<Client.Order> orders) {
        List<Client.Order> orders1 = orderRepository.acceptOrders(orders);
        for (Client.Order order : orders1) {
            log.info(order.toString());
        }
    }

    @Override
    public String getState(long id) {
        Client.Order order = orderRepository.findOne(id);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<Client.Order> expectedOrders, List<Client.Order> acceptedOrders) throws IllegalArgumentException {
        for (Client.Order expectedOrder : expectedOrders) {
            if (!expectedOrder.equals(acceptedOrders)) {
                throw new IllegalArgumentException("Expected List is not equal to actual!!!");
            }
        }
    }

    @Override
    public boolean isFinalWarehouse(Client.Order order) {
        return order.getDestinationPlace().equals(order.getCurrentLocation());
    }

    private BigDecimal calculatePrice(Client.Order order) throws IllegalArgumentException {
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
            newRolesAllowedPutToState.add("PickUp Worker");

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add("Warehouse Worker");
        }
        if (state.equals("On the way to the warehouse")) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add("PickUp Worker");

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add("Warehouse Worker");
        }
        orderState.setRolesAllowedPutToState(newRolesAllowedPutToState);
        orderState.setRolesAllowedWithdrawFromState(newRolesAllowedToWithdrawFromState);
        return orderState;
    }
}
