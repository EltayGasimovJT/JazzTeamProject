package mapping;

import dto.*;
import entity.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CustomModelMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    private CustomModelMapper() {

    }

    public static OrderDto mapDtoToOrder(Order orderToConvert) {
        List<OrderHistoryDto> historiesToConvert = orderToConvert.getHistory().stream()
                .map(history -> modelMapper.map(history, OrderHistoryDto.class))
                .collect(Collectors.toList());

        OrderDto convertedToDto = OrderDto.builder()
                .id(orderToConvert.getId())
                .destinationPlace(modelMapper.map(orderToConvert.getDestinationPlace(), OrderProcessingPointDto.class))
                .state(modelMapper.map(orderToConvert.getState(), OrderStateDto.class))
                .recipient(modelMapper.map(orderToConvert.getRecipient(), ClientDto.class))
                .sender(modelMapper.map(orderToConvert.getSender(), ClientDto.class))
                .history(historiesToConvert)
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
        List<OrderHistory> historiesToConvert = orderDtoToConvert.getHistory().stream()
                .map(history -> modelMapper.map(history, OrderHistory.class))
                .collect(Collectors.toList());

        Order convertedToOrder = Order.builder()
                .id(orderDtoToConvert.getId())
                .destinationPlace(modelMapper.map(orderDtoToConvert.getDestinationPlace(), OrderProcessingPoint.class))
                .state(modelMapper.map(orderDtoToConvert.getState(), OrderState.class))
                .recipient(modelMapper.map(orderDtoToConvert.getRecipient(), Client.class))
                .sender(modelMapper.map(orderDtoToConvert.getSender(), Client.class))
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
                .roles(userToConvert.getRoles())
                .build();
        if (userToConvert.getWorkingPlace() instanceof OrderProcessingPoint) {
            convertedToDto.setWorkingPlace(modelMapper.map(userToConvert.getWorkingPlace(), OrderProcessingPointDto.class));
        } else if (userToConvert.getWorkingPlace() instanceof Warehouse) {
            convertedToDto.setWorkingPlace(modelMapper.map(userToConvert.getWorkingPlace(), WarehouseDto.class));
        }
        return convertedToDto;
    }

    public static User mapDtoToUser(UserDto userDtoToConvert) {
        User convertedToUser = User.builder()
                .id(userDtoToConvert.getId())
                .name(userDtoToConvert.getName())
                .surname(userDtoToConvert.getSurname())
                .roles(userDtoToConvert.getRoles())
                .build();
        if (userDtoToConvert.getWorkingPlace() instanceof OrderProcessingPointDto) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDtoToConvert.getWorkingPlace() instanceof WarehouseDto) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), Warehouse.class));
        }
        return convertedToUser;
    }
}
