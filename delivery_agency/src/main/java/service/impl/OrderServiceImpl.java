package service.impl;

import dto.*;
import dto.WorkingPlaceType;
import entity.*;
import lombok.extern.slf4j.Slf4j;
import mapping.OrderMapper;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;


@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";

    @Override
    public Order updateOrderCurrentLocation(long idForLocationUpdate, AbstractBuildingDto newLocation) {
        Order order = findOne(idForLocationUpdate);
        OrderValidator.validateOrder(order);
        if (newLocation.getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            order.setCurrentLocation(modelMapper.map(newLocation, OrderProcessingPoint.class));
        } else if (newLocation.getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            order.setCurrentLocation(modelMapper.map(newLocation, Warehouse.class));
        }
        return orderRepository.update(order);
    }

    @Override
    public void updateOrderHistory(long idForHistoryUpdate, OrderHistoryDto newHistory) {
        Order order = orderRepository.findOne(idForHistoryUpdate);
        OrderValidator.validateOrder(order);
        order.setHistory(Collections.singletonList(modelMapper.map(newHistory, OrderHistory.class)));
        orderRepository.update(order);
    }

    @Override
    public Order save(OrderDto orderDtoToSave) throws SQLException {
        BigDecimal price = calculatePrice(orderDtoToSave);
        OrderState orderState = updateState(OrderStates.READY_TO_SEND.toString());
        orderDtoToSave.setPrice(price);
        orderDtoToSave.setState(modelMapper.map(orderState, OrderStateDto.class));
        OrderHistory orderHistory = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .user(User.builder().build())
                .build();
        orderDtoToSave.setHistory(Collections.singletonList(modelMapper.map(orderHistory, OrderHistoryDto.class)));

        Client senderToSave = Client.builder()
                .id(orderDtoToSave.getSender().getId())
                .name(orderDtoToSave.getSender().getName())
                .surname(orderDtoToSave.getSender().getSurname())
                .passportId(orderDtoToSave.getSender().getPassportId())
                .phoneNumber(orderDtoToSave.getSender().getPhoneNumber())
                .build();

        Client recipientToSave = Client.builder()
                .id(orderDtoToSave.getRecipient().getId())
                .name(orderDtoToSave.getRecipient().getName())
                .surname(orderDtoToSave.getRecipient().getSurname())
                .passportId(orderDtoToSave.getRecipient().getPassportId())
                .phoneNumber(orderDtoToSave.getRecipient().getPhoneNumber())
                .build();

        OrderProcessingPoint departurePoint = new OrderProcessingPoint();
        departurePoint.setId(orderDtoToSave.getCurrentLocation().getId());
        OrderProcessingPoint destinationPlace = new OrderProcessingPoint();
        departurePoint.setId(orderDtoToSave.getDestinationPlace().getId());

        OrderHistory orderHistoryToSave = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .comment(orderDtoToSave.getHistory().get(0).getComment())
                .changingTime(orderDtoToSave.getHistory().get(0).getChangingTime())
                .build();

        Order orderToSave = Order.builder()
                .id(orderDtoToSave.getId())
                .sender(senderToSave)
                .recipient(recipientToSave)
                .currentLocation(departurePoint)
                .destinationPlace(destinationPlace)
                .history(Collections.singletonList(orderHistoryToSave))
                .parcelParameters(modelMapper.map(orderDtoToSave.getParcelParameters(), ParcelParameters.class))
                .price(orderDtoToSave.getPrice())
                .state(OrderState.builder()
                        .state(OrderStates.READY_TO_SEND.toString())
                        .build())
                .build();
        OrderValidator.validateOrder(orderToSave);

        return orderRepository.save(orderToSave);
    }

    @Override
    public Order findOne(long idForSearch) throws IllegalArgumentException {
        Order orderById = orderRepository.findOne(idForSearch);

        OrderValidator.validateOrder(orderById);

        return orderById;
    }

    @Override
    public Order findByRecipient(ClientDto recipientForSearch) throws IllegalArgumentException {
        Order byRecipient = orderRepository.findByRecipient(modelMapper.map(recipientForSearch, Client.class));
        OrderValidator.validateOrder(byRecipient);

        return byRecipient;
    }

    @Override
    public Order findBySender(ClientDto senderForSearch) throws IllegalArgumentException {
        Order bySender = orderRepository.findByRecipient(modelMapper.map(senderForSearch, Client.class));
        OrderValidator.validateOrder(bySender);
        return bySender;
    }

    @Override
    public AbstractBuilding getCurrentOrderLocation(long idForFindCurrentLocation) throws IllegalArgumentException {
        Order foundOrder = orderRepository.findOne(idForFindCurrentLocation);
        OrderValidator.validateOrder(foundOrder);
        return foundOrder.getCurrentLocation();
    }

    @Override
    public void send(List<OrderDto> orderDtosToSend) {
        List<Order> ordersToSend = orderDtosToSend.stream()
                .map(OrderMapper::toOrder)
                .collect(Collectors.toList());

        for (Order order : ordersToSend) {
            OrderState orderState;
            if (isFinalWarehouse(OrderMapper.toDto(order))) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString());
            }
            order.setState(orderState);
        }

        orderRepository.saveSentOrders(ordersToSend);
    }

    @Override
    public List<Order> accept(List<OrderDto> orderDtosToAccept) {
        List<Order> ordersToAccept = new ArrayList<>();
        for (OrderDto order : orderDtosToAccept) {
            ordersToAccept.add(modelMapper.map(order, Order.class));
        }

        List<Order> ordersFromRepository = orderRepository.acceptOrders(ordersToAccept);

        OrderValidator.validateOrders(ordersFromRepository);

        List<OrderDto> expectedOrderDtos = new ArrayList<>();

        for (Order order : ordersFromRepository) {
            expectedOrderDtos.add(modelMapper.map(order, OrderDto.class));
        }

        compareOrders(expectedOrderDtos, orderDtosToAccept);

        for (OrderDto orderDto : expectedOrderDtos) {
            OrderState orderState;
            if (isFinalWarehouse(orderDto)) {
                orderState = updateState(OrderStates.ON_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAREHOUSE.toString());
            }
            if (orderDto.getCurrentLocation().equals(orderDto.getDestinationPlace())) {
                orderState = updateState(OrderStates.ORDER_COMPLETED.toString());
            }
            orderDto.setState(modelMapper.map(orderState, OrderStateDto.class));
        }

        return ordersFromRepository;
    }

    @Override
    public String getState(long idForStateFind) {
        Order order = orderRepository.findOne(idForStateFind);
        OrderValidator.validateOrder(order);
        return order.getState().getState();
    }

    @Override
    public void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException {
        for (int i = 0; i < expectedOrders.size(); i++) {
            if (!expectedOrders.get(i).equals(acceptedOrders.get(i))) {
                throw new IllegalArgumentException("Expected orders are not equal to accepted!!!");
            }
        }
    }

    private boolean isFinalWarehouse(OrderDto order) {
        return order.getDestinationPlace().equals(order.getCurrentLocation());
    }

    @Override
    public List<Order> findAll() throws IllegalArgumentException {
        List<Order> orders = orderRepository.findAll();

        OrderValidator.validateOrders(orders);

        return orders;
    }

    @Override
    public BigDecimal calculatePrice(OrderDto orderForCalculate) throws IllegalArgumentException, SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = getCoefficient(orderForCalculate.getDestinationPlace());

        return priceCalculationRuleService.calculatePrice(orderForCalculate, coefficientForPriceCalculation);
    }

    @Override
    public List<List<Order>> getOrdersOnTheWay() {
        List<List<Order>> allSentOrders = orderRepository.getSentOrders();
        OrderValidator.validateOrdersOnTheWay(allSentOrders);

        return allSentOrders;
    }

    @Override
    public Order update(OrderDto orderDtoToUpdate) {
        Order orderToUpdate = orderRepository.findOne(orderDtoToUpdate.getId());
        OrderValidator.validateOrder(orderToUpdate);
        Client newRecipient = Client.builder()
                .id(orderDtoToUpdate.getRecipient().getId())
                .name(orderDtoToUpdate.getRecipient().getName())
                .surname(orderDtoToUpdate.getRecipient().getSurname())
                .passportId(orderDtoToUpdate.getRecipient().getPassportId())
                .phoneNumber(orderDtoToUpdate.getRecipient().getPhoneNumber())
                .build();

        OrderState orderState = OrderState.builder()
                .id(orderDtoToUpdate.getState().getId())
                .state(orderDtoToUpdate.getState().getState())
                .build();

        orderToUpdate.setRecipient(newRecipient);
        orderToUpdate.setState(orderState);
        OrderValidator.validateOrder(orderToUpdate);

        return orderRepository.update(orderToUpdate);
    }

    @Override
    public void delete(Long idForDelete) {
        orderRepository.delete(idForDelete);
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
        CoefficientForPriceCalculation coefficientForPriceCalculation;

        String russia = "Russia";

        CoefficientForPriceCalculation firstCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(1L)
                .countryCoefficient(1.6)
                .country(russia)
                .parcelSizeLimit(50)
                .build();

        String poland = "Poland";

        CoefficientForPriceCalculation secondCoefficient = CoefficientForPriceCalculation
                .builder()
                .id(2L)
                .countryCoefficient(1.8)
                .country(poland)
                .parcelSizeLimit(40)
                .build();

        priceCalculationRuleService.save(modelMapper.map(firstCoefficient, CoefficientForPriceCalculationDto.class));
        priceCalculationRuleService.save(modelMapper.map(secondCoefficient, CoefficientForPriceCalculationDto.class));

        if (processingPointDto.getLocation().equals(russia)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(russia);
        } else if (processingPointDto.getLocation().equals(poland)) {
            coefficientForPriceCalculation = priceCalculationRuleService.findByCountry(poland);
        } else {
            throw new IllegalArgumentException("This country is not supported yet!!!" + processingPointDto.getLocation());
        }
        return modelMapper.map(coefficientForPriceCalculation, CoefficientForPriceCalculationDto.class);
    }
}