package util;

import dto.*;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {

    private ConvertUtil(){}

    public static OrderProcessingPointDto fromOrderProcessingPointToDTO(OrderProcessingPoint orderProcessingPoint) {
        final OrderProcessingPointDto processingPointDto = new OrderProcessingPointDto();
        processingPointDto.setId(orderProcessingPoint.getId());
        processingPointDto.setLocation(orderProcessingPoint.getLocation());
        processingPointDto.setWarehouse(orderProcessingPoint.getWarehouse());
        return processingPointDto;
    }

    public static OrderProcessingPoint fromDtoToOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setLocation(orderProcessingPointDto.getLocation());
        orderProcessingPoint.setWarehouse(orderProcessingPointDto.getWarehouse());
        return orderProcessingPoint;
    }

    public static OrderDto fromOrderToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .sendingTime(order.getSendingTime())
                .state(fromOrderStateToDto(order.getState()))
                .parcelParameters(fromDtoToParcelParameters(order.getParcelParameters()))
                .sender(fromClientToDto(order.getSender()))
                .destinationPlace(fromOrderProcessingPointToDTO(order.getDestinationPlace()))
                .history(fromOrderHistoryToDto(order.getHistory()))
                .recipient(fromClientToDto(order.getRecipient()))
                .build();
    }

    public static Order fromDtoToOrder(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .price(orderDto.getPrice())
                .parcelParameters(fromParcelParametersToDto(orderDto.getParcelParameters()))
                .sender(fromDtoToClient(orderDto.getSender()))
                .recipient(fromDtoToClient(orderDto.getRecipient()))
                .destinationPlace(fromDtoToOrderProcessingPoint(orderDto.getDestinationPlace()))
                .history(fromDtoToOrderHistory(orderDto.getHistory()))
                .state(fromDtoToOrderHistory(orderDto.getState()))
                .sendingTime(orderDto.getSendingTime())
                .build();
    }

    private static ParcelParametersDto fromDtoToParcelParameters(ParcelParameters parcelParameters) {
        return ParcelParametersDto.builder()
                .height(parcelParameters.getHeight())
                .weight(parcelParameters.getWeight())
                .width(parcelParameters.getWidth())
                .length(parcelParameters.getLength())
                .build();
    }

    private static ParcelParameters fromParcelParametersToDto(ParcelParametersDto parcelParameters) {
        return ParcelParameters.builder()
                .height(parcelParameters.getHeight())
                .weight(parcelParameters.getWeight())
                .width(parcelParameters.getWidth())
                .length(parcelParameters.getLength())
                .build();
    }

    public static List<Order> fromDtosToOrders(List<OrderDto> orderDtos){
        List<Order> orders = new ArrayList<>();

        for (OrderDto orderDto : orderDtos) {
            orders.add(fromDtoToOrder(orderDto));
        }
        return orders;
    }

    public static List<OrderDto> fromOrdersToDtos(List<Order> orders){
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(fromOrderToDto(order));
        }
        return orderDtos;
    }

    public static UserDto fromUserToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .workingPlace(user.getWorkingPlace())
                .roles(user.getRoles())
                .build();
    }

    public static User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .workingPlace(userDto.getWorkingPlace())
                .build();
    }

    public static VoyageDto fromVoyageToDTO(Voyage voyage) {
        VoyageDto voyageDto = new VoyageDto();
        voyageDto.setId(voyage.getId());
        voyageDto.setDeparturePoint(voyage.getDeparturePoint());
        voyageDto.setDestinationPoint(voyage.getDestinationPoint());
        voyageDto.setSendingTime(voyage.getSendingTime());
        return voyageDto;
    }

    public static Voyage fromDtoToVoyage(VoyageDto voyageDto) {
        Voyage voyage = new Voyage();
        voyage.setId(voyageDto.getId());
        voyage.setDeparturePoint(voyageDto.getDeparturePoint());
        voyage.setDestinationPoint(voyageDto.getDestinationPoint());
        voyage.setSendingTime(voyageDto.getSendingTime());
        return voyage;
    }


    public static WarehouseDto fromWarehouseToDTO(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(warehouse.getId());
        warehouseDto.setLocation(warehouse.getLocation());
        return warehouseDto;
    }

    public static Warehouse fromDtoToWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDto.getId());
        warehouse.setLocation(warehouseDto.getLocation());
        return warehouse;
    }

    public static CoefficientForPriceCalculationDto fromCoefficientForPriceCalculationToDTO(CoefficientForPriceCalculation coefficientForPriceCalculation) {
        return CoefficientForPriceCalculationDto.builder()
                .id(coefficientForPriceCalculation.getId())
                .country(coefficientForPriceCalculation.getCountry())
                .countryCoefficient(coefficientForPriceCalculation.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculation.getParcelSizeLimit())
                .build();
    }

    public static CoefficientForPriceCalculation fromDtoToCoefficientForPriceCalculation(CoefficientForPriceCalculationDto coefficientForPriceCalculationDto) {
        return CoefficientForPriceCalculation.builder()
                .id(coefficientForPriceCalculationDto.getId())
                .country(coefficientForPriceCalculationDto.getCountry())
                .countryCoefficient(coefficientForPriceCalculationDto.getCountryCoefficient())
                .parcelSizeLimit(coefficientForPriceCalculationDto.getParcelSizeLimit())
                .build();
    }


    public static ClientDto fromClientToDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .passportId(client.getPassportId())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public static Client fromDtoToClient(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .passportId(clientDto.getPassportId())
                .phoneNumber(clientDto.getPhoneNumber())
                .build();
    }

    public static List<OrderProcessingPoint> fromDtosToProcessingPoints(List<OrderProcessingPointDto> processingPointDtos){
        List<OrderProcessingPoint> processingPoints = new ArrayList<>();

        for (OrderProcessingPointDto processingPointDto : processingPointDtos) {
            processingPoints.add(fromDtoToOrderProcessingPoint(processingPointDto));
        }

        return processingPoints;
    }

    public static List<OrderProcessingPointDto> fromProcessingPointsToDtos(List<OrderProcessingPoint> processingPoints){
        List<OrderProcessingPointDto> processingPointsDtos = new ArrayList<>();

        for (OrderProcessingPoint processingPoint : processingPoints) {
            processingPointsDtos.add(fromOrderProcessingPointToDTO(processingPoint));
        }

        return processingPointsDtos;
    }

    public static OrderHistoryDto fromOrderHistoryToDto(OrderHistory history){
        return OrderHistoryDto.builder()
                .changingTime(history.getChangingTime())
                .comment(history.getComment())
                .user(fromUserToDto(history.getUser()))
                .build();
    }

    public static OrderHistory fromDtoToOrderHistory(OrderHistoryDto historyDto){
        return OrderHistory.builder()
                .changingTime(historyDto.getChangingTime())
                .comment(historyDto.getComment())
                .user(fromDtoToUser(historyDto.getUser()))
                .build();
    }

    public static OrderStateDto fromOrderStateToDto(OrderState state){
        return OrderStateDto.builder()
                .id(state.getId())
                .state(state.getState())
                .build();
    }

    public static OrderState fromDtoToOrderHistory(OrderStateDto stateDto){
        return OrderState.builder()
                .id(stateDto.getId())
                .state(stateDto.getState())
                .build();
    }
}