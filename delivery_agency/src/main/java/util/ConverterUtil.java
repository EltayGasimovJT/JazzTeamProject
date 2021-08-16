package util;

import dto.AbstractLocationDto;
import dto.OrderDto;
import dto.OrderProcessingPointDto;
import dto.WarehouseDto;
import entity.AbstractLocation;
import entity.Order;
import entity.OrderProcessingPoint;
import entity.Warehouse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class ConverterUtil {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static final Converter<AbstractLocation, AbstractLocationDto> abstractLocationDTOConverter = mappingContext -> {
        AbstractLocation source = mappingContext.getSource();
        AbstractLocationDto dest = mappingContext.getDestination();

        if (source instanceof OrderProcessingPoint) {
            return modelMapper.map(dest, OrderProcessingPointDto.class);
        } else if (source instanceof Warehouse) {
            return modelMapper.map(dest, WarehouseDto.class);
        }
        return null;
    };

    public static final Converter<Order, OrderDto> orderDtoConverter = mappingContext -> {
        Order source = mappingContext.getSource();
        OrderDto dest = mappingContext.getDestination();

        if (source.getCurrentLocation() instanceof OrderProcessingPoint) {
            return modelMapper.createTypeMap(dest, OrderProcessingPointDto.class).setPreConverter(abstractLocationDTOConverter);
        } else if (source.getCurrentLocation() instanceof Warehouse) {
            return modelMapper.map(dest, WarehouseDto.class);
        }
        return null;
    };

    public static Converter<AbstractLocation, AbstractLocationDto> getAbstractLocationDTOConverter() {
        return abstractLocationDTOConverter;
    }
}
