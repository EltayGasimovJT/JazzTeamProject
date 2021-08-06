package servicetest.impl;

import entity.*;
import lombok.extern.slf4j.Slf4j;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import servicetest.CoefficientForPriceCalculationService;
import servicetest.OrderService;
import validator.OrderValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";


    @Override
    public Order updateOrderCurrentLocation(long id, AbstractLocation newLocation) {
        Order order = orderRepository.findOne(id);
        order.setCurrentLocation(newLocation);
        return orderRepository.update(order);
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) {
        Order order = orderRepository.findOne(id);
        order.setHistory(Arrays.asList(newHistory));
        orderRepository.update(order);
    }

    @Override
    public Order create(Order order) {
        OrderValidator.validateOrder(order);
        OrderState orderState = updateState("Ready To Send");
        BigDecimal price = calculatePrice(order);
        order.setPrice(price);
        order.setState(orderState);
        OrderHistory orderHistory = OrderHistory.builder()
                .order(order)
                .allStates(Collections.singletonList(OrderHistory.builder().build()))
                .changedTypeEnum(ChangedTypeEnum.READY_TO_SEND)
                .changingTime(order.getSendingTime())
                .build();
        order.setHistory(Collections.singletonList(orderHistory));
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
            OrderState orderState;
            if (isFinalWarehouse(order)) {
                orderState = updateState("On the way to the final warehouse");
            } else {
                orderState = updateState("On the way to the warehouse");
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = updateState("On the way to the pick up/reception");
            }
            order.setState(orderState);
        }
        orderRepository.saveSentOrders(orders);
    }

    @Override
    public List<Order> accept(List<Order> orders) {
        List<Order> acceptedOrders = orderRepository.acceptOrders(orders);
        for (Order order : acceptedOrders) {
            log.info(order.toString());
            OrderState orderState;
            if (isFinalWarehouse(order)) {
                orderState = updateState("On final warehouse");
            } else {
                orderState = updateState("On the warehouse");
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = updateState("Order completed");
            }
            order.setState(orderState);
        }
        return acceptedOrders;
    }

    @Override
    public String getState(long id) {
        Order order = orderRepository.findOne(id);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<Order> expectedOrders, List<Order> acceptedOrders) throws IllegalArgumentException {
        for (int i = 0; i < expectedOrders.size(); i++) {
            if (!expectedOrders.get(i).equals(acceptedOrders.get(i))) {
                throw new IllegalArgumentException("Expected List is not equal to actual!!!");
            }
        }
    }

    @Override
    public boolean isFinalWarehouse(Order order) {
        return order.getDestinationPlace().equals(order.getCurrentLocation());
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public BigDecimal calculatePrice(Order order) throws IllegalArgumentException {
        CoefficientForPriceCalculation coefficientForPriceCalculation = getCoefficient(order.getDestinationPlace());

        return priceCalculationRuleService.calculatePrice(order, coefficientForPriceCalculation);
    }

    @Override
    public List<List<Order>> getOrdersOnTheWay() {
        return orderRepository.getSentOrders();
    }

    @Override
    public Order update(Order order) {
        OrderValidator.validateOrder(order);
        return orderRepository.update(order);
    }

    @Override
    public void delete(Order order) {
        OrderValidator.validateOrder(order);
        orderRepository.delete(order);
    }

    private OrderState updateState(String state) {
        List<String> newRolesAllowedPutToState = new ArrayList<>();
        List<String> newRolesAllowedToWithdrawFromState = new ArrayList<>();
        OrderState orderState = OrderState.builder()
                .state(state).build();
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

    private CoefficientForPriceCalculation getCoefficient(AbstractBuilding abstractBuilding) {
        CoefficientForPriceCalculation coefficientForPriceCalculation;

        CoefficientForPriceCalculation firstCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country("Russia")
                .parcelSizeLimit(50)
                .build();

        CoefficientForPriceCalculation secondCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country("Poland")
                .parcelSizeLimit(40)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(firstCoefficient);
        priceCalculationRuleService.addPriceCalculationRule(secondCoefficient);

        if (abstractBuilding.getLocation().equals("Russia")) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry("Russia");
        } else if (abstractBuilding.getLocation().equals("Poland")) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry("Poland");
        } else {
            throw new IllegalArgumentException("This country is not supported yet!!!" + abstractBuilding.getLocation());
        }
        return coefficientForPriceCalculation;
    }
}
