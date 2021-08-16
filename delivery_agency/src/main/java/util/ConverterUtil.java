package util;

import dto.OrderDto;
import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import entity.Order;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import lombok.Getter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

@Getter
public class ConverterUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static final Converter<Order, OrderDto> orderDtoConverter = mappingContext -> {
        Order source = mappingContext.getSource();
        OrderDto dest = mappingContext.getDestination();

        if (source.getCurrentLocation() instanceof OrderProcessingPoint) {
            dest.setCurrentLocation(modelMapper.map(source.getCurrentLocation(), OrderProcessingPointDto.class));
            return modelMapper.map(dest, OrderDto.class);
        } else if (source.getCurrentLocation() instanceof Warehouse) {
            dest.setCurrentLocation(modelMapper.map(source.getCurrentLocation(), WarehouseDto.class));
            return modelMapper.map(dest, OrderDto.class);
        }
        return null;
    };
}
