package org.jazzteam.eltay.gasimov.service.impl;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.jazzteam.eltay.gasimov.controller.security.CustomUserDetails;
import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.jazzteam.eltay.gasimov.mapping.CustomModelMapper;
import org.jazzteam.eltay.gasimov.repository.OrderRepository;
import org.jazzteam.eltay.gasimov.repository.VoyageRepository;
import org.jazzteam.eltay.gasimov.service.*;
import org.jazzteam.eltay.gasimov.validator.OrderValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jazzteam.eltay.gasimov.util.Constants.*;


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
    @Autowired
    private OrderProcessingPointService orderProcessingPointService;
    @Autowired
    private ParcelParametersService parcelParametersService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private ContextService contextService;
    @Autowired
    private WorkerService workerService;

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
    public Order save(OrderDto orderDtoToSave) throws IllegalArgumentException, ObjectNotFoundException {
        return getOrderFoSave(orderDtoToSave);
    }


    private String generateNewTrackNumber() {
        int randomStringLength = 9;
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
                orderState = orderStateService.findByState(OrderStates.ON_THE_WAY_TO_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = orderStateService.findByState(OrderStates.ON_THE_WAY_TO_THE_WAREHOUSE.toString());
            }
            if (getCurrentOrderLocation(order.getId()) instanceof OrderProcessingPoint) {
                orderState = orderStateService.findByState(OrderStates.ON_THE_WAY_TO_THE_RECEPTION.toString());
            }
            order.setState(orderState);
        }

        voyageToSave.setDispatchedOrders(ordersToSend);
    }

    @Override
    public List<Order> accept(List<OrderDto> orderDtosToAccept) throws IllegalArgumentException {

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
                orderState = orderStateService.findByState(OrderStates.ON_THE_FINAL_WAREHOUSE.toString());
            } else {
                orderState = orderStateService.findByState(OrderStates.ON_THE_WAREHOUSE.toString());
            }
            if (orderDto.getCurrentLocation().equals(orderDto.getDestinationPlace())) {
                orderState = orderStateService.findByState(OrderStates.ON_THE_RECEPTION_STORE.toString());
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
    public BigDecimal calculatePrice(OrderDto orderForCalculate) throws IllegalArgumentException, ObjectNotFoundException {
        CoefficientForPriceCalculationDto coefficientForCalculate = getCoefficient(orderForCalculate.getDestinationPlace());
        return priceCalculationRuleService.calculatePrice(orderForCalculate.getParcelParameters(), coefficientForCalculate.getCountry());
    }


    @Override
    public Order update(OrderDto orderDtoToUpdate) throws IllegalArgumentException {
        Optional<Order> foundOrderFromRepository = orderRepository.findById(orderDtoToUpdate.getId());
        OrderHistory historyToAdd = modelMapper.map(orderDtoToUpdate.getHistory().iterator().next(), OrderHistory.class);
        Order orderToUpdate = foundOrderFromRepository.orElseGet(Order::new);
        OrderValidator.validateOrder(orderToUpdate);
        Client newRecipient = Client.builder()
                .id(orderDtoToUpdate.getRecipient().getId())
                .name(orderDtoToUpdate.getRecipient().getName())
                .surname(orderDtoToUpdate.getRecipient().getSurname())
                .passportId(orderDtoToUpdate.getRecipient().getPassportId())
                .phoneNumber(orderDtoToUpdate.getRecipient().getPhoneNumber())
                .build();

        OrderState newState = orderStateService.findByState(orderToUpdate.getState().getState());
        orderToUpdate.getHistory().add(historyToAdd);
        orderToUpdate.setRecipient(newRecipient);
        orderToUpdate.setState(newState);
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

    @Override
    public Order createOrder(CreateOrderRequestDto requestOrder) throws ObjectNotFoundException {
        OrderState orderState = orderStateService.findByState(OrderStates.READY_TO_SEND.getState());
        OrderHistoryDto orderHistoryForSave = OrderHistoryDto.builder()
                .changedTypeEnum(OrderStateChangeType.READY_TO_SEND)
                .worker(requestOrder.getWorkerDto())
                .changedAt(LocalDateTime.now())
                .comment(orderState.getPrefix() + " " + requestOrder.getWorkerDto().getWorkingPlace().getLocation() + orderState.getSuffix())
                .build();
        OrderDto orderDtoToSave = OrderDto.builder()
                .destinationPlace(modelMapper.map(orderProcessingPointService.findByLocation(requestOrder.getDestinationPoint()), OrderProcessingPointDto.class))
                .parcelParameters(requestOrder.getParcelParameters())
                .price(requestOrder.getPrice())
                .recipient(requestOrder.getRecipient())
                .sender(requestOrder.getSender())
                .currentLocation(modelMapper.map(orderProcessingPointService.findByLocation(requestOrder.getWorkerDto().getWorkingPlace().getLocation()), OrderProcessingPointDto.class))
                .departurePoint(modelMapper.map(orderProcessingPointService.findByLocation(requestOrder.getWorkerDto().getWorkingPlace().getLocation()), OrderProcessingPointDto.class))
                .state(modelMapper.map(orderState, OrderStateDto.class))
                .history(Stream.of(orderHistoryForSave).collect(Collectors.toCollection(HashSet::new)))
                .build();
        return getOrderFoSave(orderDtoToSave);
    }

    @Override
    public Object changeOrderState(String orderNumber, String orderState) {
        CustomUserDetails currentUserFromContext = contextService.getCurrentUserFromContext();
        Worker foundWorker = workerService.findByName(currentUserFromContext.getUsername());
        Order foundOrder = findByTrackNumber(orderNumber);
        OrderState foundState = orderStateService.findByState(orderState);
        foundOrder.setState(foundState);
        foundOrder.setCurrentLocation(foundWorker.getWorkingPlace());
        orderRepository.save(foundOrder);
        foundOrder.getHistory().add(getNewHistory(foundOrder, foundState, orderNumber, foundWorker));
        return orderRepository.save(foundOrder);
    }

    private OrderHistory getNewHistory(Order foundOrder, OrderState orderState, String orderNumber, Worker foundWorker) {

        OrderHistory newHistory = OrderHistory.builder().build();
        newHistory.setSentAt(foundOrder.getHistory().iterator().next().getSentAt());
        newHistory.setWorker(foundWorker);
        newHistory.setChangedAt(LocalDateTime.now());
        newHistory.setComment(orderState.getPrefix() + orderNumber + orderState.getSuffix() + foundWorker.getWorkingPlace().getLocation());
        newHistory.setChangedTypeEnum(OrderStateChangeType.READY_TO_SEND.name());
        return orderHistoryService.save(CustomModelMapper.mapHistoryToDto(newHistory));
    }

    private Order getOrderFoSave(OrderDto orderDtoToSave) throws ObjectNotFoundException {
        OrderValidator.validateOrder(CustomModelMapper.mapDtoToOrder(orderDtoToSave));

        BigDecimal price = orderDtoToSave.getPrice();
        OrderState orderState = orderStateService.findByState(OrderStates.READY_TO_SEND.getState());
        orderDtoToSave.setPrice(price);
        orderDtoToSave.setState(modelMapper.map(orderStateService.findByState(READY_TO_SEND), OrderStateDto.class));

        OrderHistory savedHistory = orderHistoryService.save(modelMapper.map(orderDtoToSave.getHistory().iterator().next(), OrderHistoryDto.class));
        savedHistory.setSentAt(LocalDateTime.now());
        Set<OrderHistory> orderHistories = new HashSet<>();
        orderHistories.add(savedHistory);

        Client senderToSave = getClientToSave(orderDtoToSave.getSender());

        Client recipientToSave = getClientToSave(orderDtoToSave.getRecipient());

        OrderProcessingPoint destinationPlaceToSave = orderProcessingPointService.findByLocation(orderDtoToSave.getDestinationPlace().getLocation());
        OrderProcessingPoint departurePointToSave = orderProcessingPointService.findByLocation(orderDtoToSave.getDeparturePoint().getLocation());
        ParcelParameters savedParameters = parcelParametersService.save(orderDtoToSave.getParcelParameters());

        Order orderToSave = Order.builder()
                .sender(senderToSave)
                .recipient(recipientToSave)
                .currentLocation(departurePointToSave)
                .departurePoint(departurePointToSave)
                .destinationPlace(destinationPlaceToSave)
                .history(orderHistories)
                .parcelParameters(savedParameters)
                .price(orderDtoToSave.getPrice())
                .state(orderState)
                .trackNumber(generateNewTrackNumber())
                .sendingTime(savedHistory.getSentAt())
                .build();

        return orderRepository.save(orderToSave);
    }

    private CoefficientForPriceCalculationDto getCoefficient(OrderProcessingPointDto processingPointDto) throws IllegalArgumentException, ObjectNotFoundException {
        return modelMapper.map(priceCalculationRuleService.findByCountry(processingPointDto.getLocation()), CoefficientForPriceCalculationDto.class);
    }

    private Client getClientToSave(ClientDto client) throws ObjectNotFoundException {
        Client clientToSave;
        Client foundSender = clientService.findClientByPassportId(client.getPassportId());
        if (foundSender != null) {
            clientToSave = Client.builder()
                    .id(foundSender.getId())
                    .name(foundSender.getName())
                    .surname(foundSender.getSurname())
                    .passportId(foundSender.getPassportId())
                    .phoneNumber(foundSender.getPhoneNumber())
                    .build();
        } else {
            clientToSave = Client.builder()
                    .name(client.getName())
                    .surname(client.getSurname())
                    .passportId(client.getPassportId())
                    .phoneNumber(client.getPhoneNumber())
                    .build();
            Client savedClient = clientService.save(modelMapper.map(clientToSave, ClientDto.class));
            clientToSave.setId(savedClient.getId());
        }
        return clientToSave;
    }
}