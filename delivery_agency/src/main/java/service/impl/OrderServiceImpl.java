package service.impl;

import dto.*;
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

import static util.ConvertUtil.*;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";


    @Override
    public OrderDto updateOrderCurrentLocation(long id, AbstractBuildingDto newLocation) {
        Order order = orderRepository.findOne(id);
        OrderDto orderDto = fromOrderToDto(order);
        orderDto.setCurrentLocation(newLocation);
        Order updatedOrder = orderRepository.update(fromDtoToOrder(orderDto));
        return fromOrderToDto(updatedOrder);
    }

    @Override
    public void updateOrderHistory(long id, OrderHistory newHistory) {
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
        order.setState(fromOrderStateToDto(orderState));
        OrderHistory orderHistory = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .changingTime(order.getSendingTime())
                .user(User.builder().build())
                .build();
        order.setHistory(fromOrderHistoryToDto(orderHistory));
        return fromOrderToDto(orderRepository.save(fromDtoToOrder(order)));
    }

    @Override
    public OrderDto findById(long id) {
        return fromOrderToDto(orderRepository.findOne(id));
    }

    @Override
    public OrderDto findByRecipient(ClientDto recipient) {
        return fromOrderToDto(orderRepository.findByRecipient(fromDtoToClient(recipient)));
    }

    @Override
    public OrderDto findBySender(ClientDto sender) {
        return fromOrderToDto(orderRepository.findBySender(fromDtoToClient(sender)));
    }

    @Override
    public AbstractBuildingDto getCurrentOrderLocation(long id) {
        OrderDto order = fromOrderToDto(orderRepository.findOne(id));
        return order.getCurrentLocation();
    }

    @Override
    public void send(List<OrderDto> orderDtos) {
        final List<Order> orders = fromDtosToOrders(orderDtos);
        for (Order order : orders) {
            OrderState orderState;
            if (isFinalWarehouse(fromOrderToDto(order))) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString());
                AbstractBuilding abstractBuilding = new Warehouse();
                abstractBuilding.setLocation("Delivering From + " + order.getCurrentLocation().getLocation() + " to " + order.getDestinationPlace().getWarehouse().getLocation());
                order.setCurrentLocation(abstractBuilding);
            } else {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString());
                AbstractBuilding abstractBuilding = new Warehouse();
                abstractBuilding.setLocation("Delivering From + "
                        + order.getCurrentLocation().getLocation()
                        + " to "
                        + order.getDestinationPlace().getWarehouse().getLocation());
                order.setCurrentLocation(abstractBuilding);
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPointDto) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString());
                AbstractBuilding abstractBuilding = new OrderProcessingPoint();
                abstractBuilding.setLocation("Delivering From + " + order.getCurrentLocation().getLocation() + " to " + order.getDestinationPlace().getLocation());
                order.setCurrentLocation(abstractBuilding);
            }
            order.setState(orderState);

        }
        List<Order> ordersToSend = new ArrayList<>();
        for (OrderDto order : orderDtos) {
            ordersToSend.add(fromDtoToOrder(order));
        }

        orderRepository.saveSentOrders(ordersToSend);
    }

    @Override
    public List<OrderDto> accept(List<OrderDto> orders) {
        List<Order> ordersToAccept = new ArrayList<>();
        for (OrderDto order : orders) {
            ordersToAccept.add(fromDtoToOrder(order));
        }

        List<Order> acceptedOrders = orderRepository.acceptOrders(ordersToAccept);

        List<OrderDto> orderDtos = fromOrdersToDtos(acceptedOrders);

        for (OrderDto orderDto : orderDtos) {
            log.info(orderDto.toString());
            OrderStateDto orderStateDto;
            if (isFinalWarehouse(orderDto)) {
                orderStateDto = fromOrderStateToDto(updateState(OrderStates.ON_THE_FINAL_WAREHOUSE.toString()));
            } else {
                orderStateDto = fromOrderStateToDto(updateState(OrderStates.ON_THE_WAREHOUSE.toString()));
            }
            if (getCurrentOrderLocation(orderDto.getId()).equals(orderDto.getDestinationPlace())) {
                orderStateDto = fromOrderStateToDto(updateState(OrderStates.ORDER_COMPLETED.toString()));
            }
            orderDto.setState(orderStateDto);
        }

        List<OrderDto> acceptedOrdersDto = new ArrayList<>();

        for (Order acceptedOrder : acceptedOrders) {
            acceptedOrdersDto.add(fromOrderToDto(acceptedOrder));
        }

        return acceptedOrdersDto;
    }

    @Override
    public String getState(long id) {
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

    private boolean isFinalWarehouse(OrderDto order) {
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

    private CoefficientForPriceCalculationDto getCoefficient(OrderProcessingPointDto processingPointDto) throws SQLException {
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

        if (processingPointDto.getLocation().equals(russia)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(russia);
        } else if (processingPointDto.getLocation().equals(poland)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(poland);
        } else {
            throw new IllegalArgumentException("This country is not supported yet!!!" + processingPointDto.getLocation());
        }
        return coefficientForPriceCalculation;
    }
}