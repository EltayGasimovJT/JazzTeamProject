package util;

import dto.*;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {

    private ConvertUtil(){}

    public static OrderProcessingPointDto fromOrderProcessingPointToDTO(OrderProcessingPoint orderProcessingPoint) {
        return OrderProcessingPointDto.builder()
                .id(orderProcessingPoint.getId())
                .warehouse(orderProcessingPoint.getWarehouse())
                .build();
    }

    public static OrderProcessingPoint fromDtoToOrderProcessingPoint(OrderProcessingPointDto orderProcessingPointDto) {
        OrderProcessingPoint orderProcessingPoint = new OrderProcessingPoint();
        orderProcessingPoint.setId(orderProcessingPointDto.getId());
        orderProcessingPoint.setWarehouse(orderProcessingPointDto.getWarehouse());
        return orderProcessingPoint;
    }

    public static OrderDto fromOrderToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .price(order.getPrice())
                .sendingTime(order.getSendingTime())
                .build();
    }

    public static Order fromDtoToOrder(OrderDto orderDto) {
        Order.OrderBuilder builder = Order.builder();
        return builder
                .id(orderDto.getId())
                .price(orderDto.getPrice())
                .sendingTime(orderDto.getSendingTime())
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
                .roles(user.getRoles())
                .build();
    }

    public static User fromDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles())
                .build();
    }

    public static VoyageDto fromVoyageToDTO(Voyage voyage) {
        return VoyageDto.builder()
                .id(voyage.getId())
                .departurePoint(voyage.getDeparturePoint())
                .destinationPoint(voyage.getDestinationPoint())
                .sendingTime(voyage.getSendingTime())
                .build();
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
        return WarehouseDto.builder()
                .id(warehouse.getId())
                .build();
    }

    public static Warehouse fromDtoToWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDto.getId());
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
}