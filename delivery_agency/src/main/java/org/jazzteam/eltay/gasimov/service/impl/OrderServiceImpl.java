package org.jazzteam.eltay.gasimov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.OrderRepository;
import org.jazzteam.eltay.gasimov.repository.VoyageRepository;
import org.jazzteam.eltay.gasimov.service.ClientService;
import org.jazzteam.eltay.gasimov.service.CoefficientForPriceCalculationService;
import org.jazzteam.eltay.gasimov.service.OrderService;
import org.jazzteam.eltay.gasimov.validator.OrderValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CoefficientForPriceCalculationService priceCalculationRuleService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VoyageRepository voyageRepository;

    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_WAREHOUSE_WORKER = "Warehouse Worker";
    private static final String ROLE_PICKUP_WORKER = "Pick up Worker";

    @Override
    public Order updateOrderCurrentLocation(long idForLocationUpdate, AbstractBuildingDto newLocation) throws IllegalArgumentException {
        Order order = findOne(idForLocationUpdate);
        OrderValidator.validateOrder(order);
        if (newLocation.getWorkingPlaceType().equals(WorkingPlaceType.PROCESSING_POINT)) {
            order.setCurrentLocation(modelMapper.map(newLocation, OrderProcessingPoint.class));
        } else if (newLocation.getWorkingPlaceType().equals(WorkingPlaceType.WAREHOUSE)) {
            order.setCurrentLocation(modelMapper.map(newLocation, Warehouse.class));
        }
        return orderRepository.save(order);
    }

    @Override
    public void updateOrderHistory(long idForHistoryUpdate, OrderHistoryDto newHistory) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(idForHistoryUpdate);
        Order foundOrder = foundClientFromRepository.orElseGet(Order::new);
        OrderValidator.validateOrder(foundOrder);
        Set<OrderHistory> histories = foundOrder.getHistory();
        histories.add(modelMapper.map(newHistory, OrderHistory.class));
        foundOrder.setHistory(histories);
        orderRepository.save(foundOrder);
    }

    @Override
    public Order save(OrderDto orderDtoToSave) throws IllegalArgumentException {
        //OrderValidator.validateOrder(CustomModelMapper.mapDtoToOrder(orderDtoToSave));

        BigDecimal price = orderDtoToSave.getPrice();
        OrderState orderState = updateState(OrderStates.READY_TO_SEND.toString());
        orderDtoToSave.setPrice(price);
        orderDtoToSave.setState(modelMapper.map(orderState, OrderStateDto.class));
        OrderHistory orderHistory = OrderHistory.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND.toString())
                .user(new User())
                .comment(orderDtoToSave.getHistory().get(0).getComment())
                .build();
        orderDtoToSave.setHistory(Collections.singletonList(modelMapper.map(orderHistory, OrderHistoryDto.class)));


        Client senderToSave = Client.builder()
                .id(orderDtoToSave.getSenderId())
                .name(clientService.findById(orderDtoToSave.getSenderId()).getName())
                .surname(clientService.findById(orderDtoToSave.getSenderId()).getSurname())
                .passportId(clientService.findById(orderDtoToSave.getSenderId()).getPassportId())
                .phoneNumber(clientService.findById(orderDtoToSave.getSenderId()).getPhoneNumber())
                .build();

        Client recipientToSave = Client.builder()
                .id(orderDtoToSave.getRecipient().getId())
                .name(orderDtoToSave.getRecipient().getName())
                .surname(orderDtoToSave.getRecipient().getSurname())
                .passportId(orderDtoToSave.getRecipient().getPassportId())
                .phoneNumber(orderDtoToSave.getRecipient().getPhoneNumber())
                .build();

        Set<OrderHistory> historiesToSave = orderDtoToSave.getHistory().stream()
                .map(orderHistoryDto -> modelMapper.map(orderHistoryDto, OrderHistory.class))
                .collect(Collectors.toSet());

        OrderProcessingPoint departurePointToSave = new OrderProcessingPoint();
        departurePointToSave.setId(orderDtoToSave.getCurrentLocation().getId());
        OrderProcessingPoint destinationPlaceToSave = new OrderProcessingPoint();
        departurePointToSave.setId(orderDtoToSave.getDestinationPlace().getId());

        Order orderToSave = Order.builder()
                .id(orderDtoToSave.getId())
                .sender(senderToSave)
                .recipient(recipientToSave)
                .currentLocation(departurePointToSave)
                .destinationPlace(destinationPlaceToSave)
                .history(historiesToSave)
                .parcelParameters(modelMapper.map(orderDtoToSave.getParcelParameters(), ParcelParameters.class))
                .price(orderDtoToSave.getPrice())
                .state(orderState)
                .trackNumber(generateNewTrackNumber())
                .build();

        if (orderDtoToSave.getRecipient().getName() != null && orderDtoToSave.getRecipient().getSurname() != null
                && orderDtoToSave.getRecipient().getPassportId() != null && orderDtoToSave.getRecipient().getPhoneNumber() != null) {
            orderToSave.setRecipient(clientService.findById(orderDtoToSave.getRecipient().getId()));
        }

        return orderRepository.save(orderToSave);
    }

    public String generateNewTrackNumber() {
        int randomStringLength = 7;
        String charset = "0123456789ABCDEFGHIJKLMOPQRSTUVWXYZ";
        return RandomStringUtils.random(randomStringLength, charset);
    }

    @Override
    public Order findOne(long idForSearch) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(idForSearch);
        Order foundOrder = foundClientFromRepository.orElseGet(Order::new);

        OrderValidator.validateOrder(foundOrder);

        return foundOrder;
    }

    @Override
    public Order findByRecipient(ClientDto recipientForSearch) throws IllegalArgumentException {
        Order foundOrder = orderRepository.findByRecipient(modelMapper.map(recipientForSearch, Client.class));
        OrderValidator.validateOrder(foundOrder);
        return foundOrder;
    }

    @Override
    public Order findBySender(ClientDto senderForSearch) throws IllegalArgumentException {
        Order foundOrder = orderRepository.findByRecipient(modelMapper.map(senderForSearch, Client.class));
        OrderValidator.validateOrder(foundOrder);
        return foundOrder;
    }

    @Override
    public AbstractBuilding getCurrentOrderLocation(long idForFindCurrentLocation) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(idForFindCurrentLocation);
        Order foundOrder = foundClientFromRepository.orElseGet(Order::new);
        OrderValidator.validateOrder(foundOrder);
        return foundOrder.getCurrentLocation();
    }

    @Override
    public void send(List<OrderDto> orderDtosToSend) throws IllegalArgumentException {
        List<Order> ordersToSend = orderDtosToSend.stream()
                .map(CustomModelMapper::mapDtoToOrder)
                .collect(Collectors.toList());
        Optional<Voyage> foundVoyage = voyageRepository.findById(orderDtosToSend.get(0).getCurrentLocation().getId());
        Voyage voyageToSave = foundVoyage.orElseGet(Voyage::new);

        for (Order order : ordersToSend) {
            OrderState orderState;
            if (isFinalWarehouse(CustomModelMapper.mapOrderToDto(order))) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = updateState(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString());
            }
            order.setState(orderState);
        }

        voyageToSave.setDispatchedOrders(ordersToSend);
    }

    @Override
    public List<Order> accept(List<OrderDto> orderDtosToAccept) throws IllegalArgumentException {
        List<Order> ordersToAccept = orderDtosToAccept.stream()
                .map(orderDto -> modelMapper.map(orderDto, Order.class))
                .collect(Collectors.toList());

        Optional<Voyage> foundVoyage = voyageRepository.findById(orderDtosToAccept.get(0).getCurrentLocation().getId());
        Voyage voyageToSave = foundVoyage.orElseGet(Voyage::new);
        List<Order> ordersFromRepository = voyageToSave.getDispatchedOrders();

        OrderValidator.validateOrders(ordersFromRepository);

        List<OrderDto> expectedOrderDtos = ordersFromRepository.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());

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
    public String getState(long idForStateFind) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(idForStateFind);
        Order foundOrder = foundClientFromRepository.orElseGet(Order::new);
        OrderValidator.validateOrder(foundOrder);
        return foundOrder.getState().getState();
    }

    @Override
    public void compareOrders(List<OrderDto> expectedOrders, List<OrderDto> acceptedOrders) throws IllegalArgumentException {
        for (int i = 0; i < expectedOrders.size(); i++) {
            if (!expectedOrders.get(i).equals(acceptedOrders.get(i))) {
                throw new IllegalArgumentException("Expected orders are not equal to accepted!!!");
            }
        }
    }

    private boolean isFinalWarehouse(OrderDto orderToCheck) throws IllegalArgumentException {
        return orderToCheck.getDestinationPlace().equals(orderToCheck.getCurrentLocation());
    }

    @Override
    public List<Order> findAll() throws IllegalArgumentException {
        List<Order> ordersFromRepository = orderRepository.findAll();

        OrderValidator.validateOrders(ordersFromRepository);

        return ordersFromRepository;
    }

    @Override
    public BigDecimal calculatePrice(OrderDto orderForCalculate) throws IllegalArgumentException {
        CoefficientForPriceCalculationDto coefficientForCalculate = getCoefficient(orderForCalculate.getDestinationPlace());

        return priceCalculationRuleService.calculatePrice(orderForCalculate, coefficientForCalculate);
    }


    @Override
    public Order update(OrderDto orderDtoToUpdate) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(orderDtoToUpdate.getId());
        Order orderToUpdate = foundClientFromRepository.orElseGet(Order::new);
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

        return orderRepository.save(orderToUpdate);
    }

    @Override
    public void delete(Long idForDelete) throws IllegalArgumentException {
        Optional<Order> foundClientFromRepository = orderRepository.findById(idForDelete);
        Order orderToUpdate = foundClientFromRepository.orElseGet(Order::new);
        OrderValidator.validateOrder(orderToUpdate);
        orderRepository.deleteById(idForDelete);
    }

    @Override
    public Order findByTrackNumber(String trackNumber) {
        Order foundOrder = orderRepository.findByTrackNumber(trackNumber);
        OrderValidator.validateOrder(foundOrder);
        return foundOrder;
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
        //orderState.setRolesAllowedPutToState(newRolesAllowedPutToState);
        // orderState.setRolesAllowedWithdrawFromState(newRolesAllowedToWithdrawFromState);
        return orderState;
    }

    private CoefficientForPriceCalculationDto getCoefficient(OrderProcessingPointDto processingPointDto) throws IllegalArgumentException {
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