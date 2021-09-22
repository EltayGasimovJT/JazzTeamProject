package org.jazzteam.eltay.gasimov.mapping;

import org.jazzteam.eltay.gasimov.dto.*;
import org.jazzteam.eltay.gasimov.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(value = "customModelMapper")
public class CustomModelMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private CustomModelMapper() {
    }

    public static OrderDto mapOrderToDto(Order orderToConvert) {
        Set<OrderHistoryDto> historiesToConvert = orderToConvert.getHistory().stream()
                .map(history -> modelMapper.map(history, OrderHistoryDto.class))
                .collect(Collectors.toSet());

        OrderDto convertedToDto = OrderDto.builder()
                .id(orderToConvert.getId())
                .destinationPlace(modelMapper.map(orderToConvert.getDestinationPlace(), OrderProcessingPointDto.class))
                .state(modelMapper.map(orderToConvert.getState(), OrderStateDto.class))
                .recipient(modelMapper.map(orderToConvert.getRecipient(), ClientDto.class))
                .sender(modelMapper.map(orderToConvert.getSender(), ClientDto.class))
                .history(historiesToConvert)
                .departurePoint(modelMapper.map(orderToConvert.getDeparturePoint(), OrderProcessingPointDto.class))
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
                    .map(CustomModelMapper::mapDtoToHistory)
                    .collect(Collectors.toSet());
        }

        Order convertedToOrder = Order.builder()
                .id(orderDtoToConvert.getId())
                .destinationPlace(modelMapper.map(orderDtoToConvert.getDestinationPlace(), OrderProcessingPoint.class))
                .state(modelMapper.map(orderDtoToConvert.getState(), OrderState.class))
                .recipient(modelMapper.map(orderDtoToConvert.getRecipient(), Client.class))
                .sender(modelMapper.map(orderDtoToConvert.getSender(), Client.class))
                .trackNumber(orderDtoToConvert.getOrderTrackNumber())
                .history(historiesToConvert)
                .departurePoint(modelMapper.map(orderDtoToConvert.getDeparturePoint(), OrderProcessingPoint.class))
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

    public static WorkerDto mapWorkerToDto(Worker workerToConvert) {
        WorkerRoles roleToMap = workerToConvert.getRoles().iterator().next();

        WorkerDto convertedToDto = WorkerDto.builder()
                .id(workerToConvert.getId())
                .name(workerToConvert.getName())
                .surname(workerToConvert.getSurname())
                .password(workerToConvert.getPassword())
                .build();
        if (workerToConvert.getWorkingPlace() instanceof OrderProcessingPoint) {
            convertedToDto.setWorkingPlace(modelMapper.map(workerToConvert.getWorkingPlace(), OrderProcessingPointDto.class));
        }
        if (workerToConvert.getWorkingPlace() instanceof Warehouse) {
            convertedToDto.setWorkingPlace(modelMapper.map(workerToConvert.getWorkingPlace(), WarehouseDto.class));
        }
        if (roleToMap.getRole().equals(Role.ROLE_ADMIN.toString())) {
            convertedToDto.setRole(Role.ROLE_ADMIN);
        }
        if (roleToMap.getRole().equals(Role.ROLE_PROCESSING_POINT_WORKER.toString())) {
            convertedToDto.setRole(Role.ROLE_PROCESSING_POINT_WORKER);
        }
        if (roleToMap.getRole().equals(Role.ROLE_WAREHOUSE_WORKER.toString())) {
            convertedToDto.setRole(Role.ROLE_WAREHOUSE_WORKER);
        }
        return convertedToDto;
    }

    public static Worker mapDtoToWorker(WorkerDto workerDtoToConvert) {

        return Worker.builder()
                .id(workerDtoToConvert.getId())
                .name(workerDtoToConvert.getName())
                .surname(workerDtoToConvert.getSurname())
                .password(workerDtoToConvert.getPassword())
                .workingPlace(modelMapper.map(workerDtoToConvert.getWorkingPlace(), OrderProcessingPoint.class))
                .roles(
                        Stream.of(
                                        WorkerRoles
                                                .builder()
                                                .role(workerDtoToConvert.getRole().name())
                                                .build())
                                .collect(Collectors.toSet())
                )
                .build();
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
        if (warehouseToConvert.getDispatchedOrders() != null) {
            convertedToDto.setDispatchedOrders(
                    warehouseToConvert
                            .getDispatchedOrders()
                            .stream()
                            .map(CustomModelMapper::mapOrderToDto)
                            .collect(Collectors.toList()));
        }
        if (warehouseToConvert.getExpectedOrders() != null) {
            convertedToDto.setExpectedOrders(
                    warehouseToConvert
                            .getExpectedOrders()
                            .stream()
                            .map(CustomModelMapper::mapOrderToDto)
                            .collect(Collectors.toList())
            );
        }
        if (convertedToDto.getOrderProcessingPoints() != null) {
            convertedToDto.setOrderProcessingPoints(
                    warehouseToConvert.getOrderProcessingPoints()
                            .stream()
                            .map(processingPointToConvert -> modelMapper.map(processingPointToConvert, OrderProcessingPointDto.class))
                            .collect(Collectors.toList())
            );
        }
        return convertedToDto;
    }

    public static OrderHistoryDto mapHistoryToDto(OrderHistory orderHistory) {

        return OrderHistoryDto.builder()
                .id(orderHistory.getId())
                .changedAt(orderHistory.getChangedAt())
                .sentAt(orderHistory.getSentAt())
                .comment(orderHistory.getComment())
                .changedTypeEnum(OrderStateChangeType.valueOf(orderHistory.getChangedTypeEnum()))
                .worker(mapWorkerToDto(orderHistory.getWorker()))
                .build();
    }

    public static OrderHistory mapDtoToHistory(OrderHistoryDto orderHistoryDto) {

        return OrderHistory.builder()
                .id(orderHistoryDto.getId())
                .changedAt(orderHistoryDto.getChangedAt())
                .sentAt(orderHistoryDto.getSentAt())
                .comment(orderHistoryDto.getComment())
                .changedTypeEnum(orderHistoryDto.getChangedTypeEnum().toString())
                .build();
    }

}
