package org.jazzteam.eltay.gasimov.mapping;

import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component(value = "customModelMapper")
public class CustomModelMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private CustomModelMapper() {
    }

    public static OrderDto mapOrderToDto(Order orderToConvert) {
        List<OrderHistoryDto> historiesToConvert = orderToConvert.getHistory().stream()
                .map(history -> modelMapper.map(history, OrderHistoryDto.class))
                .collect(Collectors.toList());

        OrderDto convertedToDto = OrderDto.builder()
                .id(orderToConvert.getId())
                .destinationPlace(modelMapper.map(orderToConvert.getDestinationPlace(), OrderProcessingPointDto.class))
                .state(modelMapper.map(orderToConvert.getState(), OrderStateDto.class))
                .recipient(modelMapper.map(orderToConvert.getRecipient(), ClientDto.class))
                .senderId(orderToConvert.getSender().getId())
                .history(historiesToConvert)
                .orderTrackNumber(orderToConvert.getTrackNumber())
                .price(orderToConvert.getPrice())
                .parcelParameters(modelMapper.map(orderToConvert.getParcelParameters(), ParcelParametersDto.class))
                .build();
        if (orderToConvert.getCurrentLocation() instanceof OrderProcessingPoint) {
            convertedToDto.setCurrentLocation(modelMapper.map(orderToConvert.getCurrentLocation(), OrderProcessingPointDto.class));
        } else if (orderToConvert.getCurrentLocation() instanceof Warehouse) {
            convertedToDto.setCurrentLocation(modelMapper.map(orderToConvert.getCurrentLocation(), WarehouseDto.class));
        }
        return convertedToDto;
    }

    public static Order mapDtoToOrder(OrderDto orderDtoToConvert) {
        Set<OrderHistory> historiesToConvert = new HashSet<>();
        if (orderDtoToConvert.getHistory() != null) {
            historiesToConvert = orderDtoToConvert.getHistory().stream()
                    .map(history -> modelMapper.map(history, OrderHistory.class))
                    .collect(Collectors.toSet());
        }

        Order convertedToOrder = Order.builder()
                .id(orderDtoToConvert.getId())
                .destinationPlace(modelMapper.map(orderDtoToConvert.getDestinationPlace(), OrderProcessingPoint.class))
                .state(modelMapper.map(orderDtoToConvert.getState(), OrderState.class))
                .recipient(modelMapper.map(orderDtoToConvert.getRecipient(), Client.class))
                .sender(modelMapper.map(orderDtoToConvert.getSenderId(), Client.class))
                .trackNumber(orderDtoToConvert.getOrderTrackNumber())
                .history(historiesToConvert)
                .price(orderDtoToConvert.getPrice())
                .parcelParameters(modelMapper.map(orderDtoToConvert.getParcelParameters(), ParcelParameters.class))
                .build();
        if (orderDtoToConvert.getCurrentLocation() instanceof OrderProcessingPointDto) {
            convertedToOrder.setCurrentLocation(modelMapper.map(orderDtoToConvert.getCurrentLocation(), OrderProcessingPoint.class));
        } else if (orderDtoToConvert.getCurrentLocation() instanceof WarehouseDto) {
            convertedToOrder.setCurrentLocation(modelMapper.map(orderDtoToConvert.getCurrentLocation(), Warehouse.class));
        }
        return convertedToOrder;
    }

    public static UserDto mapUserToDto(User userToConvert) {
        UserDto convertedToDto = UserDto.builder()
                .id(userToConvert.getId())
                .name(userToConvert.getName())
                .surname(userToConvert.getSurname())
                .password(userToConvert.getPassword())
                .roles(userToConvert.getRoles())
                .build();
        if (userToConvert.getWorkingPlace() instanceof OrderProcessingPoint) {
            convertedToDto.setWorkingPlace(WorkingPlaceType.PROCESSING_POINT);
        } else if (userToConvert.getWorkingPlace() instanceof Warehouse) {
            convertedToDto.setWorkingPlace(WorkingPlaceType.WAREHOUSE);
        }
        return convertedToDto;
    }

    public static User mapDtoToUser(UserDto userDtoToConvert) {
        User convertedToUser = User.builder()
                .id(userDtoToConvert.getId())
                .name(userDtoToConvert.getName())
                .surname(userDtoToConvert.getSurname())
                .password(userDtoToConvert.getPassword())
                .roles(userDtoToConvert.getRoles())
                .build();
        if (userDtoToConvert.getWorkingPlace().equals(WorkingPlaceType.PROCESSING_POINT)) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDtoToConvert.getWorkingPlace().equals(WorkingPlaceType.WAREHOUSE)) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), Warehouse.class));
        }
        return convertedToUser;
    }

    public static Warehouse mapDtoToWarehouse(WarehouseDto warehouseDtoToConvert) {
        Warehouse convertedToWarehouse = new Warehouse();
        convertedToWarehouse.setId(warehouseDtoToConvert.getId());
        convertedToWarehouse.setLocation(warehouseDtoToConvert.getLocation());
        convertedToWarehouse.setWorkingPlaceType(warehouseDtoToConvert.getWorkingPlaceType().toString());
        if (warehouseDtoToConvert.getDispatchedOrders() != null) {
            convertedToWarehouse.setDispatchedOrders(
                    warehouseDtoToConvert
                            .getDispatchedOrders()
                            .stream()
                            .map(CustomModelMapper::mapDtoToOrder)
                            .collect(Collectors.toList()));
        }
        if (warehouseDtoToConvert.getExpectedOrders() != null) {
            convertedToWarehouse.setExpectedOrders(
                    warehouseDtoToConvert
                            .getExpectedOrders()
                            .stream()
                            .map(CustomModelMapper::mapDtoToOrder)
                            .collect(Collectors.toList())
            );
        }
        if (warehouseDtoToConvert.getOrderProcessingPoints() != null) {
            convertedToWarehouse.setOrderProcessingPoints(
                    warehouseDtoToConvert.getOrderProcessingPoints()
                            .stream()
                            .map(processingPointToConvert -> modelMapper.map(processingPointToConvert, OrderProcessingPoint.class))
                            .collect(Collectors.toList())
            );
        }
        return convertedToWarehouse;
    }

    public static WarehouseDto mapWarehouseToDto(Warehouse warehouseToConvert) {
        WarehouseDto convertedToDto = new WarehouseDto();
        convertedToDto.setId(warehouseToConvert.getId());
        convertedToDto.setLocation(warehouseToConvert.getLocation());
        convertedToDto.setWorkingPlaceType(WorkingPlaceType.valueOf(warehouseToConvert.getWorkingPlaceType()));
        convertedToDto.setDispatchedOrders(
                warehouseToConvert
                        .getDispatchedOrders()
                        .stream()
                        .map(CustomModelMapper::mapOrderToDto)
                        .collect(Collectors.toList()));
        convertedToDto.setExpectedOrders(
                warehouseToConvert
                        .getExpectedOrders()
                        .stream()
                        .map(CustomModelMapper::mapOrderToDto)
                        .collect(Collectors.toList())
        );

        convertedToDto.setOrderProcessingPoints(
                warehouseToConvert.getOrderProcessingPoints()
                        .stream()
                        .map(processingPointToConvert -> modelMapper.map(processingPointToConvert, OrderProcessingPointDto.class))
                        .collect(Collectors.toList())
        );
        return convertedToDto;
    }
}
