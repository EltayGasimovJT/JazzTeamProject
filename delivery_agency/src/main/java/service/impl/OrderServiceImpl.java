package service.impl;

import dto.CoefficientForPriceCalculationDto;
import dto.OrderDto;
import entity.*;
import lombok.extern.slf4j.Slf4j;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.CoefficientForPriceCalculationService;
import service.OrderService;
import validator.OrderValidator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.OrderStateChangeType;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";


    @Override
    public OrderDto updateOrderCurrentLocation(long id, AbstractLocation newLocation) throws SQLException {
        Order order = orderRepository.findOne(id);
        order.setCurrentLocation(newLocation);
        return fromOrderToDto(orderRepository.update(order));
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) throws SQLException {
        Order order = orderRepository.findOne(id);
        order.setHistory(newHistory);
        orderRepository.update(order);
    }

    @Override
    public OrderDto create(OrderDto order) throws SQLException {
        OrderValidator.validateOrder(order);
        BigDecimal price = calculatePrice(order);
        OrderState orderState = updateState(OrderStates.READY_TO_SEND.toString());
        order.setPrice(price);
        order.setState(orderState);
        OrderHistory orderHistory = OrderHistory.builder()
                .order(fromDtoToOrder(order))
                .histories(Collections.singletonList(OrderHistory.builder().build()))
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .changingTime(order.getSendingTime())
                .build();
        order.setHistory(orderHistory);
        return fromOrderToDto(orderRepository.save(fromDtoToOrder(order)));
    }

    @Override
    public OrderDto findById(long id) throws SQLException {
        return fromOrderToDto(orderRepository.findOne(id));
    }

    @Override
    public OrderDto findByRecipient(Client recipient) {
        return fromOrderToDto(orderRepository.findByRecipient(recipient));
    }

    @Override
    public OrderDto findBySender(Client sender) {
        return fromOrderToDto(orderRepository.findBySender(sender));
    }

    @Override
    public AbstractLocation getCurrentOrderLocation(long id) throws SQLException {
        Order order = orderRepository.findOne(id);
        return order.getCurrentLocation();
    }

    @Override
    public void send(List<OrderDto> orders, Voyage voyage) throws SQLException {
        for (OrderDto order : orders) {
            order.setCurrentLocation(voyage);
            OrderState orderState;
            if (isFinalWarehouse(order)) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString());
            }
            order.setState(orderState);
        }
        List<Order> ordersToSend = new ArrayList<>();
        for (OrderDto order : orders) {
            ordersToSend.add(fromDtoToOrder(order));
        }

        orderRepository.saveSentOrders(ordersToSend);
    }

    @Override
    public List<OrderDto> accept(List<OrderDto> orders) throws SQLException {
        List<Order> ordersToAccept = new ArrayList<>();
        for (OrderDto order : orders) {
            ordersToAccept.add(fromDtoToOrder(order));
        }

        List<Order> acceptedOrders = orderRepository.acceptOrders(ordersToAccept);

        for (Order order : acceptedOrders) {
            log.info(order.toString());
            OrderState orderState;
            if (isFinalWarehouse(fromOrderToDto(order))) {
                orderState = updateState(OrderStates.ON_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(order.getId()).equals(order.getDestinationPlace())) {
                orderState = updateState(OrderStates.ORDER_COMPLETED.toString());
            }
            order.setState(orderState);
        }

        List<OrderDto> acceptedOrdersDto = new ArrayList<>();

        for (Order acceptedOrder : acceptedOrders) {
            acceptedOrdersDto.add(fromOrderToDto(acceptedOrder));
        }

        return acceptedOrdersDto;
    }

    @Override
    public String getState(long id) throws SQLException {
        Order order = orderRepository.findOne(id);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException {
        for (int i = 0; i < expectedOrders.size(); i++) {
            if (!expectedOrders.get(i).equals(acceptedOrders.get(i))) {
                throw new IllegalArgumentException("Expected List is not equal to actual!!!");
            }
        }
    }

    @Override
    public boolean isFinalWarehouse(OrderDto order) {
        return order.getDestinationPlace().equals(order.getCurrentLocation());
    }

    @Override
    public List<OrderDto> findAll() throws SQLException {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(fromOrderToDto(order));
        }
        return orderDtos;
    }

    @Override
    public BigDecimal calculatePrice(OrderDto order) throws IllegalArgumentException, SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = getCoefficient(order.getDestinationPlace());

        return priceCalculationRuleService.calculatePrice(fromDtoToOrder(order), coefficientForPriceCalculation);
    }

    @Override
    public List<List<OrderDto>> getOrdersOnTheWay() {
        List<List<Order>> allSentOrders = orderRepository.getSentOrders();
        List<List<OrderDto>> allSentOrderDtos = new ArrayList<>();

        for (List<Order> sentOrders : allSentOrders) {
            List<OrderDto> sentOrderDtos = new ArrayList<>();
            for (Order sentOrder : sentOrders) {
                sentOrderDtos.add(fromOrderToDto(sentOrder));
            }
            allSentOrderDtos.add(sentOrderDtos);
        }

        return allSentOrderDtos;
    }

    @Override
    public OrderDto update(OrderDto order) throws SQLException {
        OrderValidator.validateOrder(order);
        Order update = orderRepository.update(fromDtoToOrder(order));
        return fromOrderToDto(update);
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(id);
    }

    private OrderState updateState(String state) {
        List<String> newRolesAllowedPutToState = new ArrayList<>();
        List<String> newRolesAllowedToWithdrawFromState = new ArrayList<>();
        OrderState orderState = OrderState.builder()
                .state(state).build();
        if (state.equals(OrderStates.READY_TO_SEND.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_PICKUP_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_PICKUP_WORKER);
        }
        if (state.equals(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_PICKUP_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals(OrderStates.ON_THE_WAREHOUSE.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals(OrderStates.ON_THE_FINAL_WAREHOUSE.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_WAREHOUSE_WORKER);
        }
        if (state.equals(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_WAREHOUSE_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_PICKUP_WORKER);
        }
        if (state.equals(OrderStates.ORDER_COMPLETED.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedPutToState.add(ROLE_PICKUP_WORKER);

            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_PICKUP_WORKER);
        }
        if (state.equals(OrderStates.ORDER_LOCKED.toString())) {
            newRolesAllowedPutToState.add(ROLE_ADMIN);
            newRolesAllowedToWithdrawFromState.add(ROLE_ADMIN);
        }
        orderState.setRolesAllowedPutToState(newRolesAllowedPutToState);
        orderState.setRolesAllowedWithdrawFromState(newRolesAllowedToWithdrawFromState);
        return orderState;
    }

    private CoefficientForPriceCalculationDto getCoefficient(AbstractBuilding abstractBuilding) throws SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation;

        String russia = "Russia";

        CoefficientForPriceCalculationDto firstCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country(russia)
                .parcelSizeLimit(50)
                .build();

        String poland = "Poland";

        CoefficientForPriceCalculationDto secondCoefficient = CoefficientForPriceCalculationDto
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country(poland)
                .parcelSizeLimit(40)
                .build();

        priceCalculationRuleService.addPriceCalculationRule(firstCoefficient);
        priceCalculationRuleService.addPriceCalculationRule(secondCoefficient);

        if (abstractBuilding.getLocation().equals(russia)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(russia);
        } else if (abstractBuilding.getLocation().equals(poland)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(poland);
        } else {
            throw new IllegalArgumentException("This country is not supported yet!!!" + abstractBuilding.getLocation());
        }
        return coefficientForPriceCalculation;
    }

    private OrderDto fromOrderToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .currentLocation(order.getCurrentLocation())
                .destinationPlace(order.getDestinationPlace())
                .history(order.getHistory())
                .parcelParameters(order.getParcelParameters())
                .price(order.getPrice())
                .recipient(order.getRecipient())
                .route(order.getRoute())
                .sender(order.getSender())
                .sendingTime(order.getSendingTime())
                .state(order.getState())
                .build();
    }

    private Order fromDtoToOrder(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .currentLocation(orderDto.getCurrentLocation())
                .destinationPlace(orderDto.getDestinationPlace())
                .history(orderDto.getHistory())
                .parcelParameters(orderDto.getParcelParameters())
                .price(orderDto.getPrice())
                .recipient(orderDto.getRecipient())
                .route(orderDto.getRoute())
                .sender(orderDto.getSender())
                .sendingTime(orderDto.getSendingTime())
                .state(orderDto.getState())
                .build();
    }
}