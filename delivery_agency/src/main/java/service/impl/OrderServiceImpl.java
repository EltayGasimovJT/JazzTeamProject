package service.impl;

import dto.*;
import dto.WorkingPlaceType;
import entity.*;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final ModelMapper modelMapper = new ModelMapper();
    private final CoefficientForPriceCalculationService priceCalculationRuleService = new CoefficientForPriceCalculationServiceImpl();
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";

    @Override
    public Order updateOrderCurrentLocation(long id, AbstractBuildingDto newLocation) {
        Order order = findOne(id);
        if(newLocation.getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)){
            order.setCurrentLocation(modelMapper.map(newLocation, OrderProcessingPoint.class));
        }else if(newLocation.getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)){
            order.setCurrentLocation(modelMapper.map(newLocation, Warehouse.class));
        }
        return orderRepository.update(order);
    }

    @Override
    public void updateOrderHistory(long id, OrderHistoryDto newHistory) {
        Order order = orderRepository.findOne(id);
        order.setHistory(Collections.singletonList(modelMapper.map(newHistory, OrderHistory.class)));
        orderRepository.update(order);
    }

    @Override
    public Order create(OrderDto order) throws SQLException {
        BigDecimal price = calculatePrice(order);
        OrderState orderState = updateState(OrderStates.READY_TO_SEND.toString());
        order.setPrice(price);
        order.setState(modelMapper.map(orderState, OrderStateDto.class));
        OrderHistory orderHistory = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .changingTime(order.getSendingTime())
                .user(User.builder().build())
                .build();
        order.setHistory(Collections.singletonList(modelMapper.map(orderHistory, OrderHistoryDto.class)));

        Client senderToSave = Client.builder()
                .id(order.getSender().getId())
                .name(order.getSender().getName())
                .surname(order.getSender().getSurname())
                .passportId(order.getSender().getPassportId())
                .phoneNumber(order.getSender().getPhoneNumber())
                .build();

        Client recipientToSave = Client.builder()
                .id(order.getRecipient().getId())
                .name(order.getRecipient().getName())
                .surname(order.getRecipient().getSurname())
                .passportId(order.getRecipient().getPassportId())
                .phoneNumber(order.getRecipient().getPhoneNumber())
                .build();

        OrderProcessingPoint departurePoint = new OrderProcessingPoint();
        departurePoint.setId(order.getCurrentLocation().getId());
        OrderProcessingPoint destinationPlace = new OrderProcessingPoint();
        departurePoint.setId(order.getDestinationPlace().getId());
        OrderHistory orderHistoryToSave = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .comment(order.getHistory().get(0).getComment())
                .changingTime(order.getHistory().get(0).getChangingTime())
                .build();


        Order orderToSave = Order.builder()
                .id(order.getId())
                .sendingTime(order.getSendingTime())
                .sender(senderToSave)
                .sendingTime(order.getSendingTime())
                .recipient(recipientToSave)
                .currentLocation(departurePoint)
                .destinationPlace(destinationPlace)
                .history(Collections.singletonList(orderHistoryToSave))
                .parcelParameters(modelMapper.map(order.getParcelParameters(), ParcelParameters.class))
                .price(order.getPrice())
                .state(OrderState.builder()
                        .state(OrderStates.READY_TO_SEND.toString())
                        .build())
                .build();
        OrderValidator.validateOrder(orderToSave);

        return orderRepository.save(orderToSave);
    }

    @Override
    public Order findOne(long id) throws IllegalArgumentException {
        Order orderById = orderRepository.findOne(id);

        OrderValidator.validateOrder(orderById);

        return orderById;
    }

    @Override
    public Order findByRecipient(ClientDto recipient) throws IllegalArgumentException {
        Order byRecipient = orderRepository.findByRecipient(modelMapper.map(recipient, Client.class));
        OrderValidator.validateOrder(byRecipient);

        return byRecipient;
    }

    @Override
    public Order findBySender(ClientDto sender) throws IllegalArgumentException {
        Order bySender = orderRepository.findByRecipient(modelMapper.map(sender, Client.class));
        OrderValidator.validateOrder(bySender);
        return bySender;
    }

    @Override
    public AbstractBuilding getCurrentOrderLocation(long id) throws IllegalArgumentException {
        return orderRepository.findOne(id).getCurrentLocation();
    }

    @Override
    public void send(List<OrderDto> orderDtos) {
        List<Order> orders = new ArrayList<>();

        for (OrderDto orderDto : orderDtos) {
            orders.add(modelMapper.map(orderDto, Order.class));
        }

        for (Order order : orders) {
            OrderState orderState;
            if (isFinalWarehouse(modelMapper.map(order, OrderDto.class))) {
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
        for (OrderDto order : orderDtos) {
            ordersToSend.add(modelMapper.map(order, Order.class));
        }

        orderRepository.saveSentOrders(ordersToSend);
    }

    @Override
    public List<Order> accept(List<OrderDto> acceptedOrders) {
        List<Order> ordersToAccept = new ArrayList<>();
        for (OrderDto order : acceptedOrders) {
            ordersToAccept.add(modelMapper.map(order, Order.class));
        }

        List<Order> ordersFromRepository = orderRepository.acceptOrders(ordersToAccept);

        List<OrderDto> expectedOrderDtos = new ArrayList<>();

        for (Order order : ordersFromRepository) {
            expectedOrderDtos.add(modelMapper.map(order, OrderDto.class));
        }

        compareOrders(expectedOrderDtos, acceptedOrders);


        for (OrderDto orderDto : expectedOrderDtos) {
            OrderState orderState;
            if (isFinalWarehouse(orderDto)) {
                orderState = updateState(OrderStates.ON_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(orderDto.getId()).equals(orderDto.getDestinationPlace())) {
                orderState = updateState(OrderStates.ORDER_COMPLETED.toString());
            }
            orderDto.setState(modelMapper.map(orderState, OrderStateDto.class));
        }

        return ordersFromRepository;
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
    public BigDecimal calculatePrice(OrderDto order) throws IllegalArgumentException, SQLException {
        CoefficientForPriceCalculationDto coefficientForPriceCalculation = getCoefficient(order.getDestinationPlace());

        return priceCalculationRuleService.calculatePrice(order, coefficientForPriceCalculation);
    }

    @Override
    public List<List<Order>> getOrdersOnTheWay() {
        List<List<Order>> allSentOrders = orderRepository.getSentOrders();
        if (allSentOrders.isEmpty()) {
            throw new IllegalArgumentException("There is no orders on the way!!!");
        }

        return allSentOrders;
    }

    @Override
    public Order update(OrderDto order) {
        Order orderToUpdate = orderRepository.findOne(order.getId());
        Client newRecipient = Client.builder()
                .id(order.getRecipient().getId())
                .name(order.getRecipient().getName())
                .surname(order.getRecipient().getSurname())
                .passportId(order.getRecipient().getPassportId())
                .phoneNumber(order.getRecipient().getPhoneNumber())
                .build();

        OrderState orderState = OrderState.builder()
                .id(order.getState().getId())
                .state(order.getState().getState())
                .build();

        orderToUpdate.setRecipient(newRecipient);
        orderToUpdate.setState(orderState);
        OrderValidator.validateOrder(orderToUpdate);

        return orderRepository.update(orderToUpdate);
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